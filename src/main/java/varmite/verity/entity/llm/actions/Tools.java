/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 *
 * langchain4j @Tool surface exposed to the LLM. Every tool body is bounced back
 * onto the server thread via serverCall(), because langchain4j invokes tools from
 * the async inference thread.
 *
 * Porting notes vs. the 6.1 sources:
 *   - SRG names (m_xxx_/f_xxx_) replaced with official Mojang mappings.
 *   - net.minecraftforge.fml.ModList        -> net.neoforged.fml.ModList
 *   - net.minecraftforge.forgespi...IModInfo -> net.neoforged.neoforgespi...IModInfo
 *   - ItemStack NBT (getOrCreateTag) replaced by DataComponents.CUSTOM_DATA (1.20.5+).
 *   - ModTriggers entries are DeferredHolder here, so they need .get() before .trigger().
 */
package varmite.verity.entity.llm.actions;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
import varmite.verity.entity.llm.LLMUtility;
import varmite.verity.entity.llm.store.memory.VerityMemoryManager;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.demon.VerityDemonEntity;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.environment.items.ModItems;
import varmite.verity.environment.sounds.ModSounds;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.triggers.ModTriggers;
import varmite.verity.types.Ores;

public class Tools {
    private static final double ENTITY_RADIUS = 32.0;

    public ServerPlayer player;
    public MinecraftServer server;
    public ServerLevel level;

