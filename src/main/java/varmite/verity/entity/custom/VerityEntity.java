/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.entity.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.sounds.ModSounds;

public class VerityEntity
extends PathfinderMob {
    private static final EntityDataAccessor<String> TEXTURE_PATH = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> IS_TALKING = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TALK_FRAME = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BOUNCE_START_TICK = SynchedEntityData.defineId(VerityEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private int talkTicks = 0;
    private int talkEndTick = -1;
    public int animationTicks = 140;
    public int clientAnimationTicks = -1;
    public int clientIntroTicks = 0;
    public int clientIntroDelay = 0;
    public volatile boolean clientIsTalking = false;
    private boolean hasTriggeredDay2 = false;
    private static final List<String> VALID_VARIANTS = List.of("crazy_talking_0", "default", "default_sleep_0", "default_talking_1", "hurt_0", "neutral_0", "noface", "serious_1", "serious_2", "serious_3", "serious_angry", "smile2", "smile4", "smiling_evil_0", "smiling_evil_1", "talking_0");
    public static final String VARIANT_DEFAULT = "default";

    public VerityEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.noCulling = true;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 32.0f));
        this.goalSelector.addGoal(2, (Goal)new RandomLookAroundGoal((Mob)this));
    }

    public void push(Vec3 vector) {
        super.push(Vec3.ZERO);
    }

    public boolean isPushable() {
        return false;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TEXTURE_PATH, "verity:textures/entity/default.png");
        builder.define(IS_TALKING, false);
        builder.define(TALK_FRAME, 0);
        builder.define(BOUNCE_START_TICK, -1000);
    }

    public void triggerBoxDrop() {
        if (!this.level().isClientSide) {
            this.setVariant("hurt_0");
            this.animationTicks = 0;
            this.entityData.set(BOUNCE_START_TICK, 1);
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (this.level().isClientSide && BOUNCE_START_TICK.equals(key) && (Integer)this.entityData.get(BOUNCE_START_TICK) > 0) {
            this.clientAnimationTicks = 50;
        }
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
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
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), ModSounds.INTRODUCTION.get(), this.getSoundSource(), 1.0f, 1.0f, false);
                    this.clientIntroTicks = 90;
                }
            }
            if (this.clientIntroTicks > 0) {
                --this.clientIntroTicks;
            } else if (this.clientIntroTicks < 0) {
                this.clientIntroTicks = 0;
            }
        } else {
            if ((Integer)this.entityData.get(BOUNCE_START_TICK) > 0 && this.tickCount > 100) {
                this.entityData.set(BOUNCE_START_TICK, -1000);
            }
            this.talkTicks = this.isTalking() ? ++this.talkTicks : 0;
            long currentDay = this.level().getDayTime() / 24000L;
            if (currentDay >= 5L) {
                Player player;
                if (((GameRules.BooleanValue)this.level().getServer().getGameRules().getRule(GameRules.RULE_DAYLIGHT)).get() && !this.level().getServer().isHardcore()) {
                    ((GameRules.BooleanValue)this.level().getServer().getGameRules().getRule(GameRules.RULE_DAYLIGHT)).set(false, this.level().getServer());
                }
                if (!this.isRemoved() && (player = this.level().getNearestPlayer((Entity)this, 64.0)) != null) {
                    this.transformIntoDemon(player);
                }
            }
            if (this.animationTicks < 60) {
                ++this.animationTicks;
                if (this.animationTicks == 50) {
                    this.setVariant(VARIANT_DEFAULT);
                }
            }
            if (this.tickCount % 40 == 0 && (currentDay = this.level().getDayTime() / 24000L) >= 2L && !this.hasTriggeredDay2) {
                this.hasTriggeredDay2 = true;
                ServerLevel serverLevel = (ServerLevel)this.level();
                ((GameRules.BooleanValue)serverLevel.getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING)).set(false, serverLevel.getServer());
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

    private void transformIntoDemon(Player player) {
        ServerLevel serverLevel = (ServerLevel)this.level();
        BlockPos spawnPos = this.getCreepyDemonSpawnPos(serverLevel, player.blockPosition());
        Entity demonEntity = ModEntities.VERITY_DEMON_ENTITY.get().spawn(serverLevel, spawnPos, MobSpawnType.CONVERSION);
        if (demonEntity != null) {
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0f, 0.5f);
            serverLevel.sendParticles((ParticleOptions)ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.5, 0.5, 0.5, 0.05);
            this.discard();
        }
    }

    private BlockPos getCreepyDemonSpawnPos(ServerLevel level, BlockPos playerPos) {
        boolean isAboveGround = level.canSeeSky(playerPos) || playerPos.getY() >= level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, playerPos).getY() - 2;
        int attempts = 20;
        for (int i = 0; i < attempts; ++i) {
            int dx = (this.random.nextBoolean() ? 1 : -1) * (this.random.nextInt(8) + 8);
            int dz = (this.random.nextBoolean() ? 1 : -1) * (this.random.nextInt(8) + 8);
            BlockPos checkPos = playerPos.offset(dx, 0, dz);
            if (isAboveGround) {
                BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, checkPos);
                if (!level.getBlockState(surfacePos.below()).isSolidRender((BlockGetter)level, surfacePos.below())) continue;
                return surfacePos;
            }
            for (int dy = -4; dy <= 4; ++dy) {
                BlockPos cavePos = checkPos.offset(0, dy, 0);
                if (!level.isEmptyBlock(cavePos) || !level.isEmptyBlock(cavePos.above()) || !level.getBlockState(cavePos.below()).isSolidRender((BlockGetter)level, cavePos.below()) || level.canSeeSky(cavePos)) continue;
                return cavePos;
            }
        }
        return this.blockPosition();
    }

    public void startTalking(int durationTicks) {
        if (!this.level().isClientSide) {
            this.entityData.set(IS_TALKING, true);
            this.entityData.set(TALK_FRAME, this.random.nextInt(2));
            this.talkTicks = 0;
            this.talkEndTick = durationTicks > 0 ? this.tickCount + durationTicks : -1;
        }
    }

    public void stopTalking() {
        if (!this.level().isClientSide) {
            this.entityData.set(IS_TALKING, false);
            this.talkTicks = 0;
            this.talkEndTick = -1;
        }
    }

    public boolean hasCustomName() {
        return false;
    }

    public boolean shouldShowName() {
        return false;
    }

    public boolean isTalking() {
        return (Boolean)this.entityData.get(IS_TALKING);
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
        String fileName;
        boolean visuallyTalking;
        String path = this.getTexturePath();
        if (path == null) {
            return ResourceLocation.parse((String)"verity:textures/entity/default.png");
        }
        boolean bl = visuallyTalking = this.isTalking() || this.clientIsTalking || this.clientIntroTicks > 0;
        if (!visuallyTalking) {
            return ResourceLocation.parse((String)path);
        }
        return ResourceLocation.parse((String)("verity:textures/entity/" + (switch (fileName = path.substring(path.lastIndexOf(47) + 1).replace(".png", "")) {
            case VARIANT_DEFAULT -> "talking_0";
            case "smile4" -> "crazy_talking_0";
            case "smile2" -> "smiling_evil_1";
            default -> fileName;
        }) + ".png"));
    }

    public void setVariant(String variant) {
        if (variant == null) {
            variant = VARIANT_DEFAULT;
        }
        if (variant.endsWith(".png")) {
            variant = variant.replace(".png", "");
        }
        if (!VALID_VARIANTS.contains(variant)) {
            long currentDay = this.level().getDayTime() / 24000L;
            variant = currentDay >= 3L ? "smile4" : VARIANT_DEFAULT;
        }
        this.entityData.set(TEXTURE_PATH, ("verity:textures/entity/" + variant + ".png"));
    }

    public String getTexturePath() {
        return (String)this.entityData.get(TEXTURE_PATH);
    }

    public void setTexturePath(String path) {
        this.entityData.set(TEXTURE_PATH, path);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("TexturePath", this.getTexturePath());
        tag.putBoolean("HasTriggeredDay2", this.hasTriggeredDay2);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        String path = tag.getString("TexturePath");
        if (path == null || path.isBlank() || path.contains("null.png")) {
            path = "verity:textures/entity/default.png";
        }
        this.setTexturePath(path);
        if (tag.contains("HasTriggeredDay2")) {
            this.hasTriggeredDay2 = tag.getBoolean("HasTriggeredDay2");
        }
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypes.FELL_OUT_OF_WORLD);
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

