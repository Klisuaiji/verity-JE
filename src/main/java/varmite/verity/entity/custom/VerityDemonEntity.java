/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.PathType
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package varmite.verity.entity.custom;

import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import varmite.verity.client.sound.ClientSoundHandler;
import varmite.verity.entity.AI.DemonAttackGoal;
import varmite.verity.entity.AI.DemonBreakDoorGoal;
import varmite.verity.entity.AI.DemonGlassBreakAndLeapGoal;
import varmite.verity.entity.AI.DemonStareAndBreakGoal;
import varmite.verity.entity.AI.DemonWindowStalkGoal;
import varmite.verity.entity.AI.pathfinding.DemonPathNavigation;

public class VerityDemonEntity
extends PathfinderMob
implements GeoEntity,
Enemy {
    private int climbTimer = 0;
    private int crawlTimer = 0;
    private int stuckTicks = 0;
    private Vec3 lastPos = null;
    public boolean isPlayingChaseSound = false;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final EntityDataAccessor<Integer> DEMON_STATE = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_CLIMBING = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> HUNT_PHASE = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_CRAWLING = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);

    public boolean isStuck() {
        return this.stuckTicks > 40;
    }

    public void resetStuckTimer() {
        this.stuckTicks = 0;
    }

    public void startClimbing() {
        this.climbTimer = 10;
    }

    public VerityDemonEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0f);
        this.setPathfindingMalus(PathType.WATER_BORDER, 0.0f);
    }

    protected PathNavigation createNavigation(Level level) {
        return new DemonPathNavigation((Mob)this, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DEMON_STATE, (Object)0);
        builder.define(IS_CLIMBING, (Object)false);
        builder.define(IS_CRAWLING, (Object)false);
        builder.define(HUNT_PHASE, (Object)0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (IS_CRAWLING.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    public void setHuntPhase(int phase) {
        this.entityData.set(HUNT_PHASE, (Object)phase);
    }

    public int getHuntPhase() {
        return (Integer)this.entityData.get(HUNT_PHASE);
    }

    public void setDemonState(int state) {
        this.entityData.set(DEMON_STATE, (Object)state);
    }

    public int getDemonState() {
        return (Integer)this.entityData.get(DEMON_STATE);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new DemonGlassBreakAndLeapGoal(this));
        this.goalSelector.addGoal(2, (Goal)new DemonBreakDoorGoal(this));
        this.goalSelector.addGoal(3, (Goal)new DemonWindowStalkGoal(this));
        this.goalSelector.addGoal(4, (Goal)new DemonStareAndBreakGoal(this));
        this.goalSelector.addGoal(5, (Goal)new DemonAttackGoal(this));
        this.goalSelector.addGoal(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0f, 1.0f));
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, false){

            public boolean canUse() {
                return VerityDemonEntity.this.getDemonState() == 1 && super.canUse();
            }
        });
        super.registerGoals();
    }

    public void forceCrawl(int ticks) {
        this.crawlTimer = Math.max(this.crawlTimer, ticks);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 200.0).add(Attributes.MOVEMENT_SPEED, 0.35).add(Attributes.FOLLOW_RANGE, 512.0).add(Attributes.ATTACK_DAMAGE, 19.0).add(Attributes.ATTACK_KNOCKBACK, 1.0).add(Attributes.STEP_HEIGHT, 1.5).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public boolean hasLineOfSightThroughGlass(Player player) {
        Vec3 end;
        Vec3 start = new Vec3(player.getX(), player.getEyeY(), player.getZ());
        double distance = start.distanceTo(end = new Vec3(this.getX(), this.getEyeY(), this.getZ()));
        if (distance > 128.0) {
            return false;
        }
        Vec3 dir = end.subtract(start).normalize();
        for (double d = 0.0; d < distance; d += 1.0) {
            String name;
            Vec3 point = start.add(dir.scale(d));
            BlockPos pos = BlockPos.containing((Position)point);
            BlockState state = this.level().getBlockState(pos);
            if (!state.blocksMotion() || (name = state.getBlock().getDescriptionId().toLowerCase()).contains("glass") || name.contains("pane") || state.is(BlockTags.LEAVES)) continue;
            return false;
        }
        return true;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.getDemonState() == 1 && !this.isPlayingChaseSound) {
                this.isPlayingChaseSound = true;
                ClientSoundHandler.playDemonChaseSound(this);
            }
            return;
        }
        LivingEntity target = this.getTarget();
        if (!(this.getDemonState() != 1 || target != null && target.isAlive())) {
            this.setTarget(null);
            this.setDemonState(0);
            this.setHuntPhase(0);
            this.getNavigation().stop();
            return;
        }
        if (target != null) {
            if (this.lastPos == null) {
                this.lastPos = this.position();
            }
            if (this.tickCount % 10 == 0) {
                this.stuckTicks = this.position().distanceToSqr(this.lastPos) < 0.05 ? (this.stuckTicks += 10) : 0;
                this.lastPos = this.position();
            }
        }
        BlockPos feet = this.blockPosition();
        if (this.getDemonState() == 1 && target != null) {
            AABB box = this.getBoundingBox().inflate(0.5, 0.5, 0.5);
            BlockPos.betweenClosedStream((BlockPos)BlockPos.containing((double)box.minX, (double)box.minY, (double)box.minZ), (BlockPos)BlockPos.containing((double)box.maxX, (double)box.maxY, (double)box.maxZ)).forEach(pos -> {
                BlockState state = this.level().getBlockState(pos);
                String blockName = state.getBlock().getDescriptionId().toLowerCase();
                if (state.is(BlockTags.LEAVES) || blockName.contains("glass") || blockName.contains("pane")) {
                    this.level().destroyBlock(pos, true);
                }
            });
            if (this.horizontalCollision) {
                this.startClimbing();
            }
            if (((Boolean)this.entityData.get(IS_CLIMBING)).booleanValue() && this.verticalCollision) {
                BlockPos overhead1 = feet.above(3);
                BlockPos overhead2 = feet.above(4);
                for (BlockPos overhead : new BlockPos[]{overhead1, overhead2}) {
                    BlockState state2 = this.level().getBlockState(overhead);
                    if (!state2.blocksMotion() || !(state2.getDestroySpeed((BlockGetter)this.level(), overhead) >= 0.0f)) continue;
                    this.level().destroyBlock(overhead, true);
                    this.triggerAttack();
                }
            }
        }
        if (this.climbTimer > 0) {
            --this.climbTimer;
            this.entityData.set(IS_CLIMBING, (Object)true);
            this.setDeltaMovement(this.getDeltaMovement().x, 0.3, this.getDeltaMovement().z);
        } else {
            this.entityData.set(IS_CLIMBING, (Object)false);
        }
        boolean needsToCrouch = false;
        Predicate<BlockState> isHardCeiling = state -> {
            String name = state.getBlock().getDescriptionId().toLowerCase();
            return state.blocksMotion() && !state.is(BlockTags.LEAVES) && !name.contains("glass") && !name.contains("pane");
        };
        if (isHardCeiling.test(this.level().getBlockState(feet.above(2))) || isHardCeiling.test(this.level().getBlockState(feet.above(3)))) {
            needsToCrouch = true;
        }
        if (!needsToCrouch && this.getDemonState() == 1 && target != null) {
            Vec3 dir = target.position().subtract(this.position());
            Vec3 horizDir = new Vec3(dir.x, 0.0, dir.z);
            if (horizDir.lengthSqr() > 0.01) {
                horizDir = horizDir.normalize();
                for (int i = 1; i <= 5; ++i) {
                    BlockPos forwardPos = BlockPos.containing((double)(this.getX() + horizDir.x * (double)i), (double)this.getY(), (double)(this.getZ() + horizDir.z * (double)i));
                    if (isHardCeiling.test(this.level().getBlockState(forwardPos)) || isHardCeiling.test(this.level().getBlockState(forwardPos.above(1)))) break;
                    if (!isHardCeiling.test(this.level().getBlockState(forwardPos.above(2))) && !isHardCeiling.test(this.level().getBlockState(forwardPos.above(3)))) continue;
                    needsToCrouch = true;
                    break;
                }
            }
        }
        boolean wasCrawling = (Boolean)this.entityData.get(IS_CRAWLING);
        if (this.getDemonState() == 1 && needsToCrouch && !((Boolean)this.entityData.get(IS_CLIMBING)).booleanValue()) {
            this.crawlTimer = 15;
        }
        if (this.crawlTimer > 0) {
            --this.crawlTimer;
        }
        boolean isCrawling = this.crawlTimer > 0;
        this.entityData.set(IS_CRAWLING, (Object)isCrawling);
        if (wasCrawling != isCrawling) {
            this.refreshDimensions();
        }
    }

    public EntityDimensions getDefaultDimensions(Pose pose) {
        return EntityDimensions.fixed((float)0.8f, (float)((Boolean)this.entityData.get(IS_CRAWLING) != false ? 1.8f : 3.8f));
    }

    public boolean onClimbable() {
        return (Boolean)this.entityData.get(IS_CLIMBING);
    }

    public void triggerAttack() {
        this.triggerAnim("action_controller", "attack");
    }

    public void triggerLeap() {
        this.triggerAnim("action_controller", "leap");
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController((GeoAnimatable)this, "movement_controller", 5, state -> {
            if (this.isDeadOrDying()) {
                return state.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
            }
            if (((Boolean)this.entityData.get(IS_CRAWLING)).booleanValue()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("crawl"));
            }
            if (((Boolean)this.entityData.get(IS_CLIMBING)).booleanValue()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("climb"));
            }
            if (state.isMoving()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop(this.getDemonState() == 1 ? "chase" : "walk"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }));
        AnimationController actionController = new AnimationController((GeoAnimatable)this, "action_controller", 5, state -> PlayState.STOP);
        actionController.triggerableAnim("attack", RawAnimation.begin().thenPlay("attack"));
        actionController.triggerableAnim("leap", RawAnimation.begin().thenPlay("leap"));
        controllers.add(actionController);
    }

    public boolean canStandOnFluid(FluidState f) {
        return f.is(FluidTags.WATER);
    }

    public boolean hasCustomName() {
        return false;
    }

    public boolean shouldShowName() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public void push(@NotNull Entity e) {
    }

    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    public ItemStack getItemBySlot(EquipmentSlot s) {
        return ItemStack.EMPTY;
    }

    public void setItemSlot(EquipmentSlot s, ItemStack st) {
    }

    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

