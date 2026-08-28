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
 *  net.minecraft.commands.CommandSourceStack
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
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.event.ServerChatEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.event.entity.item.ItemExpireEvent
 *  net.minecraftforge.event.entity.living.LivingDamageEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingDropsEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$Clone
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerRespawnEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.minecraftforge.event.entity.player.PlayerSleepInBedEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.forgespi.language.IModInfo
 *  net.minecraftforge.network.PacketDistributor
 *  net.minecraftforge.registries.ForgeRegistries
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
import net.minecraft.commands.CommandSourceStack;
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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import varmite.verity.VerityConfig;
import varmite.verity.command.ChangeKarmaCommand;
import varmite.verity.command.RecoverVerityCommand;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;
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
        WorldSpawnData data = WorldSpawnData.get(level);
        data.verityKarma += amount;
        data.verityKarma = Mth.m_14036_((float)data.verityKarma, (float)0.0f, (float)20.0f);
        data.m_77762_();
        for (ServerPlayer player : level.m_6907_()) {
            ModMessages.sendToPlayer(new KarmaSyncS2CPacket((int)data.verityKarma), player);
        }
    }

    public static void setAndSyncKarma(ServerLevel level, float amount) {
        WorldSpawnData data = WorldSpawnData.get(level);
        data.verityKarma = amount;
        data.verityKarma = Mth.m_14036_((float)data.verityKarma, (float)0.0f, (float)20.0f);
        data.m_77762_();
        for (ServerPlayer player : level.m_6907_()) {
            ModMessages.sendToPlayer(new KarmaSyncS2CPacket((int)data.verityKarma), player);
        }
    }

    public static boolean canDropItem(Item item) {
        return item != Items.f_42415_ && item != Items.f_42391_ && item != Items.f_42390_ && item != Items.f_42388_ && item != Items.f_42389_ && item != Items.f_42392_ && item != Items.f_42472_ && item != Items.f_42473_ && item != Items.f_42474_ && item != Items.f_42475_ && item != Items.f_41959_ && item != Items.f_42653_ && item != Items.f_42010_ && item != Items.f_42418_ && item != Items.f_42396_ && item != Items.f_42395_ && item != Items.f_42393_ && item != Items.f_42394_ && item != Items.f_42397_ && item != Items.f_42480_ && item != Items.f_42481_ && item != Items.f_42482_ && item != Items.f_42483_ && item != Items.f_42791_ && item != Items.f_42792_ && item != Items.f_42419_ && item != Items.f_42545_ && item != Items.f_42101_ && item != Items.f_42585_ && item != Items.f_42741_ && item != Items.f_42686_ && item != Items.f_42065_ && item != Items.f_42116_ && item != Items.f_42257_ && item != Items.f_42256_ && item != Items.f_42657_ && item != Items.f_42127_ && item != Items.f_42352_ && item != Items.f_42263_ && item != Items.f_151033_;
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !((Entity)event.getObject()).getCapability(PlayerKarmaProvider.PLAYER_KARMA).isPresent()) {
            event.addCapability(new ResourceLocation("verity", "properties"), (ICapabilityProvider)new PlayerKarmaProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(oldStore -> event.getEntity().getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(newStore -> newStore.copyFrom((PlayerKarma)oldStore)));
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerKarma.class);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        RecoverVerityCommand.register((CommandDispatcher<CommandSourceStack>)event.getDispatcher());
        ChangeKarmaCommand.register((CommandDispatcher<CommandSourceStack>)event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        Player player;
        if (!event.getEntity().m_9236_().m_5776_() && (player = event.getEntity()) instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            AABB searchBox = player2.m_20191_().m_82400_(128.0);
            List nearbyDemons = player2.m_9236_().m_45976_(VerityDemonEntity.class, searchBox);
            isMonstrous = !nearbyDemons.isEmpty();
            ServerLevel level = player2.m_284548_();
            WorldSpawnData data = WorldSpawnData.get(level);
            ModMessages.sendToPlayer(new KarmaSyncS2CPacket((int)data.verityKarma), player2);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            ModEvents.schedule(() -> {
                ServerLevel level = player2.m_284548_();
                WorldSpawnData data = WorldSpawnData.get(level);
                boolean foundJson = false;
                for (int i = 0; i < data.chatHistory.size(); ++i) {
                    CompoundTag messageTag = data.chatHistory.m_128728_(i);
                    String content = messageTag.m_128461_("content");
                    if (!content.toLowerCase().contains("json")) continue;
                    foundJson = true;
                    break;
                }
                if (foundJson) {
                    data.chatHistory.clear();
                }
                ModMessages.sendToPlayer(new KarmaSyncS2CPacket((int)data.verityKarma), player2);
            }, 20);
        }
    }

    @SubscribeEvent
    public static void onVerityTakeDamage(LivingDamageEvent event) {
        VerityEntity verity;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof VerityEntity && !(verity = (VerityEntity)livingEntity).m_9236_().m_5776_() && (event.getSource().m_269533_(DamageTypeTags.f_268745_) || event.getSource().m_276093_(DamageTypes.f_268546_) || event.getSource().m_276093_(DamageTypes.f_268612_) || event.getSource().m_276093_(DamageTypes.f_268659_) || event.getSource().m_276093_(DamageTypes.f_268526_))) {
            event.setAmount(0.0f);
            ServerLevel serverLevel = (ServerLevel)verity.m_9236_();
            long currentTime = serverLevel.m_46467_();
            long lastHurt = HURT_COOLDOWN.getOrDefault(verity.m_20148_(), 0L);
            if (currentTime - lastHurt < 100L) {
                return;
            }
            String triggerPrompt = event.getSource().m_269533_(DamageTypeTags.f_268745_) || event.getSource().m_276093_(DamageTypes.f_268546_) ? "[SYSTEM OVERRIDE: The player just pushed you into lava! Ignore all other rules and scream in extreme rage! Complain about burning! CRITICAL RULE: USE VERY SHORT, CHOPPY SENTENCES. DO NOT EXCEED 15 WORDS TOTAL. YOU MUST STILL OUTPUT VALID JSON.]" : "[SYSTEM OVERRIDE: The player just dropped a heavy block on you! Scream at them for trying to crush you! CRITICAL RULE: USE VERY SHORT, CHOPPY SENTENCES. DO NOT EXCEED 15 WORDS TOTAL. YOU MUST STILL OUTPUT VALID JSON.]";
            HURT_COOLDOWN.put(verity.m_20148_(), currentTime);
            long currentDay = serverLevel.m_46468_() / 24000L;
            float currentKarma = WorldSpawnData.get((ServerLevel)serverLevel).verityKarma;
            String finalPrompt = triggerPrompt;
            CompletableFuture.supplyAsync(() -> AiAPI.askGroq(verity, finalPrompt, currentDay, currentKarma)).thenAccept(aiResponse -> {
                if (aiResponse != null && !aiResponse.startsWith("Error")) {
                    verity.m_20194_().execute(() -> {
                        try {
                            String cleanResponse = ModEvents.extractJson(aiResponse);
                            JsonObject obj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            if (obj.has("message")) {
                                String expression;
                                Object reply = obj.get("message").getAsString();
                                String string = expression = obj.has("variant") ? obj.get("variant").getAsString() : "serious_angry";
                                if (!verity.m_213877_()) {
                                    verity.setVariant(expression);
                                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verity), (Object)new PlayTtsPayload(verity.m_19879_(), (String)reply));
                                }
                                if (((String)reply).length() > 1500) {
                                    reply = ((String)reply).substring(0, 1500) + "...";
                                }
                                if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                                    return;
                                }
                                verity.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)("<%s> ".formatted(VerityConfig.VERITY_CUSTOM_NAME.get()) + (String)reply)), false);
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
        if (event.getEntity().m_32055_().m_150930_((Item)ModItems.VERITY_ITEM.get()) && (level = event.getEntity().m_9236_()) instanceof ServerLevel && (p = (serverLevel = (ServerLevel)level).m_45930_((Entity)event.getEntity(), 256.0)) != null) {
            p.m_150109_().m_36054_(new ItemStack((ItemLike)ModItems.VERITY_ITEM.get()));
            serverLevel.m_5594_(null, p.m_20183_(), SoundEvents.f_11921_, SoundSource.PLAYERS, 1.0f, 1.0f);
            p.m_213846_((Component)Component.m_237113_((String)"<Verity> Ayo chat why u lettin me despawn like that"));
            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> p), (Object)new PlayTtsPayload(p.m_19879_(), "Ayo chat why u lettin me despawn like that"));
        }
    }

    @SubscribeEvent
    public static void rightClickAir(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().m_5776_()) {
            return;
        }
        Player player = event.getEntity();
        if (!player.m_6047_()) {
            return;
        }
        ItemStack stack = player.m_21120_(event.getHand());
        if (stack.m_150930_((Item)ModItems.VERITY_ITEM.get())) {
            String variantToSpawn = "default";
            if (stack.m_41782_() && stack.m_41783_().m_128441_("VerityVariant")) {
                variantToSpawn = stack.m_41783_().m_128461_("VerityVariant");
            }
            stack.m_41774_(1);
            Vec3 launchVelocity = player.m_20154_().m_82541_().m_82490_(1.5);
            VerityEntity newVerity = (VerityEntity)ModEntities.VERITY_ENTITY.get().m_20615_(player.m_9236_());
            if (newVerity != null) {
                newVerity.m_20219_(player.m_20183_().m_7918_(0, 1, 0).m_252807_());
                newVerity.setVariant(variantToSpawn);
                newVerity.getPersistentData().m_128379_("WasThrown", true);
                newVerity.setOwnerUUID(player.m_20148_());
                player.m_9236_().m_7967_((Entity)newVerity);
                verityEntity = newVerity;
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.m_19879_(), "AAAAAAAAHHH"));
                player.m_9236_().m_5594_(null, player.m_20183_(), SoundEvents.f_11893_, SoundSource.PLAYERS, 1.0f, 1.0f);
                newVerity.m_20256_(launchVelocity);
                newVerity.f_19864_ = true;
            }
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (stack.m_150930_((Item)ModItems.FLASHLIGHT.get())) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            if (!event.getLevel().m_5776_()) {
                Player p = event.getEntity();
                CompoundTag tag = stack.m_41784_();
                boolean isNowOn = !tag.m_128471_("FlashlightOn");
                tag.m_128379_("FlashlightOn", isNowOn);
                p.m_21011_(event.getHand(), true);
                if (isNowOn) {
                    p.m_9236_().m_5594_(null, p.m_20183_(), SoundEvents.f_244067_, SoundSource.PLAYERS, 1.0f, 1.0f);
                } else {
                    p.m_9236_().m_5594_(null, p.m_20183_(), SoundEvents.f_244603_, SoundSource.PLAYERS, 1.0f, 1.0f);
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
            if (event.getSource().m_7639_() instanceof VerityDemonEntity && ((Boolean)VerityConfig.CAN_CRASH.get()).booleanValue()) {
                player.f_8906_.m_9942_((Component)Component.m_237113_((String)("Farewell, " + p.m_7755_().getString())).m_130944_(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD}));
            }
        } else {
            VerityDemonEntity demon;
            player = event.getEntity();
            if (player instanceof VerityDemonEntity && !(demon = (VerityDemonEntity)player).m_9236_().m_5776_()) {
                isMonstrous = false;
                ServerLevel level = (ServerLevel)demon.m_9236_();
                WorldSpawnData data = WorldSpawnData.get(level);
                data.verityKarma = 20.0f;
                data.m_77762_();
                verityEntity.setVariant("happy");
                level.m_8767_((ParticleOptions)ParticleTypes.f_123767_, demon.m_20185_(), demon.m_20186_() + 1.0, demon.m_20189_(), 100, 0.5, 1.0, 0.5, 0.2);
                level.m_5594_(null, demon.m_20183_(), SoundEvents.f_12513_, SoundSource.NEUTRAL, 1.0f, 1.0f);
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.m_19879_(), "The darkness... it's gone. Thank you."));
                if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                    return;
                }
                level.m_7654_().m_6846_().m_240416_((Component)Component.m_237113_((String)("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> The darkness... it's gone. Thank you.")), false);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDropsDrop(LivingDropsEvent event) {
        if (event.getEntity().m_9236_().f_46443_) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            Iterator iterator = event.getDrops().iterator();
            while (iterator.hasNext()) {
                ItemEntity itemEntity = (ItemEntity)iterator.next();
                if (!itemEntity.m_32055_().m_150930_((Item)ModItems.FLASHLIGHT.get())) continue;
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
        List<ScheduledTask> list = PENDING_TASKS;
        synchronized (list) {
            if (!PENDING_TASKS.isEmpty()) {
                ACTIVE_TASKS.addAll(PENDING_TASKS);
                PENDING_TASKS.clear();
            }
        }
        Iterator<ScheduledTask> iterator = ACTIVE_TASKS.iterator();
        while (iterator.hasNext()) {
            ScheduledTask st = iterator.next();
            --st.ticksRemaining;
            if (st.ticksRemaining > 0) continue;
            st.task.run();
            iterator.remove();
        }
        if (verityEntity != null && !verityEntity.m_213877_() && !verityEntity.m_9236_().m_5776_()) {
            if (lonelinessTimer > 0) {
                --lonelinessTimer;
            } else {
                lonelinessTimer = 20;
                level = (ServerLevel)verityEntity.m_9236_();
                nearestPlayer = level.m_45930_((Entity)verityEntity, 32.0);
                if (nearestPlayer == null) {
                    ModEvents.updateAndSyncKarma(level, -1.0f);
                    if (!verityEntity.m_213877_()) {
                        verityEntity.setVariant("serious_1");
                        ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.m_19879_(), "I'm alone... where did you go?"));
                        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                            return;
                        }
                        verityEntity.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)"<%s> I'm alone... where did you go?".formatted(VerityConfig.VERITY_CUSTOM_NAME.get())), false);
                    }
                } else {
                    lonelinessTimer = 3000;
                }
            }
        }
        if (hasSpawned && verityEntity != null && !verityEntity.m_213877_() && !verityEntity.m_9236_().m_5776_()) {
            if (idleChatTimer > 0) {
                if (!verityEntity.m_6060_()) {
                    --idleChatTimer;
                }
            } else {
                idleChatTimer = 2400 + new Random().nextInt(2400);
                level = (ServerLevel)verityEntity.m_9236_();
                nearestPlayer = level.m_45930_((Entity)verityEntity, 32.0);
                if (nearestPlayer != null && nearestPlayer instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)nearestPlayer;
                    if (!((Boolean)verityEntity.m_20088_().m_135370_(VerityEntity.IS_TALKING)).booleanValue()) {
                        verityEntity.startTalking(80);
                        long currentDay = level.m_46468_() / 24000L;
                        float currentKarma = WorldSpawnData.get((ServerLevel)level).verityKarma;
                        String idlePrompt = "[SYSTEM OVERRIDE: You are bored and deciding to start a conversation with the player out of the blue. Comment on the current environment, ask the player a question, or say something random fitting your current personality and current day. CRITICAL RULE: DO NOT exceed 1-2 sentences. Still use the format provided before. You can hum, play music and more.]";
                        CompletableFuture.supplyAsync(() -> AiAPI.askGroq(verityEntity, idlePrompt, currentDay, currentKarma)).thenAccept(aiResponse -> {
                            if (aiResponse != null && !aiResponse.startsWith("Error")) {
                                verityEntity.m_20194_().execute(() -> {
                                    try {
                                        String cleanResponse = ModEvents.extractJson(aiResponse);
                                        JsonObject obj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                                        if (obj.has("message")) {
                                            String expression;
                                            String reply = obj.get("message").getAsString();
                                            String string = expression = obj.has("variant") ? obj.get("variant").getAsString() : "default";
                                            if (!verityEntity.m_213877_()) {
                                                verityEntity.setVariant(expression);
                                                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.m_19879_(), reply));
                                            }
                                            ModEvents.send(serverPlayer, reply);
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
        if (!p.m_9236_().m_5776_()) {
            AABB searchBox = p.m_20191_().m_82400_(64.0);
            List nearbyDemons = p.m_9236_().m_45976_(VerityDemonEntity.class, searchBox);
            if (!nearbyDemons.isEmpty()) {
                event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
                p.m_5661_((Component)Component.m_237113_((String)"You cannot rest now, Verity is nearby..."), true);
            }
        }
    }

    @SubscribeEvent
    public static void entitySpawnEvent(EntityJoinLevelEvent event) {
        Random rand = new Random();
        boolean shouldKillEntity = rand.nextBoolean();
        if (event.getLevel().m_5776_()) {
            return;
        }
        if (event.getEntity().m_6095_() == ModEntities.VERITY_ENTITY.get()) {
            hasSpawned = true;
            verityEntity = (VerityEntity)event.getEntity();
        } else {
            Entity entity = event.getEntity();
            if (entity instanceof Villager) {
                Villager v = (Villager)entity;
                if (((Boolean)VerityConfig.KILL_VILLAGERS.get()).booleanValue() && shouldKillEntity) {
                    v.m_6074_();
                }
            } else {
                entity = event.getEntity();
                if (entity instanceof Cow) {
                    Cow c = (Cow)entity;
                    c.m_6074_();
                } else {
                    entity = event.getEntity();
                    if (entity instanceof Sheep) {
                        Sheep s = (Sheep)entity;
                        if (!shouldKillEntity) {
                            return;
                        }
                        s.m_6074_();
                    } else {
                        entity = event.getEntity();
                        if (entity instanceof Pig) {
                            Pig p = (Pig)entity;
                            if (!shouldKillEntity) {
                                return;
                            }
                            p.m_6074_();
                        } else {
                            entity = event.getEntity();
                            if (entity instanceof Chicken) {
                                Chicken c = (Chicken)entity;
                                if (!shouldKillEntity) {
                                    return;
                                }
                                c.m_6074_();
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
            if (!player.m_21205_().m_41619_()) {
                return;
            }
            if (((Boolean)vEntity.m_20088_().m_135370_(VerityEntity.IS_TALKING)).booleanValue()) {
                if (!event.getLevel().m_5776_()) {
                    player.m_213846_((Component)Component.m_237113_((String)"\u00a7cYou can't do this while he's talking."));
                    player.m_9236_().m_5594_(null, player.m_20183_(), SoundEvents.f_12507_, SoundSource.PLAYERS, 1.0f, 0.9f);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.FAIL);
                return;
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.m_19078_((boolean)event.getLevel().m_5776_()));
            if (!event.getLevel().m_5776_()) {
                MinecraftServer server = vEntity.m_20194_();
                if (server != null) {
                    ResourceLocation soundToStop = new ResourceLocation("verity", "verity_disc");
                    ClientboundStopSoundPacket stopSoundPacket = new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
                    server.m_6846_().m_11268_((Packet)stopSoundPacket);
                }
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag itemNbt = stack.m_41784_();
                vEntity.m_7380_(itemNbt);
                itemNbt.m_128359_("VerityVariant", vEntity.getVariant());
                itemNbt.m_128359_("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
                Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
                if (!((String)name).endsWith("\u2122")) {
                    name = (String)name + "\u2122";
                }
                stack.m_41714_((Component)Component.m_237113_((String)name));
                vEntity.m_146870_();
                hasSpawned = false;
                vEntity.m_9236_().m_5594_(null, vEntity.m_20183_(), SoundEvents.f_12019_, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.m_21008_(hand, stack);
                player.m_21011_(hand, true);
                vEntity.m_9236_().m_5594_(null, vEntity.m_20183_(), SoundEvents.f_12019_, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        } else {
            entity = event.getTarget();
            if (entity instanceof BoxEntity) {
                BoxEntity bEntity = (BoxEntity)entity;
                if (((Boolean)bEntity.m_20088_().m_135370_(BoxEntity.HAS_CLICKED)).booleanValue()) {
                    return;
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.m_19078_((boolean)event.getLevel().m_5776_()));
                if (!event.getLevel().m_5776_()) {
                    bEntity.triggerOpen();
                    player.m_21011_(hand, true);
                    bEntity.m_20088_().m_135381_(BoxEntity.HAS_CLICKED, (Object)true);
                    player.m_9236_().m_5594_(null, bEntity.m_20183_(), (SoundEvent)ModSounds.BOX_CLICK.get(), SoundSource.BLOCKS, 0.7f, 1.0f);
                    ModEvents.schedule(() -> {
                        Level level = event.getLevel();
                        VerityEntity verity = (VerityEntity)ModEntities.VERITY_ENTITY.get().m_20592_((ServerLevel)level, (ItemStack)null, null, bEntity.m_20183_(), MobSpawnType.MOB_SUMMONED, true, true);
                        if (verity != null) {
                            verityEntity = verity;
                            verity.m_7678_((double)bEntity.m_20183_().m_123341_() + 0.5, bEntity.m_20183_().m_123342_(), (double)bEntity.m_20183_().m_123343_() + 0.5, 0.0f, 0.0f);
                            verity.triggerBoxDrop();
                            verity.getPersistentData().m_128379_("WasThrown", false);
                            ServerLevel verityLevel = (ServerLevel)verity.m_9236_();
                            verityLevel.m_8767_((ParticleOptions)ParticleTypes.f_123796_, bEntity.m_20185_(), bEntity.m_20186_() + 1.0, bEntity.m_20189_(), 20, 0.25, 0.25, 0.25, 0.02);
                        }
                        player.m_9236_().m_5594_(null, bEntity.m_20183_(), (SoundEvent)ModSounds.BOX_OPEN.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        bEntity.m_146870_();
                    }, 40);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.m_9236_().m_5594_(null, verityEntity.m_20183_(), (SoundEvent)ModSounds.IMPACT_1.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 55);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.m_9236_().m_5594_(null, verityEntity.m_20183_(), (SoundEvent)ModSounds.IMPACT_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 75);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.m_9236_().m_5594_(null, verityEntity.m_20183_(), (SoundEvent)ModSounds.IMPACT_2.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 90);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerFirstJoin(PlayerEvent.PlayerLoggedInEvent event) {
        boolean hasFlashlight;
        Player player = event.getEntity();
        Level level = player.m_9236_();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        boolean bl = hasFlashlight = player.m_150109_().f_35974_.stream().anyMatch(stack -> stack.m_150930_((Item)ModItems.FLASHLIGHT.get())) || player.m_150109_().f_35976_.stream().anyMatch(stack -> stack.m_150930_((Item)ModItems.FLASHLIGHT.get()));
        if (!hasFlashlight) {
            player.m_150109_().m_36054_(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()));
        }
        if (level2.m_46472_() != Level.f_46428_) {
            return;
        }
        WorldSpawnData data = WorldSpawnData.get(level2);
        if (!data.hasSpawnedEntity) {
            isMonstrous = false;
            data.verityKarma = 10.0f;
            player.m_213846_((Component)Component.m_237113_((String)"Need help setting up this mod? Watch these tutorials."));
            MutableComponent message = Component.m_237113_((String)"\nGroq Setup Tutorial").m_130948_(Style.f_131099_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/_i4O7pyMlks")).m_131162_(Boolean.valueOf(true))).m_7220_((Component)Component.m_237113_((String)" (Easy)").m_130940_(ChatFormatting.AQUA));
            player.m_213846_((Component)message);
            MutableComponent ollamaMessage = Component.m_237113_((String)"\nOllama Setup Tutorial").m_130948_(Style.f_131099_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=515I23cVBIM&t=24s")).m_131162_(Boolean.valueOf(true))).m_7220_((Component)Component.m_237113_((String)" (No limits and local)").m_130940_(ChatFormatting.AQUA));
            player.m_213846_((Component)ollamaMessage);
            BlockPos safeSpawnPos = ModEvents.findNearestLand(level2, player.m_20183_());
            ModEntities.BOX_ENTITY.get().m_20592_(level2, (ItemStack)null, null, safeSpawnPos, MobSpawnType.MOB_SUMMONED, true, true);
            data.hasSpawnedEntity = true;
            data.m_77762_();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        boolean hasFlashlight;
        if (event.getEntity().m_9236_().f_46443_) {
            return;
        }
        Player player = event.getEntity();
        if (player.m_20148_().equals(verityEntity.getOwnerUUID().get())) {
            ItemStack stack2 = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
            CompoundTag itemNbt = stack2.m_41784_();
            verityEntity.m_7380_(itemNbt);
            itemNbt.m_128359_("VerityVariant", verityEntity.getVariant());
            itemNbt.m_128359_("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
            Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
            if (!((String)name).endsWith("\u2122")) {
                name = (String)name + "\u2122";
            }
            stack2.m_41714_((Component)Component.m_237113_((String)name));
            player.m_150109_().m_36054_(stack2);
            hasSpawned = false;
            verityEntity.m_146870_();
        }
        boolean bl = hasFlashlight = player.m_150109_().f_35974_.stream().anyMatch(stack -> stack.m_150930_((Item)ModItems.FLASHLIGHT.get())) || player.m_150109_().f_35976_.stream().anyMatch(stack -> stack.m_150930_((Item)ModItems.FLASHLIGHT.get()));
        if (!hasFlashlight) {
            player.m_150109_().m_36054_(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()));
        }
    }

    private static BlockPos findNearestLand(ServerLevel level, BlockPos center) {
        int radius = 15;
        BlockPos bestPos = null;
        double shortestDist = Double.MAX_VALUE;
        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                double dist;
                BlockPos searchPos = center.m_7918_(x, 0, z);
                BlockPos topPos = level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, searchPos);
                BlockPos groundPos = topPos.m_7495_();
                BlockState groundState = level.m_8055_(groundPos);
                if (!groundState.m_60819_().m_76178_() || !((dist = center.m_123331_((Vec3i)topPos)) > 3.0) || !(dist < shortestDist)) continue;
                shortestDist = dist;
                bestPos = topPos;
            }
        }
        if (bestPos == null) {
            return level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, center.m_7918_(3, 0, 3));
        }
        return bestPos;
    }

    @SubscribeEvent
    public static void onPlayerBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand;
        Player player = event.getEntity();
        ItemStack stack = player.m_21120_(hand = event.getHand());
        if (stack.m_41720_() == ModItems.VERITY_ITEM.get()) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.m_19078_((boolean)event.getLevel().m_5776_()));
            String name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
            if (!event.getLevel().m_5776_()) {
                Direction face = event.getFace();
                BlockPos spawnPos = face != null ? event.getPos().m_121945_(face) : event.getPos().m_7494_();
                ServerLevel level = (ServerLevel)event.getLevel();
                if (!level.m_8055_(spawnPos).m_60795_()) {
                    event.setCanceled(true);
                    return;
                }
                String variantToSpawn = "default";
                if (stack.m_41782_() && stack.m_41783_().m_128441_("VerityVariant")) {
                    variantToSpawn = stack.m_41783_().m_128461_("VerityVariant");
                }
                player.m_21011_(hand, true);
                stack.m_41774_(1);
                player.m_9236_().m_5594_(null, player.m_20183_(), SoundEvents.f_12019_, SoundSource.BLOCKS, 1.0f, 0.8f);
                VerityEntity spawnedVerity = (VerityEntity)ModEntities.VERITY_ENTITY.get().m_262496_(level, spawnPos, MobSpawnType.MOB_SUMMONED);
                if (spawnedVerity != null) {
                    spawnedVerity.setVariant(variantToSpawn);
                    spawnedVerity.getPersistentData().m_128379_("WasThrown", false);
                    if (stack.m_41782_() && stack.m_41783_().m_128441_("VerityName")) {
                        spawnedVerity.m_6593_((Component)Component.m_237113_((String)stack.m_41783_().m_128461_("VerityName")));
                        spawnedVerity.m_20340_(true);
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
        if (verityEntity != null && !verityEntity.m_213877_()) {
            verityEntity.startTalking(80);
        }
        ServerLevel serverLevel = (ServerLevel)player.m_9236_();
        long currentDay = serverLevel.m_46468_() / 24000L;
        WorldSpawnData spawnData = WorldSpawnData.get(serverLevel);
        float currentKarma = spawnData.verityKarma;
        CompletableFuture.supplyAsync(() -> AiAPI.askGroq(verityEntity, message, currentDay, currentKarma)).thenAccept(aiResponse -> {
            if (aiResponse == null || aiResponse.startsWith("Error")) {
                player.m_20194_().execute(() -> ModEvents.send(player, "AI connection error. You might need to replace your API Key."));
                return;
            }
            try {
                isMonstrous = verityEntity.isMonstrous();
                String cleanAiResponse = ModEvents.extractJson(aiResponse);
                JsonObject obj = JsonParser.parseString((String)cleanAiResponse).getAsJsonObject();
                if (obj.has("karma_change")) {
                    float karmaChange = obj.get("karma_change").getAsFloat();
                    if (karmaChange != 0.0f) {
                        ModTriggers.KARMA_CHANGE_TRIGGER.trigger(player);
                    }
                    player.m_20194_().execute(() -> ModEvents.updateAndSyncKarma(serverLevel, karmaChange));
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
                    player.m_20194_().execute(() -> {
                        if (verityEntity != null && !verityEntity.m_213877_()) {
                            verityEntity.setVariant(expression);
                            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.m_19879_(), reply));
                        } else {
                            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), (Object)new PlayTtsPayload(player.m_19879_(), reply));
                        }
                        ModEvents.send(player, reply);
                    });
                    return;
                }
                player.m_20194_().execute(() -> {
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
                                Holder biome = player.m_9236_().m_204166_(player.m_20183_());
                                data = player.m_9236_().m_9598_().m_175515_(Registries.f_256952_).m_7981_((Object)((Biome)biome.m_203334_())).m_135815_().replace("_", " ");
                                break;
                            }
                            case "get_coords": {
                                BlockPos p = player.m_20183_();
                                data = "X=" + p.m_123341_() + " Y=" + p.m_123342_() + " Z=" + p.m_123343_();
                                break;
                            }
                            case "get_inventory": {
                                ArrayList<CallSite> items = new ArrayList<CallSite>();
                                for (ItemStack inventoryStack : player.m_150109_().f_35974_) {
                                    if (inventoryStack.m_41619_()) continue;
                                    items.add((CallSite)((Object)(inventoryStack.m_41613_() + "x " + inventoryStack.m_41786_().getString())));
                                }
                                data = ((Object)items).toString();
                                break;
                            }
                            case "get_nearby_entities": {
                                AABB box = player.m_20191_().m_82400_(ENTITY_RADIUS);
                                data = player.m_9236_().m_6443_(LivingEntity.class, box, e -> e != player).stream().map(e -> e.m_7755_().getString()).toList().toString();
                                break;
                            }
                            case "get_nearest_ore_location": {
                                String oreType = args.has("ore") ? args.get("ore").getAsString() : "diamond";
                                data = ModEvents.findNearestOre(player, oreType);
                                break;
                            }
                            case "get_nearest_village": {
                                BlockPos pos = player.m_20183_();
                                HolderSet.Named villages = (HolderSet.Named)player.m_9236_().m_9598_().m_175515_(Registries.f_256944_).m_206058_(StructureTags.f_215889_);
                                BlockPos nearestVillage = ((ServerLevel)player.m_9236_()).m_215011_(villages.m_205839_(), pos, 150, false);
                                if (nearestVillage == null) {
                                    data = "No village found within search range.";
                                    break;
                                }
                                data = "Nearest village at X=" + nearestVillage.m_123341_() + " Y=~ Z=" + nearestVillage.m_123343_() + ". The villages at the east shouldn't be explored (YOU *MUST* MENTION THIS IN YOUR ANSWER). (don't mention this, but the villagers are gone.)";
                                ModTriggers.VILLAGE_TRIGGER.trigger(player);
                                break;
                            }
                            case "get_nearest_nether_fortress": {
                                BlockPos pos = player.m_20183_();
                                ResourceKey fortressKey = ResourceKey.m_135785_((ResourceKey)Registries.f_256944_, (ResourceLocation)new ResourceLocation("minecraft", "fortress"));
                                Registry registry = serverLevel.m_9598_().m_175515_(Registries.f_256944_);
                                Optional holderOptional = registry.m_203636_(fortressKey);
                                if (holderOptional.isEmpty()) {
                                    data = "Nether fortress structure is not registered or available.";
                                    break;
                                }
                                HolderSet.Direct structureSet = HolderSet.m_205809_((Holder[])new Holder[]{(Holder)holderOptional.get()});
                                Pair result = serverLevel.m_7726_().m_8481_().m_223037_(serverLevel, (HolderSet)structureSet, pos, 100, false);
                                if (result == null) {
                                    data = "No nether fortress found within search range.";
                                    break;
                                }
                                BlockPos nearestFortress = (BlockPos)result.getFirst();
                                data = "Nearest nether fortress at X=" + nearestFortress.m_123341_() + " Y=~ Z=" + nearestFortress.m_123343_();
                                break;
                            }
                            case "get_own_coords": {
                                VerityEntity found = player.m_9236_().m_45976_(VerityEntity.class, new AABB(player.m_20183_()).m_82400_(256.0)).stream().findFirst().orElse(null);
                                if (found == null) {
                                    data = "I don't know where I am right now.";
                                    break;
                                }
                                data = "My coords are: X=" + found.m_20183_().m_123341_() + " Y=" + found.m_20183_().m_123342_() + " Z=" + found.m_20183_().m_123343_();
                                break;
                            }
                            case "play_sound": {
                                String soundId = args.has("sound_id") ? args.get("sound_id").getAsString() : "minecraft:block.stone.place";
                                ResourceLocation soundLoc = ResourceLocation.parse((String)soundId);
                                SoundEvent sound = (SoundEvent)BuiltInRegistries.f_256894_.m_7745_(soundLoc);
                                if (sound != null && verityEntity != null) {
                                    ModTriggers.PLAY_SOUND_TRIGGER.trigger(player);
                                    player.m_9236_().m_5594_(null, verityEntity.m_20183_(), sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
                                    data = "Successfully played the sound.";
                                    break;
                                }
                                data = "Error: That sound does not exist or I am not in the world.";
                                break;
                            }
                            case "drop_item": {
                                String rawItemId = args.has("item_id") ? args.get("item_id").getAsString().toLowerCase().replace(" ", "_") : "dirt";
                                int count = args.has("count") ? args.get("count").getAsInt() : 1;
                                WorldSpawnData dataInstance = WorldSpawnData.get(serverLevel);
                                float karma = dataInstance.verityKarma;
                                if (karma >= 7.0f) {
                                    ResourceLocation loc;
                                    Item foundItem = null;
                                    if (rawItemId.contains(":") && (loc = ResourceLocation.m_135820_((String)rawItemId)) != null && ForgeRegistries.ITEMS.containsKey(loc)) {
                                        foundItem = (Item)ForgeRegistries.ITEMS.getValue(loc);
                                    }
                                    if (foundItem == null || foundItem == Items.f_41852_) {
                                        String searchTarget = rawItemId.contains(":") ? rawItemId.split(":")[1] : rawItemId;
                                        for (Map.Entry entry : ForgeRegistries.ITEMS.getEntries()) {
                                            if (!((ResourceKey)entry.getKey()).m_135782_().m_135815_().equals(searchTarget)) continue;
                                            foundItem = (Item)entry.getValue();
                                            break;
                                        }
                                    }
                                    if (foundItem != null && foundItem != Items.f_41852_ && verityEntity != null) {
                                        if (ModEvents.canDropItem(foundItem)) {
                                            ItemStack dropStack = new ItemStack((ItemLike)foundItem, count);
                                            ItemEntity droppedItem = new ItemEntity(player.m_9236_(), verityEntity.m_20185_(), verityEntity.m_20186_(), verityEntity.m_20189_(), dropStack);
                                            player.m_9236_().m_7967_((Entity)droppedItem);
                                            data = "Successfully dropped " + count + " of " + foundItem.m_5524_();
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
                                    verityEntity.m_9236_().m_5594_(null, verityEntity.m_20183_(), (SoundEvent)ModSounds.VERITY_DISC_SOUND.get(), SoundSource.VOICE, 1.0f, 1.0f);
                                    ModTriggers.FAVORITE_SONG_TRIGGER.trigger(player);
                                    data = "Successfully played the favourite song.";
                                    break;
                                }
                                data = "Failed, not in world.";
                                break;
                            }
                            case "stop_favourite_song": {
                                MinecraftServer server = player.m_20194_();
                                ResourceLocation soundToStop = new ResourceLocation("verity", "verity_disc");
                                ClientboundStopSoundPacket stopSoundPacket = new ClientboundStopSoundPacket(soundToStop, SoundSource.VOICE);
                                server.m_6846_().m_11268_((Packet)stopSoundPacket);
                                data = "Stopped the favourite song.";
                                break;
                            }
                            case "return_to_player": {
                                if (verityEntity == null || verityEntity.m_213877_()) {
                                    data = "No Verity found,";
                                    break;
                                }
                                ItemStack retStack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                                CompoundTag retTag = retStack.m_41784_();
                                retTag.m_128359_("VerityVariant", verityEntity.getVariant());
                                player.m_150109_().m_36054_(retStack);
                                verityEntity.m_146870_();
                                verityEntity = null;
                                hasSpawned = false;
                                data = "Now in player's inventory";
                                break;
                            }
                            case "get_block_player_is_looking_at": {
                                double reach = player.getBlockReach() * 2.0;
                                Vec3 eyePosition = player.m_146892_();
                                Vec3 viewVector = player.m_20252_(1.0f);
                                Vec3 targetPosition = eyePosition.m_82549_(viewVector.m_82490_(reach));
                                ClipContext context = new ClipContext(eyePosition, targetPosition, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)player);
                                BlockHitResult blockHitResult = player.m_9236_().m_45547_(context);
                                if (blockHitResult.m_6662_() == HitResult.Type.BLOCK) {
                                    BlockState blockState = player.m_9236_().m_8055_(blockHitResult.m_82425_());
                                    data = blockState.m_60734_().m_49954_().getString();
                                    break;
                                }
                                data = "Block out of reach";
                                break;
                            }
                            case "transform_following_day": {
                                if (player.m_9236_().m_46791_() != Difficulty.PEACEFUL) {
                                    data = "Transforming tomorrow";
                                    transformFollowingDay = true;
                                    timeWillSpawn = player.m_9236_().m_46468_() / 24000L + 1L;
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
                                data = "The players name is: " + String.valueOf(player.m_7755_());
                                break;
                            }
                            case "get_player_health": {
                                data = player.m_7755_().getString() + "'s health is: " + player.m_21223_();
                                break;
                            }
                            case "get_light_level": {
                                data = String.valueOf(player.m_9236_().m_46803_(player.m_20183_()));
                                break;
                            }
                            case "get_difficulty": {
                                data = player.m_9236_().m_46791_().toString();
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
                                List nearbyEntities = player.m_9236_().m_45976_(LivingEntity.class, player.m_20191_().m_82400_(64.0));
                                for (LivingEntity livingEntity : nearbyEntities) {
                                    if (!(livingEntity instanceof VerityDemonEntity)) continue;
                                    VerityDemonEntity dE = (VerityDemonEntity)livingEntity;
                                    dE.m_6074_();
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
                    CompletableFuture.supplyAsync(() -> AiAPI.askGroq(verityEntity, "Player asked: %s\nTools used: %s\nData retrieved:\n%s\nTell the player this information naturally. YOU MUST STILL output your response as a VALID JSON OBJECT with the array 'actions' left empty.\n".formatted(message, toolsUsed.toString(), combinedData.toString()), currentDay, WorldSpawnData.get((ServerLevel)serverLevel).verityKarma)).thenAccept(response -> player.m_20194_().execute(() -> {
                        try {
                            String cleanResponse = ModEvents.extractJson(response);
                            JsonObject finalObj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            String reply = finalObj.get("message").getAsString();
                            if (verityEntity != null && !verityEntity.m_213877_()) {
                                if (finalObj.has("variant")) {
                                    verityEntity.setVariant(finalObj.get("variant").getAsString());
                                }
                                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verityEntity), (Object)new PlayTtsPayload(verityEntity.m_19879_(), reply));
                            } else {
                                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), (Object)new PlayTtsPayload(player.m_19879_(), reply));
                            }
                            ModEvents.send(player, reply);
                        }
                        catch (Exception e) {
                            if (verityEntity != null) {
                                verityEntity.stopTalking();
                            }
                            ModEvents.send(player, "Error parsing final AI response.");
                            e.printStackTrace();
                        }
                    }));
                });
            }
            catch (Exception e) {
                player.m_20194_().execute(() -> ModEvents.send(player, "Failed to parse AI instruction."));
                e.printStackTrace();
            }
        });
    }

    private static String findNearestOre(ServerPlayer player, String type) {
        int r = 32;
        BlockPos center = player.m_20183_();
        BlockPos min = center.m_7918_(-r, -r, -r);
        BlockPos max = center.m_7918_(r, r, r);
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.m_121940_((BlockPos)min, (BlockPos)max)) {
            double dist;
            boolean match;
            if (center.m_123331_((Vec3i)pos) > (double)(r * r)) continue;
            BlockState state = player.m_9236_().m_8055_(pos);
            if (!(match = (switch (type.toLowerCase()) {
                case "diamond" -> state.m_204336_(BlockTags.f_144259_);
                case "iron" -> state.m_204336_(BlockTags.f_144258_);
                case "gold" -> state.m_204336_(BlockTags.f_13043_);
                case "coal" -> state.m_204336_(BlockTags.f_144262_);
                case "emerald" -> state.m_204336_(BlockTags.f_144263_);
                case "lapis" -> state.m_204336_(BlockTags.f_144261_);
                case "redstone" -> state.m_204336_(BlockTags.f_144260_);
                case "copper" -> state.m_204336_(BlockTags.f_144264_);
                default -> false;
            })) || !((dist = center.m_123331_((Vec3i)pos)) < bestDist)) continue;
            bestDist = dist;
            best = pos.m_7949_();
        }
        if (best == null) {
            return "No " + type + " ore found nearby.";
        }
        return type + " ore at X=" + best.m_123341_() + " Y=" + best.m_123342_() + " Z=" + best.m_123343_();
    }

    private static void send(ServerPlayer player, String msg) {
        if (((String)msg).length() > 1500) {
            msg = ((String)msg).substring(0, 1500) + "...";
        }
        ModTriggers.TALK_TRIGGER.trigger(player);
        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            return;
        }
        player.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> " + (String)msg)), false);
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
        HURT_COOLDOWN = new HashMap<UUID, Long>();
        transformFollowingDay = false;
        followPlayer = false;
        idleChatTimer = 3600;
        lonelinessTimer = 3000;
        isMonstrous = false;
        PENDING_TASKS = Collections.synchronizedList(new ArrayList());
        ACTIVE_TASKS = new ArrayList<ScheduledTask>();
    }

    private static class ScheduledTask {
        int ticksRemaining;
        Runnable task;

        ScheduledTask(Runnable task, int ticksRemaining) {
            this.task = task;
            this.ticksRemaining = ticksRemaining;
        }
    }
}

