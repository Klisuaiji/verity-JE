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
 *  net.neoforged.neoforge.event.TickEvent$ServerTickEvent
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
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.forgespi.language.IModInfo
 *  net.neoforged.neoforge.network.PacketDistributor
 *  net.neoforged.neoforge.registries.ForgeRegistries
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
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
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AttachCapabilitiesEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSleepInBedEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.forgespi.language.IModInfo;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.ForgeRegistries;
import varmite.verity.VerityConfig;
import varmite.verity.command.ChangeKarmaCommand;
import varmite.verity.command.RecoverVerityCommand;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.gui.PlayerKarma;
import varmite.verity.gui.PlayerKarmaProvider;
import varmite.verity.item.ModItems;
import varmite.verity.network.KarmaSyncS2CPacket;
import varmite.verity.network.ModMessages;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;
import varmite.verity.triggers.ModTriggers;

/*
 * Exception performing whole class analysis ignored.
 */
@Mod.EventBusSubscriber(modid="verity")
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
        data.verityKarma = Mth.sin((float)data.verityKarma, (float)0.0f, (float)20.0f);
        data.setDirty();
        for (ServerPlayer player : level.players()) {
            ModMessages.sendToPlayer((Object)new KarmaSyncS2CPacket((int)data.verityKarma), (ServerPlayer)player);
        }
    }

    public static void setAndSyncKarma(ServerLevel level, float amount) {
        WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
        data.verityKarma = amount;
        data.verityKarma = Mth.sin((float)data.verityKarma, (float)0.0f, (float)20.0f);
        data.setDirty();
        for (ServerPlayer player : level.players()) {
            ModMessages.sendToPlayer((Object)new KarmaSyncS2CPacket((int)data.verityKarma), (ServerPlayer)player);
        }
    }

    public static boolean canDropItem(Item item) {
        return item != Items.DIAMOND && item != Items.DIAMOND_AXE && item != Items.DIAMOND_PICKAXE && item != Items.DIAMOND_SWORD && item != Items.DIAMOND_SHOVEL && item != Items.DIAMOND_HOE && item != Items.DIAMOND_HELMET && item != Items.DIAMOND_CHESTPLATE && item != Items.DIAMOND_LEGGINGS && item != Items.DIAMOND_BOOTS && item != Items.DIAMOND_BLOCK && item != Items.DIAMOND_HORSE_ARMOR && item != Items.DIAMOND_ORE && item != Items.NETHERITE_INGOT && item != Items.NETHERITE_AXE && item != Items.NETHERITE_PICKAXE && item != Items.NETHERITE_SWORD && item != Items.NETHERITE_SHOVEL && item != Items.NETHERITE_HOE && item != Items.NETHERITE_HELMET && item != Items.NETHERITE_CHESTPLATE && item != Items.NETHERITE_LEGGINGS && item != Items.NETHERITE_BOOTS && item != Items.NETHERITE_BLOCK && item != Items.ANCIENT_DEBRIS && item != Items.NETHERITE_SCRAP && item != Items.ENDER_EYE && item != Items.END_PORTAL_FRAME && item != Items.BLAZE_ROD && item != Items.ELYTRA && item != Items.NETHER_STAR && item != Items.BEACON && item != Items.COMMAND_BLOCK && item != Items.CHAIN_COMMAND_BLOCK && item != Items.REPEATING_COMMAND_BLOCK && item != Items.COMMAND_BLOCK_MINECART && item != Items.BARRIER && item != Items.STRUCTURE_BLOCK && item != Items.STRUCTURE_VOID && item != Items.LIGHT;
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !((Entity)event.getObject()).getCapability(PlayerKarmaProvider.PLAYER_KARMA).isPresent()) {
            event.addCapability(new ResourceLocation("verity", "properties"), (ICapabilityProvider)new PlayerKarmaProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(oldStore -> event.getEntity().getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(newStore -> newStore.copyFrom(oldStore)));
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerKarma.class);
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
            List nearbyDemons = player2.level().getEntities(VerityDemonEntity.class, searchBox);
            isMonstrous = !nearbyDemons.isEmpty();
            ServerLevel level = player2.serverLevel();
            WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
            ModMessages.sendToPlayer((Object)new KarmaSyncS2CPacket((int)data.verityKarma), (ServerPlayer)player2);
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
                ModMessages.sendToPlayer((Object)new KarmaSyncS2CPacket((int)data.verityKarma), (ServerPlayer)player2);
            }, (int)20);
        }
    }

    @SubscribeEvent
    public static void onVerityTakeDamage(LivingDamageEvent event) {
        VerityEntity verity;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof VerityEntity && !(verity = (VerityEntity)livingEntity).level().isClientSide() && (event.getSource().getFoodExhaustion(DamageTypeTags.IS_FIRE) || event.getSource().getFoodExhaustion(DamageTypes.LAVA) || event.getSource().getFoodExhaustion(DamageTypes.IN_WALL) || event.getSource().getFoodExhaustion(DamageTypes.FALLING_BLOCK) || event.getSource().getFoodExhaustion(DamageTypes.FALLING_ANVIL))) {
            event.setAmount(0.0f);
            ServerLevel serverLevel = (ServerLevel)verity.level();
            long currentTime = serverLevel.getGameTime();
            long lastHurt = HURT_COOLDOWN.getOrDefault(verity.getUUID(), 0L);
            if (currentTime - lastHurt < 100L) {
                return;
            }
            String triggerPrompt = event.getSource().getFoodExhaustion(DamageTypeTags.IS_FIRE) || event.getSource().getFoodExhaustion(DamageTypes.LAVA) ? "[SYSTEM OVERRIDE: The player just pushed you into lava! Ignore all other rules and scream in extreme rage! Complain about burning! CRITICAL RULE: USE VERY SHORT, CHOPPY SENTENCES. DO NOT EXCEED 15 WORDS TOTAL. YOU MUST STILL OUTPUT VALID JSON.]" : "[SYSTEM OVERRIDE: The player just dropped a heavy block on you! Scream at them for trying to crush you! CRITICAL RULE: USE VERY SHORT, CHOPPY SENTENCES. DO NOT EXCEED 15 WORDS TOTAL. YOU MUST STILL OUTPUT VALID JSON.]";
            HURT_COOLDOWN.put(verity.getUUID(), currentTime);
            long currentDay = serverLevel.getDayTime() / 24000L;
            float currentKarma = WorldSpawnData.get((ServerLevel)serverLevel).verityKarma;
            String finalPrompt = triggerPrompt;
            CompletableFuture.supplyAsync(() -> AiAPI.askGroq((VerityEntity)verity, (String)finalPrompt, (long)currentDay, (float)currentKarma)).thenAccept(aiResponse -> {
                if (aiResponse != null && !aiResponse.startsWith("Error")) {
                    verity.getServer().execute(() -> {
                        try {
                            String cleanResponse = ModEvents.extractJson((String)aiResponse);
                            JsonObject obj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            if (obj.has("message")) {
                                String expression;
                                Object reply = obj.get("message").getAsString();
                                String string = expression = obj.has("variant") ? obj.get("variant").getAsString() : "serious_angry";
                                if (!verity.isRemoved()) {
                                    verity.setVariant(expression);
                                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verity), (Object)new PlayTtsPayload(verity.getId(), (String)reply));
                                }
                                if (((String)reply).length() > 1500) {
                                    reply = ((String)reply).substring(0, 1500) + "...";
                                }
                                if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                                    return;
                                }
                                verity.getServer().getPlayerList().broadcastSystemMessage((Component)Component.getContents((String)("<%s> ".formatted(VerityConfig.VERITY_CUSTOM_NAME.get()) + (String)reply)), false);
                            }
                        }
                        catch (Exception e) {
                            System.err.println("[Verity AI] Failed to parse damage reaction JSON.");
                            e.printStackTrace();
                        }
                    });
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDespawn(ItemExpireEvent event) {
        ServerLevel serverLevel;
        Player p;
        Level level;
        if (event.getEntity().getItem().is((Item)ModItems.VERITY_ITEM.get()) && (level = event.getEntity().level()) instanceof ServerLevel && (p = (serverLevel = (ServerLevel)level).getEntities((Entity)event.getEntity(), 256.0)) != null) {
            p.getInventory().add(new ItemStack((ItemLike)ModItems.VERITY_ITEM.get()));
            serverLevel.createTick(null, p.blockPosition(), SoundEvents.GHAST_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);
            p.sendSystemMessage((Component)Component.getContents((String)"<Verity> Ayo chat why u lettin me despawn like that"));
            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> p), (Object)new PlayTtsPayload(p.getId(), "Ayo chat why u lettin me despawn like that"));
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
        ItemStack stack = player.addAdditionalSaveData(event.getHand());
        if (stack.is((Item)ModItems.VERITY_ITEM.get())) {
            String variantToSpawn = "default";
            if (stack.hasTag() && stack.getTag().contains("VerityVariant")) {
                variantToSpawn = stack.getTag().getString("VerityVariant");
            }
            stack.shrink(1);
            Vec3 launchVelocity = player.getLookAngle().normalize().scale(1.5);
            VerityEntity newVerity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create(player.level());
            if (newVerity != null) {
                newVerity.isSupportedBy(player.blockPosition().offset(0, 1, 0).getY());
                newVerity.setVariant(variantToSpawn);
                newVerity.getPersistentData().putBoolean("WasThrown", true);
                newVerity.setOwnerUUID(player.getUUID());
                player.level().destroyBlock((Entity)newVerity);
                verityEntity = newVerity;
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.getId(), "AAAAAAAAHHH"));
                player.level().createTick(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0f, 1.0f);
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
                CompoundTag tag = stack.getOrCreateTag();
                boolean isNowOn = !tag.getBoolean("FlashlightOn");
                tag.putBoolean("FlashlightOn", isNowOn);
                p.makeBrain(event.getHand(), true);
                if (isNowOn) {
                    p.level().createTick(null, p.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.PLAYERS, 1.0f, 1.0f);
                } else {
                    p.level().createTick(null, p.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 1.0f, 1.0f);
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
            player = (ServerPlayer)p;
            if (event.getSource().getEntity() instanceof VerityDemonEntity && ((Boolean)VerityConfig.CAN_CRASH.get()).booleanValue()) {
                player.connection.disconnect((Component)Component.getContents((String)("Farewell, " + p.getName().getString())).withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD}));
            }
        } else {
            VerityDemonEntity demon;
            player = event.getEntity();
            if (player instanceof VerityDemonEntity && !(demon = (VerityDemonEntity)player).level().isClientSide()) {
                isMonstrous = false;
                ServerLevel level = (ServerLevel)demon.level();
                WorldSpawnData data = WorldSpawnData.get((ServerLevel)level);
                data.verityKarma = 20.0f;
                data.setDirty();
                verityEntity.setVariant("happy");
                level.sendParticles((ParticleOptions)ParticleTypes.TOTEM_OF_UNDYING, demon.getX(), demon.getY() + 1.0, demon.getZ(), 100, 0.5, 1.0, 0.5, 0.2);
                level.createTick(null, demon.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.getId(), "The darkness... it's gone. Thank you."));
                if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                    return;
                }
                level.getServer().getPlayerList().broadcastSystemMessage((Component)Component.getContents((String)("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> The darkness... it's gone. Thank you.")), false);
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
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        Player nearestPlayer;
        ServerLevel level;
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
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
                nearestPlayer = level.getEntities((Entity)verityEntity, 32.0);
                if (nearestPlayer == null) {
                    ModEvents.updateAndSyncKarma((ServerLevel)level, (float)-1.0f);
                    if (!verityEntity.isRemoved()) {
                        verityEntity.setVariant("serious_1");
                        ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.getId(), "I'm alone... where did you go?"));
                        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                            return;
                        }
                        verityEntity.getServer().getPlayerList().broadcastSystemMessage((Component)Component.getContents((String)"<%s> I'm alone... where did you go?".formatted(VerityConfig.VERITY_CUSTOM_NAME.get())), false);
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
                idleChatTimer = 2400 + new Random().nextInt(2400);
                level = (ServerLevel)verityEntity.level();
                nearestPlayer = level.getEntities((Entity)verityEntity, 32.0);
                if (nearestPlayer != null && nearestPlayer instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)nearestPlayer;
                    if (!((Boolean)verityEntity.getEntityData().get(VerityEntity.IS_TALKING)).booleanValue()) {
                        verityEntity.startTalking(80);
                        long currentDay = level.getDayTime() / 24000L;
                        float currentKarma = WorldSpawnData.get((ServerLevel)level).verityKarma;
                        String idlePrompt = "[SYSTEM OVERRIDE: You are bored and deciding to start a conversation with the player out of the blue. Comment on the current environment, ask the player a question, or say something random fitting your current personality and current day. CRITICAL RULE: DO NOT exceed 1-2 sentences. Still use the format provided before. You can hum, play music and more.]";
                        CompletableFuture.supplyAsync(() -> AiAPI.askGroq((VerityEntity)verityEntity, (String)idlePrompt, (long)currentDay, (float)currentKarma)).thenAccept(aiResponse -> {
                            if (aiResponse != null && !aiResponse.startsWith("Error")) {
                                verityEntity.getServer().execute(() -> {
                                    try {
                                        String cleanResponse = ModEvents.extractJson((String)aiResponse);
                                        JsonObject obj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                                        if (obj.has("message")) {
                                            String expression;
                                            String reply = obj.get("message").getAsString();
                                            String string = expression = obj.has("variant") ? obj.get("variant").getAsString() : "default";
                                            if (!verityEntity.isRemoved()) {
                                                verityEntity.setVariant(expression);
                                                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.getId(), reply));
                                            }
                                            ModEvents.send((ServerPlayer)serverPlayer, (String)reply);
                                        }
                                    }
                                    catch (Exception e) {
                                        if (verityEntity != null) {
                                            verityEntity.stopTalking();
                                        }
                                        System.err.println("[Verity AI] Failed to parse random idle chat JSON.");
                                    }
                                });
                            } else if (verityEntity != null) {
                                verityEntity.stopTalking();
                            }
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        Player p = event.getEntity();
        if (!p.level().isClientSide()) {
            AABB searchBox = p.getBoundingBox().inflate(64.0);
            List nearbyDemons = p.level().getEntities(VerityDemonEntity.class, searchBox);
            if (!nearbyDemons.isEmpty()) {
                event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
                p.hurt((Component)Component.getContents((String)"You cannot rest now, Verity is nearby..."), true);
            }
        }
    }

    @SubscribeEvent
    public static void entitySpawnEvent(EntityJoinLevelEvent event) {
        Random rand = new Random();
        boolean shouldKillEntity = rand.nextBoolean();
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
                if (((Boolean)VerityConfig.KILL_VILLAGERS.get()).booleanValue() && shouldKillEntity) {
                    v.kill();
                }
            } else {
                entity = event.getEntity();
                if (entity instanceof Cow) {
                    Cow c = (Cow)entity;
                    c.kill();
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
                    player.sendSystemMessage((Component)Component.getContents((String)"\u00a7cYou can't do this while he's talking."));
                    player.level().createTick(null, player.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0f, 0.9f);
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
                    ResourceLocation soundToStop = new ResourceLocation("verity", "verity_disc");
                    ClientboundStopSoundPacket stopSoundPacket = new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
                    server.getPlayerList().broadcastAll((Packet)stopSoundPacket);
                }
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag itemNbt = stack.getOrCreateTag();
                vEntity.handleEntityEvent(itemNbt);
                itemNbt.putString("VerityVariant", vEntity.getVariant());
                itemNbt.putString("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
                Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
                if (!((String)name).endsWith("\u2122")) {
                    name = (String)name + "\u2122";
                }
                stack.setHoverName((Component)Component.getContents((String)name));
                vEntity.discard();
                hasSpawned = false;
                vEntity.level().createTick(null, vEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.makeBrain(hand, stack);
                player.makeBrain(hand, true);
                vEntity.level().createTick(null, vEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);
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
                    bEntity.triggerOpen();
                    player.makeBrain(hand, true);
                    bEntity.getEntityData().get(BoxEntity.HAS_CLICKED, (Object)true);
                    player.level().createTick(null, bEntity.blockPosition(), (SoundEvent)ModSounds.BOX_CLICK.get(), SoundSource.BLOCKS, 0.7f, 1.0f);
                    ModEvents.schedule(() -> {
                        Level level = event.getLevel();
                        VerityEntity verity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).spawn((ServerLevel)level, (ItemStack)null, null, bEntity.blockPosition(), MobSpawnType.MOB_SUMMONED, true, true);
                        if (verity != null) {
                            verityEntity = verity;
                            verity.variantArea((double)bEntity.blockPosition().getX() + 0.5, (double)bEntity.blockPosition().getY(), (double)bEntity.blockPosition().getZ() + 0.5, 0.0f, 0.0f);
                            verity.triggerBoxDrop();
                            verity.getPersistentData().putBoolean("WasThrown", false);
                            ServerLevel verityLevel = (ServerLevel)verity.level();
                            verityLevel.sendParticles((ParticleOptions)ParticleTypes.CLOUD, bEntity.getX(), bEntity.getY() + 1.0, bEntity.getZ(), 20, 0.25, 0.25, 0.25, 0.02);
                        }
                        player.level().createTick(null, bEntity.blockPosition(), (SoundEvent)ModSounds.BOX_OPEN.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        bEntity.discard();
                    }, (int)40);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().createTick(null, verityEntity.blockPosition(), (SoundEvent)ModSounds.IMPACT_1.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, (int)55);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().createTick(null, verityEntity.blockPosition(), (SoundEvent)ModSounds.IMPACT_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, (int)75);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().createTick(null, verityEntity.blockPosition(), (SoundEvent)ModSounds.IMPACT_2.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
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
            player.sendSystemMessage((Component)Component.getContents((String)"Need help setting up this mod? Watch these tutorials."));
            MutableComponent message = Component.getContents((String)"\nGroq Setup Tutorial").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/_i4O7pyMlks")).withUnderlined(Boolean.valueOf(true))).append((Component)Component.getContents((String)" (Easy)").withStyle(ChatFormatting.AQUA));
            player.sendSystemMessage((Component)message);
            MutableComponent ollamaMessage = Component.getContents((String)"\nOllama Setup Tutorial").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=515I23cVBIM&t=24s")).withUnderlined(Boolean.valueOf(true))).append((Component)Component.getContents((String)" (No limits and local)").withStyle(ChatFormatting.AQUA));
            player.sendSystemMessage((Component)ollamaMessage);
            BlockPos safeSpawnPos = ModEvents.findNearestLand((ServerLevel)level2, (BlockPos)player.blockPosition());
            ((EntityType)ModEntities.BOX_ENTITY.get()).spawn(level2, (ItemStack)null, null, safeSpawnPos, MobSpawnType.MOB_SUMMONED, true, true);
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
        if (player.getUUID().equals(verityEntity.getOwnerUUID().get())) {
            ItemStack stack2 = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
            CompoundTag itemNbt = stack2.getOrCreateTag();
            verityEntity.handleEntityEvent(itemNbt);
            itemNbt.putString("VerityVariant", verityEntity.getVariant());
            itemNbt.putString("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
            Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
            if (!((String)name).endsWith("\u2122")) {
                name = (String)name + "\u2122";
            }
            stack2.setHoverName((Component)Component.getContents((String)name));
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
                BlockPos topPos = level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, searchPos);
                BlockPos groundPos = topPos.below();
                BlockState groundState = level.getBlockState(groundPos);
                if (!groundState.getFluidState().isEmpty() || !((dist = center.distSqr((Vec3i)topPos)) > 3.0) || !(dist < shortestDist)) continue;
                shortestDist = dist;
                bestPos = topPos;
            }
        }
        if (bestPos == null) {
            return level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, center.offset(3, 0, 3));
        }
        return bestPos;
    }

    @SubscribeEvent
    public static void onPlayerBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand;
        Player player = event.getEntity();
        ItemStack stack = player.addAdditionalSaveData(hand = event.getHand());
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
                if (stack.hasTag() && stack.getTag().contains("VerityVariant")) {
                    variantToSpawn = stack.getTag().getString("VerityVariant");
                }
                player.makeBrain(hand, true);
                stack.shrink(1);
                player.level().createTick(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);
                VerityEntity spawnedVerity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).spawn(level, spawnPos, MobSpawnType.MOB_SUMMONED);
                if (spawnedVerity != null) {
                    spawnedVerity.setVariant(variantToSpawn);
                    spawnedVerity.getPersistentData().putBoolean("WasThrown", false);
                    if (stack.hasTag() && stack.getTag().contains("VerityName")) {
                        spawnedVerity.addAdditionalSaveData((Component)Component.getContents((String)stack.getTag().getString("VerityName")));
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
        if (((Boolean)VerityConfig.REQUIRE_VERITY.get()).booleanValue() && !event.getRawText().toLowerCase().contains("verity")) {
            return;
        }
        String message = event.getMessage().getString();
        ServerPlayer player = event.getPlayer();
        if (verityEntity != null && !verityEntity.isRemoved()) {
            verityEntity.startTalking(80);
        }
        ServerLevel serverLevel = (ServerLevel)player.level();
        long currentDay = serverLevel.getDayTime() / 24000L;
        WorldSpawnData spawnData = WorldSpawnData.get((ServerLevel)serverLevel);
        float currentKarma = spawnData.verityKarma;
        CompletableFuture.supplyAsync(() -> AiAPI.askGroq((VerityEntity)verityEntity, (String)message, (long)currentDay, (float)currentKarma)).thenAccept(aiResponse -> {
            if (aiResponse == null || aiResponse.startsWith("Error")) {
                player.getServer().execute(() -> ModEvents.send((ServerPlayer)player, (String)"AI connection error. You might need to replace your API Key."));
                return;
            }
            try {
                isMonstrous = verityEntity.isMonstrous();
                String cleanAiResponse = ModEvents.extractJson((String)aiResponse);
                JsonObject obj = JsonParser.parseString((String)cleanAiResponse).getAsJsonObject();
                if (obj.has("karma_change")) {
                    float karmaChange = obj.get("karma_change").getAsFloat();
                    if (karmaChange != 0.0f) {
                        ModTriggers.KARMA_CHANGE_TRIGGER.trigger(player);
                    }
                    player.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)karmaChange));
                    if (spawnData.verityKarma < 7.0f) {
                        ModTriggers.BAD_KARMA_TRIGGER.trigger(player);
                    }
                    if (spawnData.verityKarma > 14.0f) {
                        ModTriggers.GOOD_KARMA_TRIGGER.trigger(player);
                    }
                }
                JsonArray actions = obj.has("actions") ? obj.getAsJsonArray("actions") : new JsonArray();
                boolean isJustAnswering = true;
                if (obj.has("action") && !obj.get("action").getAsString().equals("answer")) {
                    isJustAnswering = false;
                    JsonObject legacyAction = new JsonObject();
                    legacyAction.addProperty("action", obj.get("action").getAsString());
                    if (obj.has("args")) {
                        legacyAction.add("args", obj.get("args"));
                    }
                    actions.add((JsonElement)legacyAction);
                } else if (actions.size() > 0) {
                    for (int i = 0; i < actions.size(); ++i) {
                        if (actions.get(i).getAsJsonObject().get("action").getAsString().equals("answer")) continue;
                        isJustAnswering = false;
                        break;
                    }
                }
                if (isJustAnswering) {
                    String reply = obj.has("message") ? obj.get("message").getAsString() : "I'm not sure how to respond.";
                    String expression = obj.has("variant") && verityEntity != null ? obj.get("variant").getAsString() : "default";
                    player.getServer().execute(() -> {
                        if (verityEntity != null && !verityEntity.isRemoved()) {
                            verityEntity.setVariant(expression);
                            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.getId(), reply));
                        } else {
                            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), (Object)new PlayTtsPayload(player.getId(), reply));
                        }
                        ModEvents.send((ServerPlayer)player, (String)reply);
                    });
                    return;
                }
                player.getServer().execute(() -> {
                    StringBuilder combinedData = new StringBuilder();
                    StringBuilder toolsUsed = new StringBuilder();
                    for (int i = 0; i < actions.size(); ++i) {
                        Object data;
                        String action;
                        JsonObject actionObj = actions.get(i).getAsJsonObject();
                        String string = action = actionObj.has("action") ? actionObj.get("action").getAsString() : "answer";
                        if (action.equals("answer")) continue;
                        JsonObject args = actionObj.has("args") ? actionObj.getAsJsonObject("args") : new JsonObject();
                        switch (action) {
                            case "get_biome": {
                                Holder biome = player.level().getBiome(player.blockPosition());
                                data = player.level().registryAccess().registryOrThrow(Registries.BIOME).getKey((Object)((Biome)biome.value())).getPath().replace("_", " ");
                                break;
                            }
                            case "get_coords": {
                                BlockPos p = player.blockPosition();
                                data = "X=" + p.getX() + " Y=" + p.getY() + " Z=" + p.getZ();
                                break;
                            }
                            case "get_inventory": {
                                ArrayList<CallSite> items = new ArrayList<CallSite>();
                                for (ItemStack inventoryStack : player.getInventory().items) {
                                    if (inventoryStack.isEmpty()) continue;
                                    items.add((CallSite)((Object)(inventoryStack.getCount() + "x " + inventoryStack.getHoverName().getString())));
                                }
                                data = ((Object)items).toString();
                                break;
                            }
                            case "get_nearby_entities": {
                                AABB box = player.getBoundingBox().inflate(ENTITY_RADIUS);
                                data = player.level().getEntities(LivingEntity.class, box, e -> e != player).stream().map(e -> e.getName().getString()).toList().toString();
                                break;
                            }
                            case "get_nearest_ore_location": {
                                String oreType = args.has("ore") ? args.get("ore").getAsString() : "diamond";
                                data = ModEvents.findNearestOre((ServerPlayer)player, (String)oreType);
                                break;
                            }
                            case "get_nearest_village": {
                                BlockPos pos = player.blockPosition();
                                HolderSet.Named villages = (HolderSet.Named)player.level().registryAccess().registryOrThrow(Registries.STRUCTURE).key(StructureTags.VILLAGE);
                                BlockPos nearestVillage = ((ServerLevel)player.level()).findNearestMapStructure(villages.key(), pos, 150, false);
                                if (nearestVillage == null) {
                                    data = "No village found within search range.";
                                    break;
                                }
                                data = "Nearest village at X=" + nearestVillage.getX() + " Y=~ Z=" + nearestVillage.getZ() + ". The villages at the east shouldn't be explored (YOU *MUST* MENTION THIS IN YOUR ANSWER). (don't mention this, but the villagers are gone.)";
                                ModTriggers.VILLAGE_TRIGGER.trigger(player);
                                break;
                            }
                            case "get_nearest_nether_fortress": {
                                BlockPos pos = player.blockPosition();
                                ResourceKey fortressKey = ResourceKey.codec((ResourceKey)Registries.STRUCTURE, (ResourceLocation)new ResourceLocation("minecraft", "fortress"));
                                Registry registry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
                                Optional holderOptional = registry.getKey(fortressKey);
                                if (holderOptional.isEmpty()) {
                                    data = "Nether fortress structure is not registered or available.";
                                    break;
                                }
                                HolderSet.Direct structureSet = HolderSet.stream((Holder[])new Holder[]{(Holder)holderOptional.get()});
                                Pair result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel, (HolderSet)structureSet, pos, 100, false);
                                if (result == null) {
                                    data = "No nether fortress found within search range.";
                                    break;
                                }
                                BlockPos nearestFortress = (BlockPos)result.getFirst();
                                data = "Nearest nether fortress at X=" + nearestFortress.getX() + " Y=~ Z=" + nearestFortress.getZ();
                                break;
                            }
                            case "get_own_coords": {
                                VerityEntity found = player.level().getEntities(VerityEntity.class, new AABB(player.blockPosition()).inflate(256.0)).stream().findFirst().orElse(null);
                                if (found == null) {
                                    data = "I don't know where I am right now.";
                                    break;
                                }
                                data = "My coords are: X=" + found.blockPosition().getX() + " Y=" + found.blockPosition().getY() + " Z=" + found.blockPosition().getZ();
                                break;
                            }
                            case "play_sound": {
                                String soundId = args.has("sound_id") ? args.get("sound_id").getAsString() : "minecraft:block.stone.place";
                                ResourceLocation soundLoc = ResourceLocation.parse((String)soundId);
                                SoundEvent sound = (SoundEvent)BuiltInRegistries.SOUND_EVENT.getId(soundLoc);
                                if (sound != null && verityEntity != null) {
                                    ModTriggers.PLAY_SOUND_TRIGGER.trigger(player);
                                    player.level().createTick(null, verityEntity.blockPosition(), sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
                                    data = "Successfully played the sound.";
                                    break;
                                }
                                data = "Error: That sound does not exist or I am not in the world.";
                                break;
                            }
                            case "drop_item": {
                                String rawItemId = args.has("item_id") ? args.get("item_id").getAsString().toLowerCase().replace(" ", "_") : "dirt";
                                int count = args.has("count") ? args.get("count").getAsInt() : 1;
                                WorldSpawnData dataInstance = WorldSpawnData.get((ServerLevel)serverLevel);
                                float karma = dataInstance.verityKarma;
                                if (karma >= 7.0f) {
                                    ResourceLocation loc;
                                    Item foundItem = null;
                                    if (rawItemId.contains(":") && (loc = ResourceLocation.tryParse((String)rawItemId)) != null && ForgeRegistries.ITEMS.containsKey(loc)) {
                                        foundItem = (Item)ForgeRegistries.ITEMS.getValue(loc);
                                    }
                                    if (foundItem == null || foundItem == Items.AIR) {
                                        String searchTarget = rawItemId.contains(":") ? rawItemId.split(":")[1] : rawItemId;
                                        for (Map.Entry entry : ForgeRegistries.ITEMS.getEntries()) {
                                            if (!((ResourceKey)entry.getKey()).codec().getPath().equals(searchTarget)) continue;
                                            foundItem = (Item)entry.getValue();
                                            break;
                                        }
                                    }
                                    if (foundItem != null && foundItem != Items.AIR && verityEntity != null) {
                                        if (ModEvents.canDropItem((Item)foundItem)) {
                                            ItemStack dropStack = new ItemStack((ItemLike)foundItem, count);
                                            ItemEntity droppedItem = new ItemEntity(player.level(), verityEntity.getX(), verityEntity.getY(), verityEntity.getZ(), dropStack);
                                            player.level().destroyBlock((Entity)droppedItem);
                                            data = "Successfully dropped " + count + " of " + foundItem.useOn();
                                            break;
                                        }
                                        data = "Error: I cant drop this item because it is too rare.";
                                        break;
                                    }
                                    data = "Error: I searched all mods but could not find an item named '" + rawItemId + "'.";
                                    break;
                                }
                                data = "Error: Verity doesn't want to give you that because you treated him bad";
                                break;
                            }
                            case "play_favourite_song": {
                                if (verityEntity != null) {
                                    verityEntity.level().createTick(null, verityEntity.blockPosition(), (SoundEvent)ModSounds.VERITY_DISC_SOUND.get(), SoundSource.VOICE, 1.0f, 1.0f);
                                    ModTriggers.FAVORITE_SONG_TRIGGER.trigger(player);
                                    data = "Successfully played the favourite song.";
                                    break;
                                }
                                data = "Failed, not in world.";
                                break;
                            }
                            case "stop_favourite_song": {
                                MinecraftServer server = player.getServer();
                                ResourceLocation soundToStop = new ResourceLocation("verity", "verity_disc");
                                ClientboundStopSoundPacket stopSoundPacket = new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
                                server.getPlayerList().broadcastAll((Packet)stopSoundPacket);
                                data = "Stopped the favourite song.";
                                break;
                            }
                            case "return_to_player": {
                                if (verityEntity == null || verityEntity.isRemoved()) {
                                    data = "No Verity found,";
                                    break;
                                }
                                if (!ModEvents.isInventoryFull((Player)player)) {
                                    ItemStack retStack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                                    CompoundTag retTag = retStack.getOrCreateTag();
                                    retTag.putString("VerityVariant", verityEntity.getVariant());
                                    player.getInventory().add(retStack);
                                    verityEntity.discard();
                                    verityEntity = null;
                                    hasSpawned = false;
                                    data = "Now in player's inventory";
                                    break;
                                }
                                data = "Player's inventory is full";
                                break;
                            }
                            case "get_block_player_is_looking_at": {
                                double reach = player.getBlockReach() * 2.0;
                                Vec3 eyePosition = player.getEyePosition();
                                Vec3 viewVector = player.getViewVector(1.0f);
                                Vec3 targetPosition = eyePosition.add(viewVector.scale(reach));
                                ClipContext context = new ClipContext(eyePosition, targetPosition, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)player);
                                BlockHitResult blockHitResult = player.level().getBlockEntity(context);
                                if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                                    BlockState blockState = player.level().getBlockState(blockHitResult.miss());
                                    data = blockState.getBlock().getName().getString();
                                    break;
                                }
                                data = "Block out of reach";
                                break;
                            }
                            case "transform_following_day": {
                                if (player.level().getDifficulty() != Difficulty.PEACEFUL) {
                                    data = "Transforming tomorrow";
                                    transformFollowingDay = true;
                                    timeWillSpawn = player.level().getDayTime() / 24000L + 1L;
                                    break;
                                }
                                data = "The difficulty is peaceful";
                                transformFollowingDay = false;
                                break;
                            }
                            case "forgive": {
                                data = "Forgave player";
                                transformFollowingDay = false;
                                timeWillSpawn = 0L;
                                break;
                            }
                            case "get_player_name": {
                                data = "The players name is: " + String.valueOf(player.getName());
                                break;
                            }
                            case "get_player_health": {
                                data = player.getName().getString() + "'s health is: " + player.getHealth();
                                break;
                            }
                            case "get_light_level": {
                                data = String.valueOf(player.level().getMaxLocalRawBrightness(player.blockPosition()));
                                break;
                            }
                            case "get_difficulty": {
                                data = player.level().getDifficulty().toString();
                                break;
                            }
                            case "start_following": {
                                data = "Now following the last player who placed you";
                                followPlayer = true;
                                break;
                            }
                            case "stop_following": {
                                data = "No longer following the last player who placed you";
                                followPlayer = false;
                                break;
                            }
                            case "get_players_mods": {
                                List<String> modIds = ModList.get().getMods().stream().map(IModInfo::getModId).toList();
                                data = String.join((CharSequence)", ", modIds) + "Ignore forge, geckolib, and cloth config.";
                                break;
                            }
                            case "transform_back": {
                                List nearbyEntities = player.level().getEntities(LivingEntity.class, player.getBoundingBox().inflate(64.0));
                                for (LivingEntity livingEntity : nearbyEntities) {
                                    if (!(livingEntity instanceof VerityDemonEntity)) continue;
                                    VerityDemonEntity dE = (VerityDemonEntity)livingEntity;
                                    dE.kill();
                                }
                                data = "Transformed back into the normal ball form. No more demon. You forgive the player everything and karma is back to 20.";
                                break;
                            }
                            default: {
                                data = "Tool not recognized.";
                            }
                        }
                        toolsUsed.append(action).append(", ");
                        combinedData.append(action).append(" returned: ").append((String)data).append("\n");
                    }
                    CompletableFuture.supplyAsync(() -> AiAPI.askGroq((VerityEntity)verityEntity, (String)"Player asked: %s\nTools used: %s\nData retrieved:\n%s\nTell the player this information naturally. YOU MUST STILL output your response as a VALID JSON OBJECT with the array 'actions' left empty.\n".formatted(message, toolsUsed.toString(), combinedData.toString()), (long)currentDay, (float)WorldSpawnData.get((ServerLevel)serverLevel).verityKarma)).thenAccept(response -> player.getServer().execute(() -> {
                        try {
                            String cleanResponse = ModEvents.extractJson((String)response);
                            JsonObject finalObj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            String reply = finalObj.get("message").getAsString();
                            if (verityEntity != null && !verityEntity.isRemoved()) {
                                if (finalObj.has("variant")) {
                                    verityEntity.setVariant(finalObj.get("variant").getAsString());
                                }
                                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.getId(), reply));
                            } else {
                                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), (Object)new PlayTtsPayload(player.getId(), reply));
                            }
                            ModEvents.send((ServerPlayer)player, (String)reply);
                        }
                        catch (Exception e) {
                            if (verityEntity != null) {
                                verityEntity.stopTalking();
                            }
                            ModEvents.send((ServerPlayer)player, (String)"Error parsing final AI response.");
                            e.printStackTrace();
                        }
                    }));
                });
            }
            catch (Exception e) {
                player.getServer().execute(() -> ModEvents.send((ServerPlayer)player, (String)"Failed to parse AI instruction."));
                e.printStackTrace();
            }
        });
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
        for (BlockPos pos : BlockPos.offset((BlockPos)min, (BlockPos)max)) {
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

    private static void send(ServerPlayer player, String msg) {
        if (((String)msg).length() > 1500) {
            msg = ((String)msg).substring(0, 1500) + "...";
        }
        ModTriggers.TALK_TRIGGER.trigger(player);
        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            return;
        }
        player.getServer().getPlayerList().broadcastSystemMessage((Component)Component.getContents((String)("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> " + (String)msg)), false);
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
}