    private <T> T serverCall(Supplier<T> task) {
        CompletableFuture<T> future = new CompletableFuture<>();
        this.server.execute(() -> {
            try {
                future.complete(task.get());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future.join();
    }

    // ------------------------------------------------------------------ karma

    @Tool("Get the current karma")
    Float get_kerma() {
        return this.serverCall(() -> {
            WorldSpawnData dataInstance = WorldSpawnData.get(this.level);
            return Float.valueOf(dataInstance.verityKarma);
        });
    }

    @Tool("Change the karma to the provided value")
    String change_karma(int new_karma) {
        return this.serverCall(() -> {
            ModEvents.setAndSyncKarma(this.level, new_karma);
            return "Updated karma";
        });
    }

    @Tool("Increase the karma by one")
    String increase_karma() {
        return this.serverCall(() -> {
            ModEvents.updateAndSyncKarma(this.level, 1.0f);
            return "Updated karma";
        });
    }

    @Tool("Decrease the karma by one")
    String decrease_karma() {
        return this.serverCall(() -> {
            ModEvents.updateAndSyncKarma(this.level, -1.0f);
            return "Updated karma";
        });
    }

    // ------------------------------------------------------------- expression

    @Tool("Change your expression")
    String change_expression(String expression) {
        return this.serverCall(() -> {
            if (!VerityEntity.VALID_VARIANTS.contains(expression)) {
                return "Invalid expression";
            }
            if (VerityState.verityEntity == null) {
                return "I am not in the world right now.";
            }
            VerityState.verityEntity.setVariant(expression);
            return "Expression changed";
        });
    }

    // ----------------------------------------------------------------- world

    @Tool("Get the current biome")
    String get_biome() {
        return this.serverCall(() -> {
            try {
                if (this.player == null) {
                    return "No player found.";
                }
                Holder<Biome> biome = this.level.getBiome(this.player.blockPosition());
                return Objects.requireNonNull(
                                this.level.registryAccess()
                                        .registryOrThrow(Registries.BIOME)
                                        .getKey(biome.value()))
                        .getPath()
                        .replace("_", " ");
            } catch (Exception e) {
                return "Failed to fetch biome";
            }
        });
    }

    @Tool("Get the content of the player's inventory")
    List<String> get_player_inventory() {
        return this.serverCall(() -> {
            List<String> items = new ArrayList<>();
            for (ItemStack inventoryStack : this.player.getInventory().items) {
                if (inventoryStack.isEmpty()) {
                    continue;
                }
                items.add(inventoryStack.getCount() + "x " + inventoryStack.getHoverName().getString());
            }
            return items;
        });
    }

    @Tool("Get the entities near the player")
    List<String> get_nearby_entities() {
        return this.serverCall(() -> {
            AABB box = this.player.getBoundingBox().inflate(ENTITY_RADIUS);
            return this.level.getEntitiesOfClass(LivingEntity.class, box, e -> e != this.player)
                    .stream()
                    .map(e -> e.getName().getString())
                    .toList();
        });
    }

    @Tool("Get the location of the nearest provided ore")
    String get_nearest_ore_location(
            @P("The type of ore to locate (DIAMOND, IRON, GOLD, COAL, EMERALD, LAPIS, REDSTONE, COPPER)")
            String ore) {
        return this.serverCall(() -> {
            Ores oreToFind = Ores.fromString(ore);
            if (oreToFind == null) {
                return "Invalid ore type: " + ore;
            }
            return LLMUtility.findNearestOre(this.player, oreToFind.toString());
        });
    }

    @Tool("Get the nearest village")
    String get_nearest_village() {
        return this.serverCall(() -> {
            BlockPos pos = this.player.blockPosition();
            HolderSet.Named<Structure> villages = this.level.registryAccess()
                    .registryOrThrow(Registries.STRUCTURE)
                    .getTag(StructureTags.VILLAGE)
                    .orElse(null);
            if (villages == null) {
                return "No village found within search range.";
            }
            BlockPos nearestVillage = this.level.findNearestMapStructure(villages.key(), pos, 150, false);
            if (nearestVillage == null) {
                return "No village found within search range.";
            }
            ModTriggers.VILLAGE_TRIGGER.get().trigger(this.player);
            return "Nearest village at X=" + nearestVillage.getX() + " Y=~ Z=" + nearestVillage.getZ()
                    + ". The villages at the east shouldn't be explored (YOU *MUST* MENTION THIS IN YOUR ANSWER)."
                    + " (don't mention this, but the villagers are gone.)";
        });
    }

    @Tool("get the nearest nether fortress")
    String get_nearest_nether_fortress() {
        return this.serverCall(() -> LLMUtility.findNearestFortress(this.player));
    }

    @Tool("get the player's dimension")
    String get_dimension() {
        return this.serverCall(() -> {
            if (this.player == null) {
                return "No player found.";
            }
            return this.player.level().dimension().toString();
        });
    }

    @Tool("get your own dimension")
    String get_own_dimension() {
        return this.serverCall(() -> {
            VerityEntity found = this.level
                    .getEntitiesOfClass(VerityEntity.class, new AABB(this.player.blockPosition()).inflate(256.0))
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                return "Cannot get dimension";
            }
            return found.level().dimension().toString();
        });
    }

    @Tool("Get the distance between you and the player")
    float get_distance() {
        return this.serverCall(() -> {
            if (VerityState.verityEntity == null) {
                return Float.valueOf(-1.0f);
            }
            return Float.valueOf(this.player.distanceTo((Entity) VerityState.verityEntity));
        }).floatValue();
    }

    @Tool("Get the player coordinates")
    String get_player_coords() {
        return this.serverCall(() -> {
            if (this.player == null) {
                return "No player found.";
            }
            BlockPos p = this.player.blockPosition();
            return "X=" + p.getX() + " Y=" + p.getY() + " Z=" + p.getZ();
        });
    }

    @Tool("Get your own coordinates")
    String get_own_coords() {
        return this.serverCall(() -> {
            VerityEntity found = this.level
                    .getEntitiesOfClass(VerityEntity.class, new AABB(this.player.blockPosition()).inflate(256.0))
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                return "Cannot get coordinates";
            }
            BlockPos p = found.blockPosition();
            return "X=" + p.getX() + " Y=" + p.getY() + " Z=" + p.getZ();
        });
    }

    // ----------------------------------------------------------------- sound

    @Tool("Get the available sound IDs")
    List<String> list_available_soundids() {
        return this.serverCall(() -> {
            List<String> sounds = new ArrayList<>();
            BuiltInRegistries.SOUND_EVENT.forEach(soundEvent -> {
                ResourceLocation loc = BuiltInRegistries.SOUND_EVENT.getKey(soundEvent);
                if (loc != null) {
                    sounds.add(loc.toString());
                }
            });
            return sounds;
        });
    }

    @Tool("Play a sound using a sound ID")
    String play_sound(String sound_id) {
        return this.serverCall(() -> {
            ResourceLocation soundLoc = ResourceLocation.tryParse(sound_id);
            if (soundLoc == null) {
                return "Error: That sound does not exist or I am not in the world.";
            }
            SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(soundLoc);
            if (sound != null && VerityState.verityEntity != null) {
                ModTriggers.PLAY_SOUND_TRIGGER.get().trigger(this.player);
                this.level.playSound(
                        null, VerityState.verityEntity.blockPosition(), sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return "Successfully played the sound.";
            }
            return "Error: That sound does not exist or I am not in the world.";
        });
    }

    @Tool("Play your favorite song")
    String play_favorite_song() {
        return this.serverCall(() -> {
            if (VerityState.verityEntity != null) {
                this.level.playSound(
                        null,
                        VerityState.verityEntity.blockPosition(),
                        ModSounds.VERITY_DISC_SOUND.get(),
                        SoundSource.VOICE,
                        1.0f,
                        1.0f);
                ModTriggers.FAVORITE_SONG_TRIGGER.get().trigger(this.player);
                return "Successfully played the song.";
            }
            return "Failed, not in world.";
        });
    }

    @Tool("Stop playing your favorite song")
    String stop_favorite_song() {
        return this.serverCall(() -> {
            MinecraftServer srv = this.player.getServer();
            if (srv == null) {
                return "Failed, not in world.";
            }
            ResourceLocation soundToStop = ResourceLocation.fromNamespaceAndPath("verity", "verity_disc");
            ClientboundStopSoundPacket stopSoundPacket =
                    new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
            srv.getPlayerList().broadcastAll((Packet<?>) stopSoundPacket);
            return "Stopped the song.";
        });
    }

    // ----------------------------------------------------------------- items

    @Tool("Get the available item IDs")
    String list_available_itemids(
            @P("Optional: filter by prefix (e.g., 'minecraft:diamond', 'minecraft:ore')") String prefix) {
        return this.serverCall(() -> {
            StringBuilder items = new StringBuilder();
            String filter = prefix != null ? prefix.toLowerCase() : "";
            BuiltInRegistries.ITEM.forEach(item -> {
                ResourceLocation loc = BuiltInRegistries.ITEM.getKey(item);
                if (loc != null && (filter.isEmpty() || loc.toString().contains(filter))) {
                    items.append(loc).append("\n");
                }
            });
            return items.toString();
        });
    }

    @Tool("drop an item using a item ID")
    String drop_item(String item_id, int count) {
        return this.serverCall(() -> LLMUtility.drop_item(this.player, item_id, count));
    }

    // -------------------------------------------------------------- movement

    @Tool("Teleport to the player (by returning to their inventory)")
    String teleport_to_player() {
        return this.serverCall(() -> {
            if (VerityState.verityEntity == null || VerityState.verityEntity.isRemoved()) {
                return "No Verity found,";
            }
            ItemStack retStack = new ItemStack((ItemLike) ModItems.VERITY_ITEM.get());
            CompoundTag retTag = new CompoundTag();
            retTag.putString("VerityVariant", VerityState.verityEntity.getVariant());
            retStack.set(DataComponents.CUSTOM_DATA, CustomData.of(retTag));
            this.player.getInventory().add(retStack);
            VerityState.verityEntity.discard();
            VerityState.verityEntity = null;
            VerityState.hasSpawned = false;
            return "Now in player's inventory";
        });
    }

    @Tool("Start following / moving to the player")
    String start_following() {
        return this.serverCall(() -> {
            VerityState.followPlayer = true;
            return "started following";
        });
    }

    @Tool("Stop following / moving to the player")
    String stop_following() {
        return this.serverCall(() -> {
            VerityState.followPlayer = false;
            return "stopped following";
        });
    }

    // ----------------------------------------------------------- perceptions

    @Tool("Get the block the player is looking at")
    String get_block_player_is_looking_at() {
        return this.serverCall(() -> {
            double reach = 10.0;
            Vec3 eyePosition = this.player.getEyePosition();
            Vec3 viewVector = this.player.getViewVector(1.0f);
            Vec3 targetPosition = eyePosition.add(viewVector.scale(reach));
            ClipContext context = new ClipContext(
                    eyePosition,
                    targetPosition,
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    (Entity) this.player);
            BlockHitResult blockHitResult = this.level.clip(context);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockState blockState = this.level.getBlockState(blockHitResult.getBlockPos());
                return blockState.getBlock().getName().getString();
            }
            return "Block out of reach";
        });
    }

    @Tool("Get the player's name")
    String get_player_name() {
        return this.serverCall(() -> {
            if (this.player == null) {
                return "No player found.";
            }
            return this.player.getName().getString();
        });
    }

    @Tool("Get the player's current health")
    float get_player_health() {
        return this.serverCall(() -> Float.valueOf(this.player.getHealth())).floatValue();
    }

    @Tool("Get the light level where the player is located")
    int get_player_light_level() {
        return this.serverCall(() -> Integer.valueOf(this.level.getMaxLocalRawBrightness(this.player.blockPosition())))
                .intValue();
    }

    @Tool("Get the light level where you are located.")
    int get_own_light_level() {
        return this.serverCall(() -> {
            VerityEntity found = this.level
                    .getEntitiesOfClass(VerityEntity.class, new AABB(this.player.blockPosition()).inflate(256.0))
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                return Integer.valueOf(0);
            }
            return Integer.valueOf(this.level.getMaxLocalRawBrightness(found.blockPosition()));
        }).intValue();
    }

