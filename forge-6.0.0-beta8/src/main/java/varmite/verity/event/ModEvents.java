/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  net.minecraft.ChatFormatting
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundStopSoundPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.animal.Chicken
 *  net.minecraft.world.entity.animal.Cow
 *  net.minecraft.world.entity.animal.Pig
 *  net.minecraft.world.entity.animal.Sheep
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.player.Player$BedSleepingProblem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.event.ServerChatEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.event.entity.EntityLeaveLevelEvent
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
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.network.PacketDistributor
 */
package varmite.verity.event;

import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.client.gui.PlayerKarma;
import varmite.verity.client.gui.PlayerKarmaProvider;
import varmite.verity.command.ChangeKarmaCommand;
import varmite.verity.command.RecoverVerityCommand;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.demon.VerityDemonEntity;
import varmite.verity.entity.llm.AiManager;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.entity.veritybox.BoxEntity;
import varmite.verity.environment.items.ModItems;
import varmite.verity.environment.sounds.ModSounds;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.network.KarmaSyncS2CPacket;
import varmite.verity.network.ModMessages;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;

@Mod.EventBusSubscriber(modid="verity")
public class ModEvents {
    private static final List<ScheduledTask> PENDING_TASKS = Collections.synchronizedList(new ArrayList());
    private static final List<ScheduledTask> ACTIVE_TASKS = new ArrayList<ScheduledTask>();

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
        System.out.println("Changing karma to " + amount);
        WorldSpawnData data = WorldSpawnData.get(level);
        data.verityKarma = amount;
        data.verityKarma = Mth.m_14036_((float)data.verityKarma, (float)0.0f, (float)20.0f);
        data.m_77762_();
        for (ServerPlayer player : level.m_6907_()) {
            ModMessages.sendToPlayer(new KarmaSyncS2CPacket((int)data.verityKarma), player);
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !((Entity)event.getObject()).getCapability(PlayerKarmaProvider.PLAYER_KARMA).isPresent()) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"properties"), (ICapabilityProvider)new PlayerKarmaProvider());
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
        if (livingEntity instanceof VerityEntity && !(verity = (VerityEntity)livingEntity).m_9236_().m_5776_()) {
            long lastHurt;
            long currentTime;
            ServerLevel serverLevel;
            if (event.getSource().m_269533_(DamageTypeTags.f_268745_) || event.getSource().m_276093_(DamageTypes.f_268546_)) {
                event.setAmount(0.0f);
                System.out.println("Verity is on fire");
                serverLevel = (ServerLevel)verity.m_9236_();
                currentTime = serverLevel.m_46467_();
                lastHurt = VerityState.HURT_COOLDOWN.getOrDefault(verity.m_20148_(), 0L);
                if (currentTime - lastHurt < 100L) {
                    return;
                }
                String[] messages = new String[]{"IT BURNS", "GET ME OUT OF HERE", "HELP ME IT BURNS", "AGH IT BURNS", "AGH"};
                Random rand = new Random();
                String answer = messages[rand.nextInt(messages.length)];
                verity.m_20194_().execute(() -> {
                    if (VerityState.verityEntity != null && !VerityState.verityEntity.m_213877_()) {
                        ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> VerityState.verityEntity), (Object)new PlayTtsPayload(VerityState.verityEntity.m_19879_(), answer));
                    }
                    ModEvents.updateAndSyncKarma(serverLevel, -2.0f);
                    verity.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)("<Verity> " + answer)), false);
                });
            }
            if (event.getSource().m_276093_(DamageTypes.f_268612_) || event.getSource().m_276093_(DamageTypes.f_268659_) || event.getSource().m_276093_(DamageTypes.f_268526_)) {
                System.out.println("Verity is suffocating");
                event.setAmount(0.0f);
                serverLevel = (ServerLevel)verity.m_9236_();
                currentTime = serverLevel.m_46467_();
                lastHurt = VerityState.HURT_COOLDOWN.getOrDefault(verity.m_20148_(), 0L);
                if (currentTime - lastHurt < 100L) {
                    return;
                }
                String triggerPrompt = event.getSource().m_269533_(DamageTypeTags.f_268745_) || event.getSource().m_276093_(DamageTypes.f_268546_) ? "Verity is burning during this turn" : "A heavy block has dropped on verity during this turn";
                VerityState.HURT_COOLDOWN.put(verity.m_20148_(), currentTime);
                String message = "<SYSTEM> " + triggerPrompt;
                AiManager.queryAI(VerityState.verityEntity, message, null);
            }
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
                VerityState.verityEntity = newVerity;
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> VerityState.verityEntity), (Object)new PlayTtsPayload(VerityState.verityEntity.m_19879_(), "AAAAAAAAHHH"));
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
                ServerLevel level = (ServerLevel)demon.m_9236_();
                WorldSpawnData data = WorldSpawnData.get(level);
                data.clearActiveDemon(demon.m_20148_());
                data.verityKarma = 20.0f;
                data.m_77762_();
                level.m_8767_((ParticleOptions)ParticleTypes.f_123767_, demon.m_20185_(), demon.m_20186_() + 1.0, demon.m_20189_(), 100, 0.5, 1.0, 0.5, 0.2);
                level.m_5594_(null, demon.m_20183_(), SoundEvents.f_12513_, SoundSource.NEUTRAL, 1.0f, 1.0f);
                VerityEntity verity = VerityState.verityEntity;
                if (verity != null && !verity.m_213877_()) {
                    verity.setVariant("happy");
                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verity), (Object)new PlayTtsPayload(verity.m_19879_(), "The darkness... it's gone. Thank you."));
                    if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                        return;
                    }
                    level.m_7654_().m_6846_().m_240416_((Component)Component.m_237113_((String)("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> The darkness... it's gone. Thank you.")), false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDemonLeaveLevel(EntityLeaveLevelEvent event) {
        if (event.getLevel().m_5776_()) {
            return;
        }
        Entity entity = event.getEntity();
        if (!(entity instanceof VerityDemonEntity)) {
            return;
        }
        VerityDemonEntity demon = (VerityDemonEntity)entity;
        Entity.RemovalReason reason = demon.m_146911_();
        if (reason == null || !reason.m_146965_()) {
            return;
        }
        WorldSpawnData.get((ServerLevel)event.getLevel()).clearActiveDemon(demon.m_20148_());
    }

    @SubscribeEvent
    public static void onPlayerDropsDrop(LivingDropsEvent event) {
        if (event.getEntity().m_9236_().f_46443_) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            event.getDrops().removeIf(itemEntity -> itemEntity.m_32055_().m_150930_((Item)ModItems.FLASHLIGHT.get()));
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
        if (VerityState.verityEntity != null && !VerityState.verityEntity.m_213877_() && !VerityState.verityEntity.m_9236_().m_5776_()) {
            if (VerityState.lonelinessTimer > 0) {
                --VerityState.lonelinessTimer;
            } else {
                VerityState.lonelinessTimer = 20;
                level = (ServerLevel)VerityState.verityEntity.m_9236_();
                nearestPlayer = level.m_45930_((Entity)VerityState.verityEntity, 32.0);
                if (nearestPlayer == null) {
                    ModEvents.updateAndSyncKarma(level, -1.0f);
                    if (!VerityState.verityEntity.m_213877_()) {
                        VerityState.verityEntity.setVariant("serious_1");
                        ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> VerityState.verityEntity), (Object)new PlayTtsPayload(VerityState.verityEntity.m_19879_(), "I'm alone... where did you go?"));
                        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                            return;
                        }
                        VerityState.verityEntity.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)"<%s> I'm alone... where did you go?".formatted(VerityConfig.VERITY_CUSTOM_NAME.get())), false);
                    }
                } else {
                    VerityState.lonelinessTimer = 3000;
                }
            }
        }
        if (VerityState.hasSpawned && VerityState.verityEntity != null && !VerityState.verityEntity.m_213877_() && !VerityState.verityEntity.m_9236_().m_5776_()) {
            if (VerityState.idleChatTimer > 0) {
                if (!VerityState.verityEntity.m_6060_()) {
                    --VerityState.idleChatTimer;
                }
            } else {
                VerityState.idleChatTimer = 2400 + new Random().nextInt(2400);
                level = (ServerLevel)VerityState.verityEntity.m_9236_();
                nearestPlayer = level.m_45930_((Entity)VerityState.verityEntity, 32.0);
                if (nearestPlayer instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)nearestPlayer;
                    if (!((Boolean)VerityState.verityEntity.m_20088_().m_135370_(VerityEntity.IS_TALKING)).booleanValue()) {
                        VerityState.verityEntity.startTalking(80);
                        String idlePrompt = "<SYSTEM> You decide to start a random conversation with the user. Ask them a question, comment the environment or even give random facts.\n";
                        AiManager.queryAI(VerityState.verityEntity, idlePrompt, serverPlayer);
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
        if (event.getLevel().m_5776_()) {
            return;
        }
        Random rand = new Random();
        boolean shouldKillEntity = rand.nextBoolean();
        if (event.getEntity().m_6095_() == ModEntities.VERITY_ENTITY.get()) {
            VerityState.hasSpawned = true;
            VerityState.verityEntity = (VerityEntity)event.getEntity();
            return;
        }
        if (!((Boolean)VerityConfig.KILL_ENTITIES.get()).booleanValue()) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof Sheep) {
            Sheep s = (Sheep)entity;
            if (((Boolean)VerityConfig.KILL_WILDLIFE.get()).booleanValue() && shouldKillEntity) {
                s.m_6074_();
            }
        } else if (entity instanceof Cow) {
            Cow c = (Cow)entity;
            if (((Boolean)VerityConfig.KILL_WILDLIFE.get()).booleanValue() && shouldKillEntity) {
                c.m_6074_();
            }
        } else if (entity instanceof Pig) {
            Pig p = (Pig)entity;
            if (((Boolean)VerityConfig.KILL_WILDLIFE.get()).booleanValue() && shouldKillEntity) {
                p.m_6074_();
            }
        } else if (entity instanceof Chicken) {
            Chicken ch = (Chicken)entity;
            if (((Boolean)VerityConfig.KILL_WILDLIFE.get()).booleanValue() && shouldKillEntity) {
                ch.m_6074_();
            }
        } else if (entity instanceof Villager) {
            Villager v = (Villager)entity;
            if (((Boolean)VerityConfig.KILL_VILLAGERS.get()).booleanValue()) {
                v.m_6074_();
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
                    ResourceLocation soundToStop = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_disc");
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
                VerityState.hasSpawned = false;
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
                            VerityState.verityEntity = verity;
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
                        if (VerityState.verityEntity != null) {
                            VerityState.verityEntity.m_9236_().m_5594_(null, VerityState.verityEntity.m_20183_(), (SoundEvent)ModSounds.IMPACT_1.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 55);
                    ModEvents.schedule(() -> {
                        if (VerityState.verityEntity != null) {
                            VerityState.verityEntity.m_9236_().m_5594_(null, VerityState.verityEntity.m_20183_(), (SoundEvent)ModSounds.IMPACT_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 75);
                    ModEvents.schedule(() -> {
                        if (VerityState.verityEntity != null) {
                            VerityState.verityEntity.m_9236_().m_5594_(null, VerityState.verityEntity.m_20183_(), (SoundEvent)ModSounds.IMPACT_2.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
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
            data.verityKarma = 10.0f;
            player.m_213846_((Component)Component.m_237113_((String)"Need help setting up this mod? Watch this tutorial."));
            MutableComponent message = Component.m_237113_((String)"Setup Tutorial").m_130940_(ChatFormatting.AQUA).m_130948_(Style.f_131099_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/O3pSCBvJ_i0")).m_131162_(Boolean.valueOf(true)));
            player.m_213846_((Component)message);
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
        if (player.m_9236_().m_46469_().m_46207_(GameRules.f_46133_)) {
            return;
        }
        VerityEntity verity = VerityState.verityEntity;
        if (verity != null && !verity.m_213877_() && verity.getOwnerUUID().isPresent() && player.m_20148_().equals(verity.getOwnerUUID().get())) {
            ItemStack stack2 = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
            CompoundTag itemNbt = stack2.m_41784_();
            verity.m_7380_(itemNbt);
            itemNbt.m_128359_("VerityVariant", verity.getVariant());
            itemNbt.m_128359_("VerityName", (String)VerityConfig.VERITY_CUSTOM_NAME.get());
            Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
            if (!((String)name).endsWith("\u2122")) {
                name = (String)name + "\u2122";
            }
            stack2.m_41714_((Component)Component.m_237113_((String)name));
            player.m_150109_().m_36054_(stack2);
            VerityState.hasSpawned = false;
            verity.m_146870_();
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

    public static boolean hasVerityInInventory(Player player) {
        if (player == null) {
            return false;
        }
        boolean inMain = player.m_150109_().f_35974_.stream().anyMatch(stack -> !stack.m_41619_() && stack.m_150930_((Item)ModItems.VERITY_ITEM.get()));
        if (inMain) {
            return true;
        }
        return !player.m_21206_().m_41619_() && player.m_21206_().m_150930_((Item)ModItems.VERITY_ITEM.get());
    }

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        boolean isSpawned = VerityState.hasSpawned && VerityState.verityEntity != null && !VerityState.verityEntity.m_213877_();
        boolean isInInventory = ModEvents.hasVerityInInventory((Player)player);
        if (!isSpawned && !isInInventory) {
            System.out.println("Verity is neither spawned nor in player's inventory");
            return;
        }
        if (((Boolean)VerityConfig.REQUIRE_VERITY.get()).booleanValue()) {
            System.out.println("Require verity is on");
            if (!event.getRawText().toLowerCase().contains(((String)VerityConfig.VERITY_CUSTOM_NAME.get()).toLowerCase())) {
                System.out.println("verity keyword not found");
                return;
            }
        }
        String message = event.getMessage().getString();
        VerityState.idleChatTimer = 2400 + new Random().nextInt(2400);
        if (isSpawned) {
            VerityState.verityEntity.startTalking(80);
        }
        System.out.println("run ask");
        String finalMessage = "<" + player.m_7755_().getString() + "> " + message;
        AiManager.queryAI(isSpawned ? VerityState.verityEntity : null, finalMessage, player);
        System.out.println("ran ask");
    }

    private static class ScheduledTask {
        int ticksRemaining;
        final Runnable task;

        ScheduledTask(Runnable task, int ticksRemaining) {
            this.task = task;
            this.ticksRemaining = ticksRemaining;
        }
    }
}

