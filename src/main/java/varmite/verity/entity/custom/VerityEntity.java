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
 *  net.neoforged.neoforge.network.PacketDistributor
 *  varmite.verity.VerityConfig
 *  varmite.verity.entity.AI.AiAPI
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.client.VerityEntityTexture
 *  varmite.verity.entity.custom.BoxEntity
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.entity.custom.VerityEntity
 *  varmite.verity.entity.custom.VerityEntity$FollowPlacerGoal
 *  varmite.verity.event.ModEvents
 *  varmite.verity.event.WorldSpawnData
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.network.PlayTtsPayload
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.entity.custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
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
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.client.VerityEntityTexture;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

/*
 * Exception performing whole class analysis ignored.
 */
public class VerityEntity
extends PathfinderMob {
    private ChunkPos lastForcedChunk = null;
    private volatile boolean isAiProcessing = false;
    private int lastTriggerTick = -100;
    private static final EntityDataAccessor<String> TEXTURE_PATH = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> IS_TALKING = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TALK_FRAME = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BOUNCE_START_TICK = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Boolean> IS_MONSTROUS = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> APOLOGY_COUNT = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private final List<UUID> playersWhoLooked = new ArrayList();
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
        this.noCulling = true;
        this.setPersistenceRequired();
    }

    public boolean isPushable() {
        return true;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.defineId(TEXTURE_PATH, (Object)"verity:textures/entity/happy.png");
        this.entityData.defineId(IS_TALKING, (Object)false);
        this.entityData.defineId(TALK_FRAME, (Object)0);
        this.entityData.defineId(BOUNCE_START_TICK, (Object)-1000);
        this.entityData.defineId(OWNER_UUID, Optional.empty());
        this.entityData.defineId(IS_MONSTROUS, (Object)false);
        this.entityData.defineId(APOLOGY_COUNT, (Object)0);
    }

    public Optional<UUID> getOwnerUUID() {
        return (Optional)this.entityData.get(OWNER_UUID);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.get(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public SpawnGroupData applyRaidBuffs(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Player closestPlayer = pLevel.getEntities((Entity)this, 10.0);
        if (closestPlayer != null) {
            this.setOwnerUUID(closestPlayer.getUUID());
        }
        return super.applyRaidBuffs(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void triggerBoxDrop() {
        if (!this.level().isClientSide) {
            this.getServer().execute(() -> {
                WorldSpawnData data = WorldSpawnData.get((ServerLevel)((ServerLevel)this.level()));
                data.verityKarma = 10.0f;
                data.setDirty();
            });
            this.setVariant("hurt");
            this.animationTicks = 0;
            this.entityData.get(BOUNCE_START_TICK, (Object)1);
        }
    }

    public void hurt(EntityDataAccessor<?> key) {
        super.hurt(key);
        if (this.level().isClientSide && BOUNCE_START_TICK.equals(key) && (Integer)this.entityData.get(BOUNCE_START_TICK) > 0) {
            this.clientAnimationTicks = 50;
        }
    }

    public boolean hurt(float fallDistance, float multiplier, DamageSource source) {
        if (!this.level().isClientSide) {
            if (fallDistance > 0.75f) {
                ModEvents.schedule(() -> {
                    if (!this.isRemoved()) {
                        double bounceStrength = Math.min(Math.sqrt(fallDistance) * 0.22, 0.7);
                        this.setDeltaMovement(this.getDeltaMovement().x, bounceStrength, this.getDeltaMovement().z);
                        this.hasImpulse = true;
                        this.hasPose(false);
                        Random random1 = new Random();
                        int i = random1.nextInt(3);
                        this.setVariant("hurt");
                        ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), (Object)new PlayTtsPayload(this.getId(), "Ouch"));
                        if (i == 0) {
                            this.hurt((SoundEvent)ModSounds.IMPACT_0.get(), 1.0f, 1.0f);
                        } else if (i == 1) {
                            this.hurt((SoundEvent)ModSounds.IMPACT_1.get(), 1.0f, 1.0f);
                        } else {
                            this.hurt((SoundEvent)ModSounds.IMPACT_2.get(), 1.0f, 1.0f);
                        }
                    }
                }, (int)1);
                ModEvents.schedule(() -> {
                    if (!this.isRemoved()) {
                        this.setVariant("happy");
                    }
                }, (int)20);
            }
            this.resetFallDistance();
        }
        return false;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, (Goal)new FollowPlacerGoal(this, this, 1.25, 8.0f, 3.0f));
        this.goalSelector.addGoal(2, (Goal)new Goal() { public boolean canUse() { return false; } });
        this.goalSelector.addGoal(3, (Goal)new RandomLookAroundGoal((Mob)this));
    }

    public void tick() {
        Vec3 motionBeforeCollision = this.getDeltaMovement();
        super.tick();
        if (this.level().isClientSide) {
            double dz;
            this.clientRollAngleO = this.clientRollAngle;
            double dx = this.getX() - this.xo;
            double distanceMoved = Math.sqrt(dx * dx + (dz = this.getZ() - this.zo) * dz);
            if (distanceMoved > 0.005) {
                this.clientRollAngle += (float)(distanceMoved * 150.0);
            }
            if (this.clientAnimationTicks > 0) {
                --this.clientAnimationTicks;
            } else if (this.clientAnimationTicks == 0) {
                this.clientAnimationTicks = -1;
                this.setVariant("happy");
                this.clientIntroDelay = 10;
            }
            if (this.clientIntroDelay > 0) {
                --this.clientIntroDelay;
                if (this.clientIntroDelay == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.INTRODUCTION.get(), this.getSoundSource(), 1.0f, 1.0f, false);
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
            ServerLevel serverLevel = (ServerLevel)this.level();
            ChunkPos currentChunk = this.chunkPosition();
            long currentDay = serverLevel.getDayTime() / 24000L;
            WorldSpawnData data = WorldSpawnData.get((ServerLevel)serverLevel);
            BlockState currentBlockState = serverLevel.getBlockState(this.blockPosition());
            if (currentBlockState.getBlock() instanceof FallingBlock && (destroyed = serverLevel.setBlock(this.blockPosition(), true, null)) && !this.isAiProcessing && this.tickCount - this.lastTriggerTick > 100) {
                this.isAiProcessing = true;
                this.lastTriggerTick = this.tickCount;
                System.out.println("[VERITY DEBUG] Sand block hit! Triggering AI... (Tick: " + this.tickCount + ")");
                String triggerPrompt = "[SYSTEM OVERRIDE: The player just put a block on you! Ignore all other rules and scream in extreme rage! Complain about it hurting! CRITICAL RULE: USE VERY SHORT, CHOPPY SENTENCES. DO NOT EXCEED 15 WORDS TOTAL. YOU MUST STILL OUTPUT VALID JSON.]";
                long finalCurrentDay = currentDay;
                ServerLevel finalServerLevel = serverLevel;
                ((CompletableFuture)CompletableFuture.supplyAsync(() -> AiAPI.askGroq((VerityEntity)this, (String)triggerPrompt, (long)finalCurrentDay, (float)data.verityKarma)).thenAccept(aiResponse -> finalServerLevel.getServer().execute(() -> {
                    this.isAiProcessing = false;
                    System.out.println("[VERITY DEBUG] API Response received.");
                    if (aiResponse != null && !aiResponse.startsWith("Error")) {
                        try {
                            String cleanResponse = VerityEntity.extractJson((String)aiResponse);
                            JsonObject obj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            if (obj.has("message")) {
                                String expression;
                                Object reply = obj.get("message").getAsString();
                                String string = expression = obj.has("variant") ? obj.get("variant").getAsString() : "serious_angry";
                                if (!this.isRemoved()) {
                                    this.setVariant(expression);
                                    System.out.println("[VERITY DEBUG] Sending TTS packet to client.");
                                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), (Object)new PlayTtsPayload(this.getId(), (String)reply));
                                }
                                if (((String)reply).length() > 1500) {
                                    reply = ((String)reply).substring(0, 1500) + "...";
                                }
                                if (!((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                                    this.getServer().getPlayerList().broadcastSystemMessage((Component)Component.getContents((String)("<%s> ".formatted(VerityConfig.VERITY_CUSTOM_NAME.get()) + (String)reply)), false);
                                }
                            }
                        }
                        catch (Exception e) {
                            System.err.println("[Verity AI] Failed to parse damage reaction JSON.");
                            e.printStackTrace();
                        }
                    }
                }))).exceptionally(ex -> {
                    finalServerLevel.getServer().execute(() -> {
                        this.isAiProcessing = false;
                        System.err.println("[Verity AI] API Call failed.");
                        ex.printStackTrace();
                    });
                    return null;
                });
            }
            if (this.lastForcedChunk == null || !this.lastForcedChunk.equals((Object)currentChunk)) {
                if (this.lastForcedChunk != null) {
                    serverLevel.setChunkForced(this.lastForcedChunk.x, this.lastForcedChunk.z, false);
                }
                serverLevel.setChunkForced(currentChunk.x, currentChunk.z, true);
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
                List nearbyPlayers = this.level().getEntities(Player.class, this.getBoundingBox().inflate(10.0));
                for (Player nearbyPlayer : nearbyPlayers) {
                    if (!this.isPlayerLookingAtMe(nearbyPlayer)) continue;
                    this.addPlayerWhoLooked(nearbyPlayer.getUUID());
                }
            }
            if (this.horizontalCollision) {
                double newX = this.getDeltaMovement().x;
                double newZ = this.getDeltaMovement().z;
                boolean bounced = false;
                if (Math.abs(motionBeforeCollision.x) > 0.1 && Math.abs(newX) < 0.02) {
                    newX = -motionBeforeCollision.x * 0.6;
                    bounced = true;
                }
                if (Math.abs(motionBeforeCollision.z) > 0.1 && Math.abs(newZ) < 0.02) {
                    newZ = -motionBeforeCollision.z * 0.6;
                    bounced = true;
                }
                if (bounced) {
                    this.setDeltaMovement(newX, this.getDeltaMovement().y, newZ);
                    this.hasImpulse = true;
                    Random random1 = new Random();
                    int i = random1.nextInt(3);
                    this.setVariant("hurt");
                    if (i == 0) {
                        this.hurt((SoundEvent)ModSounds.IMPACT_0.get(), 1.0f, 1.0f);
                    } else if (i == 1) {
                        this.hurt((SoundEvent)ModSounds.IMPACT_1.get(), 1.0f, 1.0f);
                    } else {
                        this.hurt((SoundEvent)ModSounds.IMPACT_2.get(), 1.0f, 1.0f);
                    }
                }
            }
            if (this.blockPosition().getY() <= -63) {
                this.setDeltaMovement(0.0, 0.0, 0.0);
                this.hasImpulse = true;
            }
            if ((Integer)this.entityData.get(BOUNCE_START_TICK) > 0 && this.tickCount > 100) {
                this.entityData.get(BOUNCE_START_TICK, (Object)-1000);
            }
            this.talkTicks = this.isTalking() ? ++this.talkTicks : 0;
            serverLevel = (ServerLevel)this.level();
            currentDay = serverLevel.getDayTime() / 24000L;
            WorldSpawnData worldSpawnData = WorldSpawnData.get((ServerLevel)serverLevel);
            float currentKarma = worldSpawnData.verityKarma;
            if (currentDay >= (long)((Integer)VerityConfig.DAY_COUNT.get()).intValue() && currentKarma < 9000.0f && !worldSpawnData.hasSpawnedDemon && !this.isRemoved() && (player2 = serverLevel.getEntities((Entity)this, 64.0)) != null && this.transformIntoDemon(player2)) {
                this.setVariant("noface");
                worldSpawnData.hasSpawnedDemon = true;
                worldSpawnData.setDirty();
            }
            if (currentDay >= ModEvents.timeWillSpawn && ModEvents.transformFollowingDay && !worldSpawnData.hasSpawnedDemonAngered && !this.isRemoved() && (player = serverLevel.getEntities((Entity)this, 64.0)) != null && this.transformIntoDemon(player)) {
                this.setVariant("noface");
                worldSpawnData.hasSpawnedDemonAngered = true;
                worldSpawnData.setDirty();
            }
            if (this.animationTicks < 60) {
                ++this.animationTicks;
                if (this.animationTicks == 50) {
                    this.setVariant("happy");
                }
            }
            if (this.tickCount % 40 == 0 && (currentDay = serverLevel.getDayTime() / 24000L) >= (long)((Integer)VerityConfig.DAY_COUNT.get() / 2) && !this.hasTriggeredDay2 && currentKarma < 9000.0f) {
                this.hasTriggeredDay2 = true;
                ((GameRules.BooleanValue)serverLevel.getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING)).create(false, serverLevel.getServer());
                ArrayList<Entity> entitiesToKill = new ArrayList<Entity>();
                for (Entity entity : serverLevel.getAllEntities()) {
                    if (entity instanceof Player || entity instanceof VerityEntity || entity instanceof BoxEntity || entity instanceof VerityDemonEntity || !(entity instanceof LivingEntity)) continue;
                    entitiesToKill.add(entity);
                }
                for (Entity entity : entitiesToKill) {
                    entity.kill();
                }
            }
            if (this.isTalking() && this.talkEndTick > 0 && this.tickCount >= this.talkEndTick) {
                this.stopTalking();
            }
        }
    }

    public void readAdditionalSaveData(Entity.RemovalReason reason) {
        if (!this.level().isClientSide() && this.lastForcedChunk != null) {
            ((ServerLevel)this.level()).setChunkForced(this.lastForcedChunk.x, this.lastForcedChunk.z, false);
            this.lastForcedChunk = null;
        }
        super.readAdditionalSaveData(reason);
    }

    private static String extractJson(String raw) {
        int start = raw.indexOf(123);
        int end = raw.lastIndexOf(125);
        if (start != -1 && end != -1 && end >= start) {
            return raw.substring(start, end + 1);
        }
        return raw;
    }

    public boolean isPersistenceRequired() {
        return true;
    }

    private boolean transformIntoDemon(Player player) {
        if (player.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        ServerLevel serverLevel = (ServerLevel)this.level();
        for (Entity entity : serverLevel.getAllEntities()) {
            if (!(entity instanceof VerityDemonEntity)) continue;
            return false;
        }
        ModEvents.transformFollowingDay = false;
        if ((Integer)VerityConfig.DAY_COUNT.get() <= (int)player.level().getDayTime() / 24000) {
            VerityConfig.DAY_COUNT.set((Object)((int)player.level().getDayTime() / 24000));
        }
        ModEvents.timeWillSpawn = 0L;
        BlockPos spawnPos = this.getCreepyDemonSpawnPos(serverLevel, player.blockPosition());
        Entity demonEntity = ((EntityType)ModEntities.VERITY_DEMON_ENTITY.get()).spawn(serverLevel, spawnPos, MobSpawnType.CONVERSION);
        if (demonEntity != null) {
            this.setMonstrous(true);
            this.setApologyCount(0);
            this.clearPlayersWhoLooked();
            this.pleadingTimer = 6000;
            player.sendSystemMessage((Component)Component.getContents((String)"\u00a74Verity's face goes blank... You must look at him to calm him down."));
            double dX = player.getX() - demonEntity.getX();
            double dZ = player.getZ() - demonEntity.getZ();
            double dY = player.getEyeY() - demonEntity.getEyeY();
            float yaw = (float)(Mth.floor((double)dZ, (double)dX) * 57.29577951308232) - 90.0f;
            float pitch = (float)(-(Mth.floor((double)dY, (double)Math.sqrt(dX * dX + dZ * dZ)) * 57.29577951308232));
            demonEntity.defineSynchedData(yaw);
            demonEntity.setYHeadRot(yaw);
            demonEntity.playerTouch(pitch);
            demonEntity.yRotO = yaw;
            demonEntity.xRotO = pitch;
            long currentDay = serverLevel.getDayTime() / 24000L;
            serverLevel.setDayTime(currentDay * 24000L + 18000L);
            serverLevel.createTick(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0f, 0.5f);
            serverLevel.sendParticles((ParticleOptions)ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.5, 0.5, 0.5, 0.05);
            return true;
        }
        return false;
    }

    private BlockPos getCreepyDemonSpawnPos(ServerLevel level, BlockPos playerPos) {
        boolean isAboveGround = level.canSeeSky(playerPos) || playerPos.getY() >= level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING, playerPos).getY() - 2;
        int attempts = 40;
        BlockPos verityPos = this.blockPosition();
        for (int i = 0; i < attempts; ++i) {
            int dz;
            int dx = (this.random.nextBoolean() ? 1 : -1) * (this.random.nextInt(16) + 16);
            BlockPos checkPos = playerPos.offset(dx, 0, dz = (this.random.nextBoolean() ? 1 : -1) * (this.random.nextInt(16) + 16));
            if (checkPos.distSqr((Vec3i)verityPos) < 256.0) continue;
            if (isAboveGround) {
                BlockPos surfacePos = level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, checkPos);
                if (!level.getBlockState(surfacePos.below()).isSolidRender((BlockGetter)level, surfacePos.below())) continue;
                return surfacePos;
            }
            for (int dy = -4; dy <= 4; ++dy) {
                BlockPos cavePos = checkPos.offset(0, dy, 0);
                if (!level.isEmptyBlock(cavePos) || !level.isEmptyBlock(cavePos.above()) || !level.getBlockState(cavePos.below()).isSolidRender((BlockGetter)level, cavePos.below()) || level.canSeeSky(cavePos)) continue;
                return cavePos;
            }
        }
        BlockPos fallbackPos = verityPos.offset(16, 0, 16);
        if (isAboveGround) {
            return level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, fallbackPos);
        }
        return fallbackPos;
    }

    public void startTalking(int durationTicks) {
        if (!this.level().isClientSide) {
            this.entityData.get(IS_TALKING, (Object)true);
            this.entityData.get(TALK_FRAME, (Object)this.random.nextInt(2));
            this.talkTicks = 0;
            this.talkEndTick = durationTicks > 0 ? this.tickCount + durationTicks : -1;
        }
    }

    public void stopTalking() {
        if (!this.level().isClientSide) {
            this.entityData.get(IS_TALKING, (Object)false);
            this.talkTicks = 0;
            this.talkEndTick = -1;
        }
    }

    public boolean hasCustomName() {
        return super.hasCustomName();
    }

    public boolean shouldShowName() {
        return false;
    }

    public boolean isTalking() {
        return (Boolean)this.entityData.get(IS_TALKING);
    }

    public boolean isMonstrous() {
        return (Boolean)this.entityData.get(IS_MONSTROUS);
    }

    public void setMonstrous(boolean monstrous) {
        this.entityData.get(IS_MONSTROUS, (Object)monstrous);
    }

    public int getApologyCount() {
        return (Integer)this.entityData.get(APOLOGY_COUNT);
    }

    public void setApologyCount(int count) {
        this.entityData.get(APOLOGY_COUNT, (Object)count);
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
            return "happy";
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
                    case "happy" -> "happy_talking";
                    case "crazy" -> "crazy_talking";
                    case "evil" -> "evil_talking";
                    case "serious_1", "serious_2", "serious_3" -> "serious_talking";
                    default -> fileName;
                };
                texture = new ResourceLocation("verity", "textures/entity/" + talkingTexture + ".png");
            }
        }
        VerityEntityTexture.setBaseTexture((ResourceLocation)texture);
        VerityEntityTexture.applyHue((int)((Integer)VerityConfig.COLOR.get()));
        return VerityEntityTexture.id();
    }

    public void setVariant(String variant) {
        if (variant == null) {
            variant = "happy";
        }
        if (variant.endsWith(".png")) {
            variant = variant.replace(".png", "");
        }
        if (!VALID_VARIANTS.contains(variant)) {
            int threshold;
            long currentDay = this.level().getDayTime() / 24000L;
            variant = currentDay >= (long)(threshold = Math.max(1, (Integer)VerityConfig.DAY_COUNT.get() / 2)) ? "evil" : "happy";
        }
        this.entityData.get(TEXTURE_PATH, (Object)("verity:textures/entity/" + variant + ".png"));
    }

    public String getTexturePath() {
        return (String)this.entityData.get(TEXTURE_PATH);
    }

    public void setTexturePath(String path) {
        this.entityData.get(TEXTURE_PATH, (Object)path);
    }

    public void handleEntityEvent(CompoundTag tag) {
        super.handleEntityEvent(tag);
        tag.putString("TexturePath", this.getTexturePath());
        tag.putBoolean("HasTriggeredDay2", this.hasTriggeredDay2);
        tag.putInt("ConsecutiveBadMessages", this.consecutiveBadMessages);
        tag.putBoolean("IsMonstrous", this.isMonstrous());
        tag.putInt("ApologyCount", this.getApologyCount());
        tag.putInt("PleadingTimer", this.pleadingTimer);
        if (this.getOwnerUUID().isPresent()) {
            tag.putUUID("Owner", (UUID)this.getOwnerUUID().get());
        }
    }

    public void hurt(CompoundTag tag) {
        super.hurt(tag);
        String path = tag.getString("TexturePath");
        if (path == null || path.isBlank() || path.contains("null.png")) {
            path = "verity:textures/entity/happy.png";
        }
        this.setTexturePath(path);
        if (tag.contains("HasTriggeredDay2")) {
            this.hasTriggeredDay2 = tag.getBoolean("HasTriggeredDay2");
        }
        if (tag.contains("ConsecutiveBadMessages")) {
            this.consecutiveBadMessages = tag.getInt("ConsecutiveBadMessages");
        }
        if (tag.hasUUID("Owner")) {
            this.setOwnerUUID(tag.getUUID("Owner"));
        }
        if (tag.contains("IsMonstrous")) {
            this.setMonstrous(tag.getBoolean("IsMonstrous"));
        }
        if (tag.contains("ApologyCount")) {
            this.setApologyCount(tag.getInt("ApologyCount"));
        }
        if (tag.contains("PleadingTimer")) {
            this.pleadingTimer = tag.getInt("PleadingTimer");
        }
    }

    public boolean isPlayerLookingAtMe(Player player) {
        Vec3 normalizedToVerity;
        Vec3 eyePosition = player.getEyePosition();
        Vec3 lookVector = player.getViewVector(1.0f);
        Vec3 verityCenter = this.getBoundingBox().getCenter();
        Vec3 toVerity = verityCenter.subtract(eyePosition);
        Vec3 normalizedLook = lookVector.normalize();
        double dotProduct = normalizedLook.dot(normalizedToVerity = toVerity.normalize());
        return dotProduct > 0.95 && (double)player.setId((Entity)this) < 20.0;
    }

    public boolean isInvulnerableTo(DamageSource source) {
        if (source.getFoodExhaustion(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        return !source.getFoodExhaustion(DamageTypes.IN_WALL) && !source.getFoodExhaustion(DamageTypes.FALLING_BLOCK) && !source.getFoodExhaustion(DamageTypes.FALLING_ANVIL) && !source.getFoodExhaustion(DamageTypeTags.IS_FIRE) && !source.getFoodExhaustion(DamageTypes.LAVA) && !source.getFoodExhaustion(DamageTypes.FALL);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
    }
}