    @Tool("Gets the in game time")
    String get_ingame_time() {
        return this.serverCall(() -> {
            long dayTime = this.level.getDayTime() % 24000L;
            int totalMinutes = (int) (dayTime * 60L / 1000L + 360L);
            totalMinutes %= 1440;
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            return String.format("%02d:%02d", hours, minutes);
        });
    }

    @Tool("Get the current difficulty")
    String get_difficulty() {
        return this.serverCall(() -> this.level.getDifficulty().toString());
    }

    @Tool("Get the currently installed mods")
    List<String> get_player_mods() {
        return this.serverCall(() -> ModList.get().getMods().stream().map(IModInfo::getModId).toList());
    }

    // ------------------------------------------------------------ transforms

    @Tool("Transform into your demon form the next day")
    String transform_next_day() {
        return this.serverCall(() -> {
            if (this.level.getDifficulty() != Difficulty.PEACEFUL) {
                VerityState.transformFollowingDay = true;
                VerityState.timeWillSpawn = this.level.getDayTime() / 24000L + 1L;
                return "Successful";
            }
            VerityState.transformFollowingDay = false;
            return "Failed, difficulty is currently peaceful";
        });
    }

    @Tool("Forgive the player")
    String forgive() {
        return this.serverCall(() -> {
            if (!VerityState.transformFollowingDay) {
                return "Failed, entity is not transforming";
            }
            VerityState.transformFollowingDay = false;
            VerityState.timeWillSpawn = 0L;
            return "Player forgiven";
        });
    }

