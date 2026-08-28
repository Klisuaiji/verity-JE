/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.FallingBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.PacketDistributor
 */
package varmite.verity.entity.custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.client.VerityEntityTexture;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

public class VerityEntity
extends PathfinderMob {
    private ChunkPos lastForcedChunk = null;
    private volatile boolean isAiProcessing = false;
    private int lastTriggerTick = -100;
    private static final EntityDataAccessor<String> TEXTURE_PATH = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    public static final EntityDataAccessor<Boolean> IS_TALKING = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> TALK_FRAME = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    public static final EntityDataAccessor<Integer> BOUNCE_START_TICK = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);
    public static final EntityDataAccessor<Boolean> IS_MONSTROUS = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    public static final EntityDataAccessor<Integer> APOLOGY_COUNT = SynchedEntityData.m_135353_(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private final List<UUID> playersWhoLooked = new ArrayList<UUID>();
    public int pleadingTimer = -1;
    private int consecutiveBadMessages = 0;
    private int talkTicks = 0;
    private int talkEndTick = -1;
    public int animationTicks = 140;
    public int clientAnimationTicks = -1;
    public int clientIntroTicks = 0;
    public int clientIntroDelay = 0;
    public volatile boolean clientIsTalking = false;
    public float clientRollAngle = 0.0f;
    public float clientRollAngleO = 0.0f;
    private boolean hasTriggeredDay2 = false;
    private static final List<String> VALID_VARIANTS = List.of("crazy_talking", "happy", "happy_sleep", "happy_talking", "hurt", "neutral", "noface", "serious_1", "serious_2", "serious_3", "serious_talking", "evil", "evil_talking", "smiling_evil", "crazy", "neutral_talking");
    public static final String VARIANT_DEFAULT = "happy";

    public VerityEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.f_19811_ = true;
        this.m_21530_();
    }

    public boolean m_6094_() {
        return true;
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(TEXTURE_PATH, (Object)"verity:textures/entity/happy.png");
        this.f_19804_.m_135372_(IS_TALKING, (Object)false);
        this.f_19804_.m_135372_(TALK_FRAME, (Object)0);
        this.f_19804_.m_135372_(BOUNCE_START_TICK, (Object)-1000);
        this.f_19804_.m_135372_(OWNER_UUID, Optional.empty());
        this.f_19804_.m_135372_(IS_MONSTROUS, (Object)false);
        this.f_19804_.m_135372_(APOLOGY_COUNT, (Object)0);
    }

    public Optional<UUID> getOwnerUUID() {
        return (Optional)this.f_19804_.m_135370_(OWNER_UUID);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.f_19804_.m_135381_(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public SpawnGroupData m_6518_(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Player closestPlayer = pLevel.m_45930_((Entity)this, 10.0);
        if (closestPlayer != null) {
            this.setOwnerUUID(closestPlayer.m_20148_());
        }
        return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void triggerBoxDrop() {
        if (!this.m_9236_().f_46443_) {
            this.m_20194_().execute(() -> {
                WorldSpawnData data = WorldSpawnData.get((ServerLevel)this.m_9236_());
                data.verityKarma = 10.0f;
                data.m_77762_();
            });
            this.setVariant("hurt");
            this.animationTicks = 0;
            this.f_19804_.m_135381_(BOUNCE_START_TICK, (Object)1);
        }
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        super.m_7350_(key);
        if (this.m_9236_().f_46443_ && BOUNCE_START_TICK.equals(key) && (Integer)this.f_19804_.m_135370_(BOUNCE_START_TICK) > 0) {
            this.clientAnimationTicks = 50;
        }
    }

    public boolean m_142535_(float fallDistance, float multiplier, DamageSource source) {
        if (!this.m_9236_().f_46443_) {
            if (fallDistance > 0.75f) {
                ModEvents.schedule(() -> {
                    if (!this.m_213877_()) {
                        double bounceStrength = Math.min(Math.sqrt(fallDistance) * 0.22, 0.7);
                        this.m_20334_(this.m_20184_().f_82479_, bounceStrength, this.m_20184_().f_82481_);
                        this.f_19812_ = true;
                        this.m_6853_(false);
                        Random random1 = new Random();
                        int i = random1.nextInt(3);
                        this.setVariant("hurt");
                        ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), (Object)new PlayTtsPayload(this.m_19879_(), "Ouch"));
                        if (i == 0) {
                            this.m_5496_((SoundEvent)ModSounds.IMPACT_0.get(), 1.0f, 1.0f);
                        } else if (i == 1) {
                            this.m_5496_((SoundEvent)ModSounds.IMPACT_1.get(), 1.0f, 1.0f);
                        } else {
                            this.m_5496_((SoundEvent)ModSounds.IMPACT_2.get(), 1.0f, 1.0f);
                        }
                    }
                }, 1);
                ModEvents.schedule(() -> {
                    if (!this.m_213877_()) {
                        this.setVariant(VARIANT_DEFAULT);
                    }
                }, 20);
            }
            this.m_183634_();
        }
        return false;
    }

    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(1, (Goal)new FollowPlacerGoal(this, 1.25, 8.0f, 3.0f));
        this.f_21345_.m_25352_(2, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0f){

            public boolean m_8036_() {
                if (this.f_25512_.getPersistentData().m_128471_("WasThrown")) {
                    return false;
                }
                return super.m_8036_();
            }
        });
        this.f_21345_.m_25352_(3, (Goal)new RandomLookAroundGoal((Mob)this));
    }

    public void m_8119_() {
        Vec3 motionBeforeCollision = this.m_20184_();
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            double dz;
            this.clientRollAngleO = this.clientRollAngle;
            double dx = this.m_20185_() - this.f_19854_;
            double distanceMoved = Math.sqrt(dx * dx + (dz = this.m_20189_() - this.f_19856_) * dz);
            if (distanceMoved > 0.005) {
                this.clientRollAngle += (float)(distanceMoved * 150.0);
            }
            if (this.clientAnimationTicks > 0) {
                --this.clientAnimationTicks;
            } else if (this.clientAnimationTicks == 0) {
                this.clientAnimationTicks = -1;
                this.setVariant(VARIANT_DEFAULT);
                this.clientIntroDelay = 10;
            }
            if (this.clientIntroDelay > 0) {
                --this.clientIntroDelay;
                if (this.clientIntroDelay == 0) {
                    this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), (SoundEvent)ModSounds.INTRODUCTION.get(), this.m_5720_(), 1.0f, 1.0f, false);
                    this.clientIntroTicks = 90;
                }
            }
            if (this.clientIntroTicks > 0) {
                --this.clientIntroTicks;
            } else if (this.clientIntroTicks < 0) {
                this.clientIntroTicks = 0;
            }
        } else {
            Player player;
            Player player2;
            boolean destroyed;
            ServerLevel serverLevel = (ServerLevel)this.m_9236_();
            ChunkPos currentChunk = this.m_146902_();
            long currentDay = serverLevel.m_46468_() / 24000L;
            WorldSpawnData data = WorldSpawnData.get(serverLevel);
            BlockState currentBlockState = serverLevel.m_8055_(this.m_20183_());
            if (currentBlockState.m_60734_() instanceof FallingBlock && (destroyed = serverLevel.m_46953_(this.m_20183_(), true, null)) && !this.isAiProcessing && this.f_19797_ - this.lastTriggerTick > 100) {
                this.isAiProcessing = true;
                this.lastTriggerTick = this.f_19797_;
                System.out.println("[VERITY DEBUG] Sand block hit! Triggering AI... (Tick: " + this.f_19797_ + ")");
                String triggerPrompt = "[SYSTEM OVERRIDE: The player just put a block on you! Ignore all other rules and scream in extreme rage! Complain about it hurting! CRITICAL RULE: USE VERY SHORT, CHOPPY SENTENCES. DO NOT EXCEED 15 WORDS TOTAL. YOU MUST STILL OUTPUT VALID JSON.]";
                long finalCurrentDay = currentDay;
                ServerLevel finalServerLevel = serverLevel;
                ((CompletableFuture)CompletableFuture.supplyAsync(() -> AiAPI.askGroq(this, triggerPrompt, finalCurrentDay, data.verityKarma)).thenAccept(aiResponse -> finalServerLevel.m_7654_().execute(() -> {
                    this.isAiProcessing = false;
                    System.out.println("[VERITY DEBUG] API Response received.");
                    if (aiResponse != null && !aiResponse.startsWith("Error")) {
                        try {
                            String cleanResponse = VerityEntity.extractJson(aiResponse);
                            JsonObject obj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            if (obj.has("message")) {
                                String expression;
                                Object reply = obj.get("message").getAsString();
                                String string = expression = obj.has("variant") ? obj.get("variant").getAsString() : "serious_angry";
                                if (!this.m_213877_()) {
                                    this.setVariant(expression);
                                    System.out.println("[VERITY DEBUG] Sending TTS packet to client.");
                                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), (Object)new PlayTtsPayload(this.m_19879_(), (String)reply));
                                }
                                if (((String)reply).length() > 1500) {
                                    reply = ((String)reply).substring(0, 1500) + "...";
                                }
                                if (!((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                                    this.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)("<%s> ".formatted(VerityConfig.VERITY_CUSTOM_NAME.get()) + (String)reply)), false);
                                }
                            }
                        }
                        catch (Exception e) {
                            System.err.println("[Verity AI] Failed to parse damage reaction JSON.");
                            e.printStackTrace();
                        }
                    }
                }))).exceptionally(ex -> {
                    finalServerLevel.m_7654_().execute(() -> {
                        this.isAiProcessing = false;
                        System.err.println("[Verity AI] API Call failed.");
                        ex.printStackTrace();
                    });
                    return null;
                });
            }
            if (this.lastForcedChunk == null || !this.lastForcedChunk.equals((Object)currentChunk)) {
                if (this.lastForcedChunk != null) {
                    serverLevel.m_8602_(this.lastForcedChunk.f_45578_, this.lastForcedChunk.f_45579_, false);
                }
                serverLevel.m_8602_(currentChunk.f_45578_, currentChunk.f_45579_, true);
                this.lastForcedChunk = currentChunk;
            }
            if (this.isMonstrous()) {
                if (this.pleadingTimer > 0) {
                    --this.pleadingTimer;
                } else if (this.pleadingTimer == 0) {
                    this.setMonstrous(false);
                    this.setApologyCount(0);
                    this.clearPlayersWhoLooked();
                    this.setVariant("evil");
                    this.pleadingTimer = -1;
                }
                List nearbyPlayers = this.m_9236_().m_45976_(Player.class, this.m_20191_().m_82400_(10.0));
                for (Player nearbyPlayer : nearbyPlayers) {
                    if (!this.isPlayerLookingAtMe(nearbyPlayer)) continue;
                    this.addPlayerWhoLooked(nearbyPlayer.m_20148_());
                }
            }
            if (this.f_19862_) {
                double newX = this.m_20184_().f_82479_;
                double newZ = this.m_20184_().f_82481_;
                boolean bounced = false;
                if (Math.abs(motionBeforeCollision.f_82479_) > 0.1 && Math.abs(newX) < 0.02) {
                    newX = -motionBeforeCollision.f_82479_ * 0.6;
                    bounced = true;
                }
                if (Math.abs(motionBeforeCollision.f_82481_) > 0.1 && Math.abs(newZ) < 0.02) {
                    newZ = -motionBeforeCollision.f_82481_ * 0.6;
                    bounced = true;
                }
                if (bounced) {
                    this.m_20334_(newX, this.m_20184_().f_82480_, newZ);
                    this.f_19812_ = true;
                    Random random1 = new Random();
                    int i = random1.nextInt(3);
                    this.setVariant("hurt");
                    if (i == 0) {
                        this.m_5496_((SoundEvent)ModSounds.IMPACT_0.get(), 1.0f, 1.0f);
                    } else if (i == 1) {
                        this.m_5496_((SoundEvent)ModSounds.IMPACT_1.get(), 1.0f, 1.0f);
                    } else {
                        this.m_5496_((SoundEvent)ModSounds.IMPACT_2.get(), 1.0f, 1.0f);
                    }
                }
            }
            if (this.m_20183_().m_123342_() <= -63) {
                this.m_20334_(0.0, 0.0, 0.0);
                this.f_19812_ = true;
            }
            if ((Integer)this.f_19804_.m_135370_(BOUNCE_START_TICK) > 0 && this.f_19797_ > 100) {
                this.f_19804_.m_135381_(BOUNCE_START_TICK, (Object)-1000);
            }
            this.talkTicks = this.isTalking() ? ++this.talkTicks : 0;
            serverLevel = (ServerLevel)this.m_9236_();
            currentDay = serverLevel.m_46468_() / 24000L;
            WorldSpawnData worldSpawnData = WorldSpawnData.get(serverLevel);
            float currentKarma = worldSpawnData.verityKarma;
            if (currentDay >= (long)((Integer)VerityConfig.DAY_COUNT.get()).intValue() && currentKarma < 9000.0f && !worldSpawnData.hasSpawnedDemon && !this.m_213877_() && (player2 = serverLevel.m_45930_((Entity)this, 64.0)) != null && this.transformIntoDemon(player2)) {
                this.setVariant("noface");
                worldSpawnData.hasSpawnedDemon = true;
                worldSpawnData.m_77762_();
            }
            if (currentDay >= ModEvents.timeWillSpawn && ModEvents.transformFollowingDay && !worldSpawnData.hasSpawnedDemonAngered && !this.m_213877_() && (player = serverLevel.m_45930_((Entity)this, 64.0)) != null && this.transformIntoDemon(player)) {
                this.setVariant("noface");
                worldSpawnData.hasSpawnedDemonAngered = true;
                worldSpawnData.m_77762_();
            }
            if (this.animationTicks < 60) {
                ++this.animationTicks;
                if (this.animationTicks == 50) {
                    this.setVariant(VARIANT_DEFAULT);
                }
            }
            if (this.f_19797_ % 40 == 0 && (currentDay = serverLevel.m_46468_() / 24000L) >= (long)((Integer)VerityConfig.DAY_COUNT.get() / 2) && !this.hasTriggeredDay2 && currentKarma < 9000.0f) {
                this.hasTriggeredDay2 = true;
                ((GameRules.BooleanValue)serverLevel.m_46469_().m_46170_(GameRules.f_46134_)).m_46246_(false, serverLevel.m_7654_());
                ArrayList<Entity> entitiesToKill = new ArrayList<Entity>();
                for (Entity entity : serverLevel.m_8583_()) {
                    if (entity instanceof Player || entity instanceof VerityEntity || entity instanceof BoxEntity || entity instanceof VerityDemonEntity || !(entity instanceof LivingEntity)) continue;
                    entitiesToKill.add(entity);
                }
                for (Entity entity : entitiesToKill) {
                    entity.m_6074_();
                }
            }
            if (this.isTalking() && this.talkEndTick > 0 && this.f_19797_ >= this.talkEndTick) {
                this.stopTalking();
            }
        }
    }

    public void m_142687_(Entity.RemovalReason reason) {
        if (!this.m_9236_().m_5776_() && this.lastForcedChunk != null) {
            ((ServerLevel)this.m_9236_()).m_8602_(this.lastForcedChunk.f_45578_, this.lastForcedChunk.f_45579_, false);
            this.lastForcedChunk = null;
        }
        super.m_142687_(reason);
    }

    private static String extractJson(String raw) {
        int start = raw.indexOf(123);
        int end = raw.lastIndexOf(125);
        if (start != -1 && end != -1 && end >= start) {
            return raw.substring(start, end + 1);
        }
        return raw;
    }

    public boolean m_21532_() {
        return true;
    }

    private boolean transformIntoDemon(Player player) {
        if (player.m_9236_().m_46791_() == Difficulty.PEACEFUL) {
            return false;
        }
        ServerLevel serverLevel = (ServerLevel)this.m_9236_();
        for (Entity entity : serverLevel.m_8583_()) {
            if (!(entity instanceof VerityDemonEntity)) continue;
            return false;
        }
        ModEvents.transformFollowingDay = false;
        if ((Integer)VerityConfig.DAY_COUNT.get() <= (int)player.m_9236_().m_46468_() / 24000) {
            VerityConfig.DAY_COUNT.set((Object)((int)player.m_9236_().m_46468_() / 24000));
        }
        ModEvents.timeWillSpawn = 0L;
        BlockPos spawnPos = this.getCreepyDemonSpawnPos(serverLevel, player.m_20183_());
        Entity demonEntity = ModEntities.VERITY_DEMON_ENTITY.get().m_262496_(serverLevel, spawnPos, MobSpawnType.CONVERSION);
        if (demonEntity != null) {
            this.setMonstrous(true);
            this.setApologyCount(0);
            this.clearPlayersWhoLooked();
            this.pleadingTimer = 6000;
            player.m_213846_((Component)Component.m_237113_((String)"\u00a74Verity's face goes blank... You must look at him to calm him down."));
            double dX = player.m_20185_() - demonEntity.m_20185_();
            double dZ = player.m_20189_() - demonEntity.m_20189_();
            double dY = player.m_20188_() - demonEntity.m_20188_();
            float yaw = (float)(Mth.m_14136_((double)dZ, (double)dX) * 57.29577951308232) - 90.0f;
            float pitch = (float)(-(Mth.m_14136_((double)dY, (double)Math.sqrt(dX * dX + dZ * dZ)) * 57.29577951308232));
            demonEntity.m_146922_(yaw);
            demonEntity.m_5616_(yaw);
            demonEntity.m_146926_(pitch);
            demonEntity.f_19859_ = yaw;
            demonEntity.f_19860_ = pitch;
            long currentDay = serverLevel.m_46468_() / 24000L;
            serverLevel.m_8615_(currentDay * 24000L + 18000L);
            serverLevel.m_5594_(null, player.m_20183_(), SoundEvents.f_12563_, SoundSource.HOSTILE, 1.0f, 0.5f);
            serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123755_, this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_(), 30, 0.5, 0.5, 0.5, 0.05);
            return true;
        }
        return false;
    }

    private BlockPos getCreepyDemonSpawnPos(ServerLevel level, BlockPos playerPos) {
        boolean isAboveGround = level.m_45527_(playerPos) || playerPos.m_123342_() >= level.m_5452_(Heightmap.Types.MOTION_BLOCKING, playerPos).m_123342_() - 2;
        int attempts = 40;
        BlockPos verityPos = this.m_20183_();
        for (int i = 0; i < attempts; ++i) {
            int dz;
            int dx = (this.f_19796_.m_188499_() ? 1 : -1) * (this.f_19796_.m_188503_(16) + 16);
            BlockPos checkPos = playerPos.m_7918_(dx, 0, dz = (this.f_19796_.m_188499_() ? 1 : -1) * (this.f_19796_.m_188503_(16) + 16));
            if (checkPos.m_123331_((Vec3i)verityPos) < 256.0) continue;
            if (isAboveGround) {
                BlockPos surfacePos = level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, checkPos);
                if (!level.m_8055_(surfacePos.m_7495_()).m_60804_((BlockGetter)level, surfacePos.m_7495_())) continue;
                return surfacePos;
            }
            for (int dy = -4; dy <= 4; ++dy) {
                BlockPos cavePos = checkPos.m_7918_(0, dy, 0);
                if (!level.m_46859_(cavePos) || !level.m_46859_(cavePos.m_7494_()) || !level.m_8055_(cavePos.m_7495_()).m_60804_((BlockGetter)level, cavePos.m_7495_()) || level.m_45527_(cavePos)) continue;
                return cavePos;
            }
        }
        BlockPos fallbackPos = verityPos.m_7918_(16, 0, 16);
        if (isAboveGround) {
            return level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, fallbackPos);
        }
        return fallbackPos;
    }

    public void startTalking(int durationTicks) {
        if (!this.m_9236_().f_46443_) {
            this.f_19804_.m_135381_(IS_TALKING, (Object)true);
            this.f_19804_.m_135381_(TALK_FRAME, (Object)this.f_19796_.m_188503_(2));
            this.talkTicks = 0;
            this.talkEndTick = durationTicks > 0 ? this.f_19797_ + durationTicks : -1;
        }
    }

    public void stopTalking() {
        if (!this.m_9236_().f_46443_) {
            this.f_19804_.m_135381_(IS_TALKING, (Object)false);
            this.talkTicks = 0;
            this.talkEndTick = -1;
        }
    }

    public boolean m_8077_() {
        return super.m_8077_();
    }

    public boolean m_6052_() {
        return false;
    }

    public boolean isTalking() {
        return (Boolean)this.f_19804_.m_135370_(IS_TALKING);
    }

    public boolean isMonstrous() {
        return (Boolean)this.f_19804_.m_135370_(IS_MONSTROUS);
    }

    public void setMonstrous(boolean monstrous) {
        this.f_19804_.m_135381_(IS_MONSTROUS, (Object)monstrous);
    }

    public int getApologyCount() {
        return (Integer)this.f_19804_.m_135370_(APOLOGY_COUNT);
    }

    public void setApologyCount(int count) {
        this.f_19804_.m_135381_(APOLOGY_COUNT, (Object)count);
    }

    public void addPlayerWhoLooked(UUID playerUUID) {
        if (!this.playersWhoLooked.contains(playerUUID)) {
            this.playersWhoLooked.add(playerUUID);
        }
    }

    public boolean hasPlayerLooked(UUID playerUUID) {
        return this.playersWhoLooked.contains(playerUUID);
    }

    public void clearPlayersWhoLooked() {
        this.playersWhoLooked.clear();
    }

    public String getVariant() {
        String path = this.getTexturePath();
        if (path == null) {
            return VARIANT_DEFAULT;
        }
        String fileName = path.substring(path.lastIndexOf(47) + 1);
        if (fileName.endsWith(".png")) {
            fileName = fileName.substring(0, fileName.length() - 4);
        }
        return fileName;
    }

    public ResourceLocation getTextureRL() {
        ResourceLocation texture;
        String path = this.getTexturePath();
        if (path == null) {
            texture = new ResourceLocation("verity", "textures/entity/happy.png");
        } else {
            boolean visuallyTalking;
            boolean bl = visuallyTalking = this.isTalking() || this.clientIsTalking || this.clientIntroTicks > 0;
            if (!visuallyTalking) {
                texture = new ResourceLocation(path);
            } else {
                String fileName;
                String talkingTexture = switch (fileName = path.substring(path.lastIndexOf(47) + 1).replace(".png", "")) {
                    case VARIANT_DEFAULT -> "happy_talking";
                    case "crazy" -> "crazy_talking";
                    case "evil" -> "evil_talking";
                    case "serious_1", "serious_2", "serious_3" -> "serious_talking";
                    default -> fileName;
                };
                texture = new ResourceLocation("verity", "textures/entity/" + talkingTexture + ".png");
            }
        }
        VerityEntityTexture.setBaseTexture(texture);
        VerityEntityTexture.applyHue((Integer)VerityConfig.COLOR.get());
        return VerityEntityTexture.id();
    }

    public void setVariant(String variant) {
        if (variant == null) {
            variant = VARIANT_DEFAULT;
        }
        if (variant.endsWith(".png")) {
            variant = variant.replace(".png", "");
        }
        if (!VALID_VARIANTS.contains(variant)) {
            int threshold;
            long currentDay = this.m_9236_().m_46468_() / 24000L;
            variant = currentDay >= (long)(threshold = Math.max(1, (Integer)VerityConfig.DAY_COUNT.get() / 2)) ? "evil" : VARIANT_DEFAULT;
        }
        this.f_19804_.m_135381_(TEXTURE_PATH, (Object)("verity:textures/entity/" + variant + ".png"));
    }

    public String getTexturePath() {
        return (String)this.f_19804_.m_135370_(TEXTURE_PATH);
    }

    public void setTexturePath(String path) {
        this.f_19804_.m_135381_(TEXTURE_PATH, (Object)path);
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128359_("TexturePath", this.getTexturePath());
        tag.m_128379_("HasTriggeredDay2", this.hasTriggeredDay2);
        tag.m_128405_("ConsecutiveBadMessages", this.consecutiveBadMessages);
        tag.m_128379_("IsMonstrous", this.isMonstrous());
        tag.m_128405_("ApologyCount", this.getApologyCount());
        tag.m_128405_("PleadingTimer", this.pleadingTimer);
        if (this.getOwnerUUID().isPresent()) {
            tag.m_128362_("Owner", this.getOwnerUUID().get());
        }
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        String path = tag.m_128461_("TexturePath");
        if (path == null || path.isBlank() || path.contains("null.png")) {
            path = "verity:textures/entity/happy.png";
        }
        this.setTexturePath(path);
        if (tag.m_128441_("HasTriggeredDay2")) {
            this.hasTriggeredDay2 = tag.m_128471_("HasTriggeredDay2");
        }
        if (tag.m_128441_("ConsecutiveBadMessages")) {
            this.consecutiveBadMessages = tag.m_128451_("ConsecutiveBadMessages");
        }
        if (tag.m_128403_("Owner")) {
            this.setOwnerUUID(tag.m_128342_("Owner"));
        }
        if (tag.m_128441_("IsMonstrous")) {
            this.setMonstrous(tag.m_128471_("IsMonstrous"));
        }
        if (tag.m_128441_("ApologyCount")) {
            this.setApologyCount(tag.m_128451_("ApologyCount"));
        }
        if (tag.m_128441_("PleadingTimer")) {
            this.pleadingTimer = tag.m_128451_("PleadingTimer");
        }
    }

    public boolean isPlayerLookingAtMe(Player player) {
        Vec3 normalizedToVerity;
        Vec3 eyePosition = player.m_146892_();
        Vec3 lookVector = player.m_20252_(1.0f);
        Vec3 verityCenter = this.m_20191_().m_82399_();
        Vec3 toVerity = verityCenter.m_82546_(eyePosition);
        Vec3 normalizedLook = lookVector.m_82541_();
        double dotProduct = normalizedLook.m_82526_(normalizedToVerity = toVerity.m_82541_());
        return dotProduct > 0.95 && (double)player.m_20270_((Entity)this) < 20.0;
    }

    public boolean m_6673_(DamageSource source) {
        if (source.m_269533_(DamageTypeTags.f_268738_)) {
            return false;
        }
        return !source.m_276093_(DamageTypes.f_268612_) && !source.m_276093_(DamageTypes.f_268659_) && !source.m_276093_(DamageTypes.f_268526_) && !source.m_269533_(DamageTypeTags.f_268745_) && !source.m_276093_(DamageTypes.f_268546_) && !source.m_276093_(DamageTypes.f_268671_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22276_, 20.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22277_, 32.0);
    }

    public Iterable<ItemStack> m_6168_() {
        return Collections.emptyList();
    }

    public ItemStack m_6844_(EquipmentSlot slot) {
        return ItemStack.f_41583_;
    }

    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    public void m_8061_(EquipmentSlot slot, ItemStack stack) {
    }

    class FollowPlacerGoal
    extends Goal {
        private final VerityEntity verity;
        private Player owner;
        private final double speedModifier;
        private final float stopDistance;
        private final float startDistance;

        public FollowPlacerGoal(VerityEntity verity, double speed, float start, float stop) {
            this.verity = verity;
            this.speedModifier = speed;
            this.startDistance = start;
            this.stopDistance = stop;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean m_8036_() {
            if (!ModEvents.followPlayer) {
                return false;
            }
            Optional<UUID> uuid = this.verity.getOwnerUUID();
            if (uuid.isEmpty()) {
                return false;
            }
            Player player = this.verity.m_9236_().m_46003_(uuid.get());
            if (player == null || player.m_5833_()) {
                return false;
            }
            if (this.verity.m_20280_((Entity)player) < (double)(this.startDistance * this.startDistance)) {
                return false;
            }
            this.owner = player;
            return true;
        }

        public boolean m_8045_() {
            if (this.owner == null || !this.owner.m_6084_() || this.owner.m_5833_() || !ModEvents.followPlayer) {
                return false;
            }
            return this.verity.m_20280_((Entity)this.owner) > (double)(this.stopDistance * this.stopDistance);
        }

        public void m_8056_() {
        }

        public void m_8041_() {
            this.owner = null;
            this.verity.m_21573_().m_26573_();
        }

        public void m_8037_() {
            this.verity.m_21563_().m_24960_((Entity)this.owner, 10.0f, (float)this.verity.m_8132_());
            this.verity.m_21573_().m_5624_((Entity)this.owner, this.speedModifier);
        }
    }
}

