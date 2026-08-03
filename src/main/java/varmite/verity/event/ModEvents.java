/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderSet
 *  net.minecraft.core.HolderSet$Direct
 *  net.minecraft.core.HolderSet$Named
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundStopSoundPacket
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.tags.StructureTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.animal.Chicken
 *  net.minecraft.world.entity.animal.Cow
 *  net.minecraft.world.entity.animal.Pig
 *  net.minecraft.world.entity.animal.Sheep
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.player.Player$BedSleepingProblem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.capabilities.ICapabilityProvider
 *  net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
 *  net.neoforged.neoforge.event.AttachCapabilitiesEvent
 *  net.neoforged.neoforge.event.RegisterCommandsEvent
 *  net.neoforged.neoforge.event.ServerChatEvent
 *  net.neoforged.neoforge.event.TickEvent$Phase
 *  net.neoforged.neoforge.event.tick.ServerTickEvent
 *  net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
 *  net.neoforged.neoforge.event.entity.item.ItemExpireEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDamageEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDeathEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDropsEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$Clone
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerRespawnEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.neoforged.neoforge.event.entity.player.PlayerSleepInBedEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.ModList
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforgespi.language.IModInfo
 *  net.neoforged.neoforge.network.PacketDistributor
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  varmite.verity.VerityConfig
 *  varmite.verity.command.ChangeKarmaCommand
 *  varmite.verity.command.RecoverVerityCommand
 *  varmite.verity.entity.AI.AiAPI
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.custom.BoxEntity
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.entity.custom.VerityEntity
 *  varmite.verity.event.ModEvents
 *  varmite.verity.event.ModEvents$ScheduledTask
 *  varmite.verity.event.WorldSpawnData
 *  varmite.verity.gui.PlayerKarma
 *  varmite.verity.gui.PlayerKarmaProvider
 *  varmite.verity.item.ModItems
 *  varmite.verity.network.KarmaSyncS2CPacket
 *  varmite.verity.network.ModMessages
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.network.PlayTtsPayload
 *  varmite.verity.sounds.ModSounds
 *  varmite.verity.triggers.ModTriggers
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.command.ChangeKarmaCommand;
import varmite.verity.command.RecoverVerityCommand;
import varmite.verity.entity.LLM.AiManager;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.gui.PlayerKarma;
import varmite.verity.item.ModItems;
import varmite.verity.network.KarmaSyncS2CPacket;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;
import varmite.verity.triggers.ModTriggers;

import net.neoforged.neoforge.event.tick.ServerTickEvent;
/*
 * Exception performing whole class analysis ignored.
 */
@EventBusSubscriber(modid="verity")
public class ModEvents {
    public static boolean hasSpawned = false;
    public static long timeWillSpawn;
    static double ENTITY_RADIUS;
    static VerityEntity verityEntity;
    private static final Map<UUID, Long> HURT_COOLDOWN;
    public static boolean transformFollowingDay;
    public static boolean followPlayer;
    private static int idleChatTimer;
    private static int lonelinessTimer;
    public static boolean isMonstrous;
    private static final List<ScheduledTask> PENDING_TASKS;
    private static final List<ScheduledTask> ACTIVE_TASKS;