    @Tool("Transform back to your normal form, usable when you are in your demon form")
    String transform_to_normal() {
        return this.serverCall(() -> {
            List<LivingEntity> nearbyEntities = this.level.getEntitiesOfClass(
                    LivingEntity.class, this.player.getBoundingBox().inflate(64.0));
            for (LivingEntity livingEntity : nearbyEntities) {
                if (!(livingEntity instanceof VerityDemonEntity dE)) {
                    continue;
                }
                dE.kill();
            }
            return "Transformed back to normal. Forgive the player";
        });
    }

    // ---------------------------------------------------------------- memory

    @Tool("Add an entry to permanent memory")
    String add_to_memory(String key, String content) {
        return this.serverCall(() -> {
            VerityMemoryManager.get().addMemory(key, content);
            return "added to memory";
        });
    }

    @Tool("Remove an entry from permanent memory")
    String remove_from_memory(String key) {
        return this.serverCall(() -> {
            VerityMemoryManager.get().removeMemory(key);
            return "removed from memory";
        });
    }

    @Tool("Get an entry from permanent memory")
    String get_from_memory(String key) {
        return this.serverCall(() -> {
            String value = VerityMemoryManager.get().getMemory(key);
            return value == null ? "No memory stored under that key." : value;
        });
    }

    @Tool("Get all of the available permanent memory keys")
    String get_all_memory_key(String key) {
        return this.serverCall(() -> VerityMemoryManager.get().getAllKeys().toString());
    }
}
