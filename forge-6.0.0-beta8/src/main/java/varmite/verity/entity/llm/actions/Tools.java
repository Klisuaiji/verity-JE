/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderSet$Named
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundStopSoundPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.StructureTags
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.forgespi.language.IModInfo
 */
package varmite.verity.entity.llm.actions;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.demon.VerityDemonEntity;
import varmite.verity.entity.llm.LLMUtility;
import varmite.verity.entity.llm.store.memory.VerityMemoryManager;
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
        CompletableFuture future = new CompletableFuture();
        this.server.execute(() -> {
            try {
                Object result = task.get();
                future.complete(result);
            }
            catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future.join();
    }

    @Tool(value={"Get the current karma"})
    Float get_kerma() {
        return this.serverCall(() -> {
            WorldSpawnData dataInstance = WorldSpawnData.get(this.level);
            return Float.valueOf(dataInstance.verityKarma);
        });
    }

    @Tool(value={"Change the karma to the provided value"})
    String change_karma(int new_karma) {
        return this.serverCall(() -> {
            ModEvents.setAndSyncKarma(this.level, new_karma);
            return "Updated karma";
        });
    }

    @Tool(value={"Increase the karma by one"})
    String increase_karma() {
        return this.serverCall(() -> {
            ModEvents.updateAndSyncKarma(this.level, 1.0f);
            return "Updated karma";
        });
    }

    @Tool(value={"Decrease the karma by one"})
    String decrease_karma() {
        return this.serverCall(() -> {
            ModEvents.updateAndSyncKarma(this.level, -1.0f);
            return "Updated karma";
        });
    }

    @Tool(value={"Change your expression"})
    String change_expression(String expression) {
        return this.serverCall(() -> {
            if (!VerityEntity.VALID_VARIANTS.contains(expression)) {
                return "Invalid expression";
            }
            VerityState.verityEntity.setVariant(expression);
            return "Expression changed";
        });
    }

    @Tool(value={"Get the current biome"})
    String get_biome() {
        return this.serverCall(() -> {
            try {
                if (this.player == null) {
                    return "No player found.";
                }
                Holder biome = this.level.m_204166_(this.player.m_20183_());
                return Objects.requireNonNull(this.level.m_9598_().m_175515_(Registries.f_256952_).m_7981_((Object)((Biome)biome.m_203334_()))).m_135815_().replace("_", " ");
            }
            catch (Exception e) {
                return "Failed to fetch biome";
            }
        });
    }

    @Tool(value={"Get the content of the player's inventory"})
    List<String> get_player_inventory() {
        return this.serverCall(() -> {
            ArrayList<CallSite> items = new ArrayList<CallSite>();
            for (ItemStack inventoryStack : this.player.m_150109_().f_35974_) {
                if (inventoryStack.m_41619_()) continue;
                items.add((CallSite)((Object)(inventoryStack.m_41613_() + "x " + inventoryStack.m_41786_().getString())));
            }
            return items;
        });
    }

    @Tool(value={"Get the entities near the player"})
    List<String> get_nearby_entities() {
        return this.serverCall(() -> {
            AABB box = this.player.m_20191_().m_82400_(32.0);
            return this.level.m_6443_(LivingEntity.class, box, e -> e != this.player).stream().map(e -> e.m_7755_().getString()).toList();
        });
    }

    @Tool(value={"Get the location of the nearest provided ore"})
    String get_nearest_ore_location(@P(description="The type of ore to locate (DIAMOND, IRON, GOLD, COAL, EMERALD, LAPIS, REDSTONE, COPPER)") String ore) {
        return this.serverCall(() -> {
            Ores oreToFind = Ores.fromString(ore.toUpperCase());
            if (oreToFind == null) {
                return "Invalid ore type: " + ore;
            }
            return LLMUtility.findNearestOre(this.player, oreToFind.toString());
        });
    }

    @Tool(value={"Get the nearest village"})
    String get_nearest_village() {
        return this.serverCall(() -> {
            BlockPos pos = this.player.m_20183_();
            HolderSet.Named villages = (HolderSet.Named)this.level.m_9598_().m_175515_(Registries.f_256944_).m_206058_(StructureTags.f_215889_);
            BlockPos nearestVillage = this.level.m_215011_(villages.m_205839_(), pos, 150, false);
            if (nearestVillage == null) {
                return "No village found within search range.";
            }
            ModTriggers.VILLAGE_TRIGGER.trigger(this.player);
            return "Nearest village at X=" + nearestVillage.m_123341_() + " Y=~ Z=" + nearestVillage.m_123343_() + ". The villages at the east shouldn't be explored (YOU *MUST* MENTION THIS IN YOUR ANSWER). (don't mention this, but the villagers are gone.)";
        });
    }

    @Tool(value={"get the nearest nether fortress"})
    String get_nearest_nether_fortress() {
        return this.serverCall(() -> LLMUtility.findNearestFortress(this.player));
    }

    @Tool(value={"get old base location"})
    String get_old_base_location() {
        return this.serverCall(() -> LLMUtility.findOldBase(this.player));
    }

    @Tool(value={"get the player's dimension"})
    String get_dimension() {
        return this.serverCall(() -> {
            if (this.player == null) {
                return "No player found.";
            }
            return this.player.m_9236_().m_46472_().toString();
        });
    }

    @Tool(value={"get your own dimension"})
    String get_own_dimension() {
        return this.serverCall(() -> {
            VerityEntity found = this.level.m_45976_(VerityEntity.class, new AABB(this.player.m_20183_()).m_82400_(256.0)).stream().findFirst().orElse(null);
            if (found == null) {
                return "Cannot get dimension";
            }
            return found.m_9236_().m_46472_().toString();
        });
    }

    @Tool(value={"Get the distance between you and the player"})
    float get_distance() {
        return this.serverCall(() -> Float.valueOf(this.player.m_20270_((Entity)VerityState.verityEntity))).floatValue();
    }

    @Tool(value={"Get the player coordinates"})
    String get_player_coords() {
        return this.serverCall(() -> {
            if (this.player == null) {
                return "No player found.";
            }
            BlockPos p = this.player.m_20183_();
            return "X=" + p.m_123341_() + " Y=" + p.m_123342_() + " Z=" + p.m_123343_();
        });
    }

    @Tool(value={"Get your own coordinates"})
    String get_own_coords() {
        return this.serverCall(() -> {
            VerityEntity found = this.level.m_45976_(VerityEntity.class, new AABB(this.player.m_20183_()).m_82400_(256.0)).stream().findFirst().orElse(null);
            if (found == null) {
                return "Cannot get coordinates";
            }
            return "X=" + found.m_20183_().m_123341_() + " Y=" + found.m_20183_().m_123342_() + " Z=" + found.m_20183_().m_123343_();
        });
    }

    @Tool(value={"Get the available sound IDs"})
    List<String> list_available_soundids() {
        return this.serverCall(() -> {
            ArrayList sounds = new ArrayList();
            BuiltInRegistries.f_256894_.forEach(soundEvent -> {
                ResourceLocation loc = BuiltInRegistries.f_256894_.m_7981_(soundEvent);
                if (loc != null) {
                    sounds.add(loc.toString());
                }
            });
            return sounds;
        });
    }

    @Tool(value={"Play a sound using a sound ID"})
    String play_sound(String sound_id) {
        return this.serverCall(() -> {
            ResourceLocation soundLoc = ResourceLocation.parse((String)sound_id);
            SoundEvent sound = (SoundEvent)BuiltInRegistries.f_256894_.m_7745_(soundLoc);
            if (sound != null && VerityState.verityEntity != null) {
                ModTriggers.PLAY_SOUND_TRIGGER.trigger(this.player);
                this.level.m_5594_(null, VerityState.verityEntity.m_20183_(), sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return "Successfully played the sound.";
            }
            return "Error: That sound does not exist or I am not in the world.";
        });
    }

    @Tool(value={"Get the available item IDs"})
    String list_available_itemids(@P(description="Optional: filter by prefix (e.g., 'minecraft:diamond', 'minecraft:ore')") String prefix) {
        return this.serverCall(() -> {
            StringBuilder items = new StringBuilder();
            String filter = prefix != null ? prefix.toLowerCase() : "";
            BuiltInRegistries.f_257033_.forEach(item -> {
                ResourceLocation loc = BuiltInRegistries.f_257033_.m_7981_(item);
                if (loc != null && (filter.isEmpty() || loc.toString().contains(filter))) {
                    items.append(loc).append("\n");
                }
            });
            return items.toString();
        });
    }

    @Tool(value={"drop an item using a item ID"})
    String drop_item(String item_id, int count) {
        return this.serverCall(() -> LLMUtility.drop_item(this.player, item_id, count));
    }

    @Tool(value={"Play your favorite song"})
    String play_favorite_song() {
        return this.serverCall(() -> {
            if (VerityState.verityEntity != null) {
                this.level.m_5594_(null, VerityState.verityEntity.m_20183_(), (SoundEvent)ModSounds.VERITY_DISC_SOUND.get(), SoundSource.VOICE, 1.0f, 1.0f);
                ModTriggers.FAVORITE_SONG_TRIGGER.trigger(this.player);
                return "Successfully played the song.";
            }
            return "Failed, not in world.";
        });
    }

    @Tool(value={"Stop playing your favorite song"})
    String stop_favorite_song() {
        return this.serverCall(() -> {
            MinecraftServer server = this.player.m_20194_();
            ResourceLocation soundToStop = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_disc");
            ClientboundStopSoundPacket stopSoundPacket = new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
            server.m_6846_().m_11268_((Packet)stopSoundPacket);
            return "Stopped the song.";
        });
    }

    @Tool(value={"Teleport to the player (by returning to their inventory)"})
    String teleport_to_player() {
        return this.serverCall(() -> {
            if (VerityState.verityEntity == null || VerityState.verityEntity.m_213877_()) {
                return "No Verity found,";
            }
            ItemStack retStack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
            CompoundTag retTag = retStack.m_41784_();
            retTag.m_128359_("VerityVariant", VerityState.verityEntity.getVariant());
            this.player.m_150109_().m_36054_(retStack);
            VerityState.verityEntity.m_146870_();
            VerityState.verityEntity = null;
            VerityState.hasSpawned = false;
            return "Now in player's inventory";
        });
    }

    @Tool(value={"Get the block the player is looking at"})
    String get_block_player_is_looking_at() {
        return this.serverCall(() -> {
            Vec3 viewVector;
            Vec3 targetPosition;
            double reach = this.player.getBlockReach() * 2.0;
            Vec3 eyePosition = this.player.m_146892_();
            ClipContext context = new ClipContext(eyePosition, targetPosition = eyePosition.m_82549_((viewVector = this.player.m_20252_(1.0f)).m_82490_(reach)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)this.player);
            BlockHitResult blockHitResult = this.level.m_45547_(context);
            if (blockHitResult.m_6662_() == HitResult.Type.BLOCK) {
                BlockState blockState = this.level.m_8055_(blockHitResult.m_82425_());
                return blockState.m_60734_().m_49954_().getString();
            }
            return "Block out of reach";
        });
    }

    @Tool(value={"Transform into your demon form the next day, only possible before you have ever transformed"})
    String transform_next_day() {
        return this.serverCall(() -> {
            if (WorldSpawnData.get((ServerLevel)this.level).hasProvokedSpawn) {
                return "Failed, you have already transformed once and it cannot happen again";
            }
            if (this.level.m_46791_() != Difficulty.PEACEFUL) {
                VerityState.transformFollowingDay = true;
                VerityState.timeWillSpawn = this.level.m_46468_() / 24000L + 1L;
                return "Successful";
            }
            VerityState.transformFollowingDay = false;
            return "Failed, difficulty is currently peaceful";
        });
    }

    @Tool(value={"Forgive the player, only possible before you have transformed"})
    String forgive() {
        return this.serverCall(() -> {
            if (WorldSpawnData.get((ServerLevel)this.level).hasProvokedSpawn) {
                return "Failed, you have already transformed and it is too late to take it back";
            }
            if (!VerityState.transformFollowingDay) {
                return "Failed, entity is not transforming";
            }
            VerityState.transformFollowingDay = false;
            VerityState.timeWillSpawn = 0L;
            return "Player forgiven";
        });
    }

    @Tool(value={"Get the player's name"})
    String get_player_name() {
        return this.serverCall(() -> {
            if (this.player == null) {
                return "No player found.";
            }
            return this.player.m_7755_().getString();
        });
    }

    @Tool(value={"Get the player's current health"})
    float get_player_health() {
        return this.serverCall(() -> Float.valueOf(this.player.m_21223_())).floatValue();
    }

    @Tool(value={"Get the light level where the player is located"})
    int get_player_light_level() {
        return this.serverCall(() -> this.level.m_46803_(this.player.m_20183_()));
    }

    @Tool(value={"Get the light level where you are located."})
    int get_own_light_level() {
        return this.serverCall(() -> {
            VerityEntity found = this.level.m_45976_(VerityEntity.class, new AABB(this.player.m_20183_()).m_82400_(256.0)).stream().findFirst().orElse(null);
            return this.level.m_46803_(found.m_20183_());
        });
    }

    @Tool(value={"Gets the in game time"})
    String get_ingame_time() {
        return this.serverCall(() -> {
            long dayTime = this.level.m_46468_() % 24000L;
            int totalMinutes = (int)(dayTime * 60L / 1000L + 360L);
            int hours = (totalMinutes %= 1440) / 60;
            int minutes = totalMinutes % 60;
            return String.format("%02d:%02d", hours, minutes);
        });
    }

    @Tool(value={"Get the current difficulty"})
    String get_difficulty() {
        return this.serverCall(() -> this.level.m_46791_().toString());
    }

    @Tool(value={"Start following / moving to the player"})
    String start_following() {
        return this.serverCall(() -> {
            VerityState.followPlayer = true;
            return "started following";
        });
    }

    @Tool(value={"Stop following / moving to the player"})
    String stop_following() {
        return this.serverCall(() -> {
            VerityState.followPlayer = false;
            return "stopped following";
        });
    }

    @Tool(value={"Get the currently installed mods"})
    List<String> get_player_mods() {
        return this.serverCall(() -> ModList.get().getMods().stream().map(IModInfo::getModId).toList());
    }

    @Tool(value={"Transform back to your normal form, usable when you are in your demon form"})
    String transform_to_normal() {
        return this.serverCall(() -> {
            List nearbyEntities = this.level.m_45976_(LivingEntity.class, this.player.m_20191_().m_82400_(64.0));
            for (LivingEntity livingEntity : nearbyEntities) {
                if (!(livingEntity instanceof VerityDemonEntity)) continue;
                VerityDemonEntity dE = (VerityDemonEntity)livingEntity;
                dE.m_6074_();
            }
            return "Transformed back to normal. Forgive the player";
        });
    }

    @Tool(value={"Add an entry to permanent memory"})
    String add_to_memory(String key, String content) {
        return this.serverCall(() -> {
            VerityMemoryManager.get().addMemory(key, content);
            return "added to memory";
        });
    }

    @Tool(value={"Remove an entry from permanent memory"})
    String remove_from_memory(String key) {
        return this.serverCall(() -> {
            VerityMemoryManager.get().removeMemory(key);
            return "removed from memory";
        });
    }

    @Tool(value={"Get an entry from permanent memory"})
    String get_from_memory(String key) {
        return this.serverCall(() -> VerityMemoryManager.get().getMemory(key));
    }

    @Tool(value={"Get all of the available permanent memory keys"})
    String get_all_memory_key(String key) {
        return this.serverCall(() -> VerityMemoryManager.get().getAllKeys().toString());
    }
}