    public static void updateAndSyncKarma(ServerLevel level, float amount) {
        WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
        data.verityKarma += amount;
        data.verityKarma = Mth.clamp(data.verityKarma, 0.0f, 20.0f);
        data.setDirty();
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new KarmaSyncS2CPacket((int)data.verityKarma));
        }
    }

    public static void setAndSyncKarma(ServerLevel level, float amount) {
        WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
        data.verityKarma = amount;
        data.verityKarma = Mth.clamp(data.verityKarma, 0.0f, 20.0f);
        data.setDirty();
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new KarmaSyncS2CPacket((int)data.verityKarma));
        }
    }

    public static boolean canDropItem(Item item) {
        return item != Items.DIAMOND && item != Items.DIAMOND_AXE && item != Items.DIAMOND_PICKAXE && item != Items.DIAMOND_SWORD && item != Items.DIAMOND_SHOVEL && item != Items.DIAMOND_HOE && item != Items.DIAMOND_HELMET && item != Items.DIAMOND_CHESTPLATE && item != Items.DIAMOND_LEGGINGS && item != Items.DIAMOND_BOOTS && item != Items.DIAMOND_BLOCK && item != Items.DIAMOND_HORSE_ARMOR && item != Items.DIAMOND_ORE && item != Items.NETHERITE_INGOT && item != Items.NETHERITE_AXE && item != Items.NETHERITE_PICKAXE && item != Items.NETHERITE_SWORD && item != Items.NETHERITE_SHOVEL && item != Items.NETHERITE_HOE && item != Items.NETHERITE_HELMET && item != Items.NETHERITE_CHESTPLATE && item != Items.NETHERITE_LEGGINGS && item != Items.NETHERITE_BOOTS && item != Items.NETHERITE_BLOCK && item != Items.ANCIENT_DEBRIS && item != Items.NETHERITE_SCRAP && item != Items.ENDER_EYE && item != Items.END_PORTAL_FRAME && item != Items.BLAZE_ROD && item != Items.ELYTRA && item != Items.NETHER_STAR && item != Items.BEACON && item != Items.COMMAND_BLOCK && item != Items.CHAIN_COMMAND_BLOCK && item != Items.REPEATING_COMMAND_BLOCK && item != Items.COMMAND_BLOCK_MINECART && item != Items.BARRIER && item != Items.STRUCTURE_BLOCK && item != Items.STRUCTURE_VOID && item != Items.LIGHT;
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        RecoverVerityCommand.register((CommandDispatcher)event.getDispatcher());
        ChangeKarmaCommand.register((CommandDispatcher)event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        Player player;
        if (!event.getEntity().level().isClientSide() && (player = event.getEntity()) instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            AABB searchBox = player2.getBoundingBox().inflate(128.0);
            List nearbyDemons = player2.level().getEntities(EntityTypeTest.forClass(VerityDemonEntity.class), searchBox, e -> true);
            isMonstrous = !nearbyDemons.isEmpty();
            ServerLevel level = player2.serverLevel();
            WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
            PacketDistributor.sendToPlayer(player2, new KarmaSyncS2CPacket((int)data.verityKarma));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            ModEvents.schedule(() -> {
                ServerLevel level = player2.serverLevel();
                WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
                boolean foundJson = false;
                for (int i = 0; i < data.chatHistory.size(); ++i) {
                    CompoundTag messageTag = data.chatHistory.getCompound(i);
                    String content = messageTag.getString("content");
                    if (!content.toLowerCase().contains("json")) continue;
                    foundJson = true;
                    break;
                }
                if (foundJson) {
                    data.chatHistory.clear();
                }
                PacketDistributor.sendToPlayer(player2, new KarmaSyncS2CPacket((int)data.verityKarma));
            }, (int)20);
        }
    }

    @SubscribeEvent
    public static void onVerityTakeDamage(LivingDamageEvent.Pre event) {
        VerityEntity verity;
        LivingEntity livingEntity = event.getEntity();
        if (!(livingEntity instanceof VerityEntity) || (verity = (VerityEntity)livingEntity).level().isClientSide()) {
            return;
        }
        boolean burning = event.getSource().is(DamageTypeTags.IS_FIRE) || event.getSource().is(DamageTypes.LAVA);
        boolean crushed = event.getSource().is(DamageTypes.IN_WALL) || event.getSource().is(DamageTypes.FALLING_BLOCK) || event.getSource().is(DamageTypes.FALLING_ANVIL);
        if (!burning && !crushed) {
            return;
        }
        event.setNewDamage(0.0f);
        ServerLevel serverLevel = (ServerLevel)verity.level();
        MinecraftServer server = verity.getServer();
        long currentTime = serverLevel.getGameTime();
        long lastHurt = HURT_COOLDOWN.getOrDefault(verity.getUUID(), 0L);
        if (currentTime - lastHurt < 100L || server == null) {
            return;
        }
        HURT_COOLDOWN.put(verity.getUUID(), currentTime);
        if (burning) {
            String[] messages = new String[]{"IT BURNS", "GET ME OUT OF HERE", "HELP ME IT BURNS", "AGH IT BURNS", "AGH"};
            String answer = messages[RandomSource.create().nextInt(messages.length)];
            server.execute(() -> {
                if (!verity.isRemoved()) {
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(verity, new PlayTtsPayload(verity.getId(), answer));
                }
                ModEvents.updateAndSyncKarma(serverLevel, -2.0f);
                if (!((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                    server.getPlayerList().broadcastSystemMessage((Component)Component.literal("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> " + answer), false);
                }
            });
            return;
        }
        AiManager.queryAI(verity, "<SYSTEM> A heavy block has dropped on verity during this turn", null);
    }

    @SubscribeEvent
    public static void onDespawn(ItemExpireEvent event) {
        ServerLevel serverLevel;
        Level level;
        if (event.getEntity().getItem().is((Item)ModItems.VERITY_ITEM.get()) && (level = event.getEntity().level()) instanceof ServerLevel) {
            serverLevel = (ServerLevel)level;
            Player p = serverLevel.getNearestPlayer((Entity)event.getEntity(), 256.0);
            if (p != null) {
                p.getInventory().add(new ItemStack((ItemLike)ModItems.VERITY_ITEM.get()));
                serverLevel.playSound((Player)null, p.blockPosition(), SoundEvents.GHAST_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);
                p.sendSystemMessage((Component)Component.translatable("verity.msg.despawn_chat", VerityConfig.VERITY_CUSTOM_NAME.get()));
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(p, new PlayTtsPayload(p.getId(), "Ayo chat why u lettin me despawn like that"));
            }
        }
    }

    @SubscribeEvent
    public static void rightClickAir(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        Player player = event.getEntity();
        if (!player.isCrouching()) {
            return;
        }
        ItemStack stack = player.getItemInHand(event.getHand());
        if (stack.is((Item)ModItems.VERITY_ITEM.get())) {
            String variantToSpawn = "default";
            CompoundTag tagData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            if (tagData.contains("VerityVariant")) {
                variantToSpawn = tagData.getString("VerityVariant");
            }
            stack.shrink(1);
            Vec3 launchVelocity = player.getLookAngle().normalize().scale(1.5);
            VerityEntity newVerity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create(player.level());
            if (newVerity != null) {
                newVerity.moveTo(player.blockPosition().offset(0, 1, 0), 0.0f, 0.0f);
                newVerity.setVariant(variantToSpawn);
                newVerity.getPersistentData().putBoolean("WasThrown", true);
                newVerity.setOwnerUUID(player.getUUID());
                player.level().addFreshEntity(newVerity);
                verityEntity = newVerity;
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(verityEntity, new PlayTtsPayload(verityEntity.getId(), "AAAAAAAAHHH"));
                player.level().playSound((Player)null, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0f, 1.0f);
                newVerity.setDeltaMovement(launchVelocity);
                newVerity.hurtMarked = true;
            }
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (stack.is((Item)ModItems.FLASHLIGHT.get())) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            if (!event.getLevel().isClientSide()) {
                Player p = event.getEntity();
                CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag tag = customData.copyTag();
                boolean isNowOn = !tag.getBoolean("FlashlightOn");
                tag.putBoolean("FlashlightOn", isNowOn);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                if (isNowOn) {
                    p.level().playSound((Player)null, p.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.PLAYERS, 1.0f, 1.0f);
                } else {
                    p.level().playSound((Player)null, p.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) {
            Player p = (Player)livingEntity;
            if (!(p instanceof ServerPlayer)) {
                return;
            }
            ServerPlayer serverPlayer = (ServerPlayer)p;
            if (event.getSource().getEntity() instanceof VerityDemonEntity && ((Boolean)VerityConfig.CAN_CRASH.get()).booleanValue()) {
                serverPlayer.connection.disconnect((Component)Component.literal(("Farewell, " + p.getName().getString())).withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD}));
            }
        } else {
            VerityDemonEntity demon;
            Entity entity = event.getEntity();
            if (entity instanceof VerityDemonEntity && !(demon = (VerityDemonEntity)entity).level().isClientSide()) {
                isMonstrous = false;
                ServerLevel level = (ServerLevel)demon.level();
                WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
                data.verityKarma = 20.0f;
                data.setDirty();
                verityEntity.setVariant("happy");
                level.sendParticles((ParticleOptions)ParticleTypes.TOTEM_OF_UNDYING, demon.getX(), demon.getY() + 1.0, demon.getZ(), 100, 0.5, 1.0, 0.5, 0.2);
                level.playSound((Player)null, demon.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(verityEntity, new PlayTtsPayload(verityEntity.getId(), "The darkness... it's gone. Thank you."));
                if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                    return;
                }
                level.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal(("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> The darkness... it's gone. Thank you.")), false);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDropsDrop(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            Iterator iterator = event.getDrops().iterator();
            while (iterator.hasNext()) {
                ItemEntity itemEntity = (ItemEntity)iterator.next();
                if (!itemEntity.getItem().is((Item)ModItems.FLASHLIGHT.get())) continue;
                iterator.remove();
            }
        }
    }

    public static void schedule(Runnable action, int delayInTicks) {
        PENDING_TASKS.add(new ScheduledTask(action, delayInTicks));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Player nearestPlayer;
        ServerLevel level;
        List list = PENDING_TASKS;
        synchronized (list) {
            if (!PENDING_TASKS.isEmpty()) {
                ACTIVE_TASKS.addAll(PENDING_TASKS);
                PENDING_TASKS.clear();
            }
        }
        Iterator iterator = ACTIVE_TASKS.iterator();
        while (iterator.hasNext()) {
            ScheduledTask st = (ScheduledTask)iterator.next();
            --st.ticksRemaining;
            if (st.ticksRemaining > 0) continue;
            st.task.run();
            iterator.remove();
        }
        if (verityEntity != null && !verityEntity.isRemoved() && !verityEntity.level().isClientSide()) {
            if (lonelinessTimer > 0) {
                --lonelinessTimer;
            } else {
                lonelinessTimer = 20;
                level = (ServerLevel)verityEntity.level();
                nearestPlayer = level.getNearestPlayer((Entity)verityEntity, 32.0);
                if (nearestPlayer == null) {
                    ModEvents.updateAndSyncKarma((ServerLevel)level, (float)-1.0f);
                if (!verityEntity.isRemoved()) {
                    verityEntity.setVariant("serious_1");
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(verityEntity, new PlayTtsPayload(verityEntity.getId(), "I'm alone... where did you go?"));
                    if (!((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                        verityEntity.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("verity.msg.alone", VerityConfig.VERITY_CUSTOM_NAME.get()), false);
                    }
                }
                } else {
                    lonelinessTimer = 3000;
                }
            }
        }
        if (hasSpawned && verityEntity != null && !verityEntity.isRemoved() && !verityEntity.level().isClientSide()) {
            if (idleChatTimer > 0) {
                if (!verityEntity.isOnFire()) {
                    --idleChatTimer;
                }
            } else {
                idleChatTimer = 2400 + RandomSource.create().nextInt(2400);
                level = (ServerLevel)verityEntity.level();
                nearestPlayer = level.getNearestPlayer((Entity)verityEntity, 32.0);
                if (nearestPlayer != null && nearestPlayer instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)nearestPlayer;
                    if (!((Boolean)verityEntity.getEntityData().get(VerityEntity.IS_TALKING)).booleanValue()) {
                        verityEntity.startTalking(80);
                        String idlePrompt = "<SYSTEM> You are bored and deciding to start a conversation with the player out of the blue. Comment on the current environment, ask the player a question, or say something random fitting your current personality and current day.";
                        AiManager.queryAI(verityEntity, idlePrompt, serverPlayer);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(CanPlayerSleepEvent event) {
        Player p = event.getEntity();
        if (!p.level().isClientSide()) {
            AABB searchBox = p.getBoundingBox().inflate(64.0);
            List nearbyDemons = p.level().getEntities(EntityTypeTest.forClass(VerityDemonEntity.class), searchBox, e -> true);
            if (!nearbyDemons.isEmpty()) {
                event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
                p.displayClientMessage((Component)Component.translatable("verity.msg.cannot_rest"), true);
            }
        }
    }

    @SubscribeEvent
    public static void entitySpawnEvent(EntityJoinLevelEvent event) {
        boolean shouldKillEntity = RandomSource.create().nextBoolean();
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (event.getEntity().getType() == ModEntities.VERITY_ENTITY.get()) {
            hasSpawned = true;
            verityEntity = (VerityEntity)event.getEntity();
        } else {
            Entity entity = event.getEntity();
            if (entity instanceof Villager) {
                Villager v = (Villager)entity;
                if (((Boolean)VerityConfig.KILL_VILLAGERS.get()).booleanValue()) {
                    v.kill();
                }
            } else {
                entity = event.getEntity();
                if (entity instanceof Cow) {
                    Cow c = (Cow)entity;
                    if (shouldKillEntity) {
                        c.kill();
                    }
                } else {
                    entity = event.getEntity();
                    if (entity instanceof Sheep) {
                        Sheep s = (Sheep)entity;
                        if (!shouldKillEntity) {
                            return;
                        }
                        s.kill();
                    } else {
                        entity = event.getEntity();
                        if (entity instanceof Pig) {
                            Pig p = (Pig)entity;
                            if (!shouldKillEntity) {
                                return;
                            }
                            p.kill();
                        } else {
                            entity = event.getEntity();
                            if (entity instanceof Chicken) {
                                Chicken c = (Chicken)entity;
                                if (!shouldKillEntity) {
                                    return;
                                }
                                c.kill();
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return;
        }
        Entity entity = event.getTarget();
        if (entity instanceof VerityEntity) {
            VerityEntity vEntity = (VerityEntity)entity;
            if (!player.getMainHandItem().isEmpty()) {
                return;
            }
            if (((Boolean)vEntity.getEntityData().get(VerityEntity.IS_TALKING)).booleanValue()) {
                if (!event.getLevel().isClientSide()) {
                    player.sendSystemMessage((Component)Component.translatable("verity.msg.while_talking"));
                    player.level().playSound((Player)null, player.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0f, 0.9f);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.FAIL);
                return;
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess((boolean)event.getLevel().isClientSide()));
            if (!event.getLevel().isClientSide()) {
                MinecraftServer server = vEntity.getServer();
                if (server != null) {
                    ResourceLocation soundToStop = ResourceLocation.fromNamespaceAndPath("verity", "verity_disc");
                    ClientboundStopSoundPacket stopSoundPacket = new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
                    server.getPlayerList().broadcastAll((Packet)stopSoundPacket);
                }
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag itemNbt = new CompoundTag();
                vEntity.saveWithoutId(itemNbt);
                itemNbt.putString("VerityVariant", vEntity.getVariant());
                itemNbt.putString("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(itemNbt));
                String name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
                if (!name.endsWith("\u2122")) {
                    name = name + "\u2122";
                }
                stack.set(DataComponents.CUSTOM_NAME, Component.literal(name));
                vEntity.discard();
                hasSpawned = false;
                vEntity.level().playSound((Player)null, vEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.addItem(stack);
            }
        } else {
            entity = event.getTarget();
            if (entity instanceof BoxEntity) {
                BoxEntity bEntity = (BoxEntity)entity;
                if (((Boolean)bEntity.getEntityData().get(BoxEntity.HAS_CLICKED)).booleanValue()) {
                    return;
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess((boolean)event.getLevel().isClientSide()));
                if (!event.getLevel().isClientSide()) {
                    bEntity.triggerOpen((ServerPlayer) player);
                    player.swing(hand);
                    bEntity.getEntityData().set(BoxEntity.HAS_CLICKED, true);
                    player.level().playSound((Player)null, bEntity.blockPosition(), (SoundEvent)ModSounds.BOX_CLICK.get(), SoundSource.BLOCKS, 0.7f, 1.0f);
                    ModEvents.schedule(() -> {
                        Level level = event.getLevel();
                        VerityEntity verity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).spawn((ServerLevel)level, bEntity.blockPosition(), MobSpawnType.MOB_SUMMONED);
                        if (verity != null) {
                            verityEntity = verity;
                            verity.variantArea((double)bEntity.blockPosition().getX() + 0.5, (double)bEntity.blockPosition().getY(), (double)bEntity.blockPosition().getZ() + 0.5, 0.0f, 0.0f);
                            verity.triggerBoxDrop();
                            verity.getPersistentData().putBoolean("WasThrown", false);
                            ServerLevel verityLevel = (ServerLevel)verity.level();
                            verityLevel.sendParticles((ParticleOptions)ParticleTypes.CLOUD, bEntity.getX(), bEntity.getY() + 1.0, bEntity.getZ(), 20, 0.25, 0.25, 0.25, 0.02);
                        }
                        player.level().playSound((Player)null, bEntity.blockPosition(), (SoundEvent)ModSounds.BOX_OPEN.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        bEntity.discard();
                    }, (int)40);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().playSound((Player)null, verityEntity.blockPosition(), (SoundEvent)ModSounds.IMPACT_1.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, (int)55);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().playSound((Player)null, verityEntity.blockPosition(), (SoundEvent)ModSounds.IMPACT_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, (int)75);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().playSound((Player)null, verityEntity.blockPosition(), (SoundEvent)ModSounds.IMPACT_2.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, (int)90);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerFirstJoin(PlayerEvent.PlayerLoggedInEvent event) {
        boolean hasFlashlight;
        Player player = event.getEntity();
        Level level = player.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        boolean bl = hasFlashlight = player.getInventory().items.stream().anyMatch(stack -> stack.is((Item)ModItems.FLASHLIGHT.get())) || player.getInventory().offhand.stream().anyMatch(stack -> stack.is((Item)ModItems.FLASHLIGHT.get()));
        if (!hasFlashlight) {
            player.getInventory().add(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()));
        }
        if (level2.dimension() != Level.OVERWORLD) {
            return;
        }
        WorldSpawnData data = WorldSpawnData.get((ServerLevel)level2);
        if (!data.hasSpawnedEntity) {
            isMonstrous = false;
            data.verityKarma = 10.0f;
            player.sendSystemMessage((Component)Component.translatable("verity.msg.need_help_tutorial"));
            MutableComponent message = Component.translatable("verity.msg.groq_tutorial").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/_i4O7pyMlks")).withUnderlined(true)).append((Component)Component.translatable("verity.msg.tutorial_easy").withStyle(ChatFormatting.AQUA));
            player.sendSystemMessage((Component)message);
            MutableComponent ollamaMessage = Component.translatable("verity.msg.ollama_tutorial").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=515I23cVBIM&t=24s")).withUnderlined(true)).append((Component)Component.translatable("verity.msg.tutorial_local").withStyle(ChatFormatting.AQUA));
            player.sendSystemMessage((Component)ollamaMessage);
            BlockPos safeSpawnPos = ModEvents.findNearestLand((ServerLevel)level2, (BlockPos)player.blockPosition());
            ((EntityType)ModEntities.BOX_ENTITY.get()).spawn(level2, safeSpawnPos, MobSpawnType.MOB_SUMMONED);
            data.hasSpawnedEntity = true;
            data.setDirty();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        boolean hasFlashlight;
        if (event.getEntity().level().isClientSide) {
            return;
        }
        Player player = event.getEntity();
        if (verityEntity != null && verityEntity.getOwnerUUID().isPresent() && player.getUUID().equals(verityEntity.getOwnerUUID().get())) {
            ItemStack stack2 = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
            CompoundTag itemNbt = new CompoundTag();
            verityEntity.saveWithoutId(itemNbt);
            itemNbt.putString("VerityVariant", verityEntity.getVariant());
            itemNbt.putString("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
            stack2.set(DataComponents.CUSTOM_DATA, CustomData.of(itemNbt));
            String name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
            if (!((String)name).endsWith("\u2122")) {
                name = (String)name + "\u2122";
            }
            stack2.set(DataComponents.CUSTOM_NAME, Component.literal(name));
            player.getInventory().add(stack2);
            hasSpawned = false;
            verityEntity.discard();
        }
        boolean bl = hasFlashlight = player.getInventory().items.stream().anyMatch(stack -> stack.is((Item)ModItems.FLASHLIGHT.get())) || player.getInventory().offhand.stream().anyMatch(stack -> stack.is((Item)ModItems.FLASHLIGHT.get()));
        if (!hasFlashlight) {
            player.getInventory().add(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()));
        }
    }

    private static BlockPos findNearestLand(ServerLevel level, BlockPos center) {
        int radius = 15;
        BlockPos bestPos = null;
        double shortestDist = Double.MAX_VALUE;
        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                double dist;
                BlockPos searchPos = center.offset(x, 0, z);
                BlockPos topPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, searchPos);
                BlockPos groundPos = topPos.below();
                BlockState groundState = level.getBlockState(groundPos);
                if (!groundState.getFluidState().isEmpty() || !((dist = center.distSqr((Vec3i)topPos)) > 3.0) || !(dist < shortestDist)) continue;
                shortestDist = dist;
                bestPos = topPos;
            }
        }
        if (bestPos == null) {
            return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, center.offset(3, 0, 3));
        }
        return bestPos;
    }

    @SubscribeEvent
    public static void onPlayerBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand;
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(hand = event.getHand());
        if (stack.getItem() == ModItems.VERITY_ITEM.get()) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess((boolean)event.getLevel().isClientSide()));
            String name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
            if (!event.getLevel().isClientSide()) {
                Direction face = event.getFace();
                BlockPos spawnPos = face != null ? event.getPos().relative(face) : event.getPos().above();
                ServerLevel level = (ServerLevel)event.getLevel();
                if (!level.getBlockState(spawnPos).isAir()) {
                    event.setCanceled(true);
                    return;
                }
                String variantToSpawn = "default";
                CompoundTag tagData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                if (tagData.contains("VerityVariant")) {
                    variantToSpawn = tagData.getString("VerityVariant");
                }
                player.swing(hand);
                stack.shrink(1);
                player.level().playSound((Player)null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);
                VerityEntity spawnedVerity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).spawn(level, spawnPos, MobSpawnType.MOB_SUMMONED);
                if (spawnedVerity != null) {
                    spawnedVerity.setVariant(variantToSpawn);
                    spawnedVerity.getPersistentData().putBoolean("WasThrown", false);
                    if (tagData.contains("VerityName")) {
                        spawnedVerity.setCustomName(Component.literal(tagData.getString("VerityName")));
                        spawnedVerity.setCustomNameVisible(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        if (!hasSpawned) {
            return;
        }
        String message = event.getMessage().getString();
        if (((Boolean)VerityConfig.REQUIRE_VERITY.get()).booleanValue() && !message.toLowerCase().contains(((String)VerityConfig.VERITY_CUSTOM_NAME.get()).toLowerCase())) {
            return;
        }
        ServerPlayer player = event.getPlayer();
        idleChatTimer = 2400 + RandomSource.create().nextInt(2400);
        if (verityEntity == null || verityEntity.isRemoved()) {
            return;
        }
        verityEntity.startTalking(80);
        String finalMessage = "<" + player.getName().getString() + "> " + message;
        AiManager.queryAI(verityEntity, finalMessage, player);
    }

    private static boolean isInventoryFull(Player player) {
        for (int i = 0; i < player.getInventory().items.size(); ++i) {
            if (!((ItemStack)player.getInventory().items.get(i)).isEmpty()) continue;
            return false;
        }
        return true;
    }

    private static String findNearestOre(ServerPlayer player, String type) {
        int r = 32;
        BlockPos center = player.blockPosition();
        BlockPos min = center.offset(-r, -r, -r);
        BlockPos max = center.offset(r, r, r);
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            double dist;
            boolean match;
            if (center.distSqr((Vec3i)pos) > (double)(r * r)) continue;
            BlockState state = player.level().getBlockState(pos);
            if (!(match = (switch (type.toLowerCase()) {
                case "diamond" -> state.is(BlockTags.DIAMOND_ORES);
                case "iron" -> state.is(BlockTags.IRON_ORES);
                case "gold" -> state.is(BlockTags.GOLD_ORES);
                case "coal" -> state.is(BlockTags.COAL_ORES);
                case "emerald" -> state.is(BlockTags.EMERALD_ORES);
                case "lapis" -> state.is(BlockTags.LAPIS_ORES);
                case "redstone" -> state.is(BlockTags.REDSTONE_ORES);
                case "copper" -> state.is(BlockTags.COPPER_ORES);
                default -> false;
            })) || !((dist = center.distSqr((Vec3i)pos)) < bestDist)) continue;
            bestDist = dist;
            best = pos.immutable();
        }
        if (best == null) {
            return "No " + type + " ore found nearby.";
        }
        return type + " ore at X=" + best.getX() + " Y=" + best.getY() + " Z=" + best.getZ();
    }

    public static void send(ServerPlayer player, Component msg) {
        if (msg.getString().length() > 1500) {
            msg = (Component)Component.literal(msg.getString().substring(0, 1500) + "...");
        }
        ModTriggers.TALK_TRIGGER.get().trigger(player);
        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            return;
        }
        player.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> ").append(msg), false);
    }

    public static void send(ServerPlayer player, String msg) {
        send(player, (Component)Component.literal(msg));
    }

    private static String extractJson(String raw) {
        int start = raw.indexOf(123);
        int end = raw.lastIndexOf(125);
        if (start != -1 && end != -1 && end >= start) {
            return raw.substring(start, end + 1);
        }
        return raw;
    }

    static {
        ENTITY_RADIUS = 32.0;
        verityEntity = null;
        HURT_COOLDOWN = new HashMap();
        transformFollowingDay = false;
        followPlayer = false;
        idleChatTimer = 3600;
        lonelinessTimer = 3000;
        isMonstrous = false;
        PENDING_TASKS = Collections.synchronizedList(new ArrayList());
        ACTIVE_TASKS = new ArrayList();
    }


    private static class ScheduledTask {
        final Runnable task;
        int ticksRemaining;

        ScheduledTask(Runnable task, int ticksRemaining) {
            this.task = task;
            this.ticksRemaining = ticksRemaining;
        }
    }

}
