/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
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
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 *  varmite.verity.client.sound.ClientSoundHandler
 *  varmite.verity.entity.AI.DemonAttackGoal
 *  varmite.verity.entity.AI.DemonBreakDoorGoal
 *  varmite.verity.entity.AI.DemonGlassBreakAndLeapGoal
 *  varmite.verity.entity.AI.DemonStareAndBreakGoal
 *  varmite.verity.entity.AI.DemonWindowStalkGoal
 *  varmite.verity.entity.AI.pathfinding.DemonPathNavigation
 *  varmite.verity.entity.custom.VerityDemonEntity
 */
package varmite.verity.entity.custom;

import java.util.Collections;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
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
    private ChunkPos lastForcedChunk = null;
    private int crawlTimer = 0;
    private int stuckTicks = 0;
    private int eatTicks = 0;
    private int grabTicks = 0;
    private Vec3 lastPos = null;
    public boolean isPlayingChaseSound = false;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final EntityDataAccessor<Integer> DEMON_STATE = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_CLIMBING = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> HUNT_PHASE = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_CRAWLING = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_EATING = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_GRABBING = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> GRABBED_ENTITY_ID = SynchedEntityData.defineId(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);

    public boolean isStuck() {
        return this.stuckTicks > 40;
    }

    public void resetStuckTimer() {
        this.stuckTicks = 0;
    }

    public VerityDemonEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.m_274367_(1.5f);
        this.getPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        this.getPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0f);
        this.setPersistenceRequired();
    }

    protected PathNavigation isInvulnerableTo(Level level) {
        return new DemonPathNavigation((Mob)this, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.defineId(DEMON_STATE, (Object)0);
        this.entityData.defineId(IS_CLIMBING, (Object)false);
        this.entityData.defineId(IS_CRAWLING, (Object)false);
        this.entityData.defineId(HUNT_PHASE, (Object)0);
        this.entityData.defineId(IS_EATING, (Object)false);
        this.entityData.defineId(IS_GRABBING, (Object)false);
        this.entityData.defineId(GRABBED_ENTITY_ID, (Object)0);
    }

    public void hurt(EntityDataAccessor<?> key) {
        if (IS_CRAWLING.equals(key)) {
            this.refreshDimensions();
        }
        super.hurt(key);
    }

    public void setHuntPhase(int phase) {
        this.entityData.get(HUNT_PHASE, (Object)phase);
    }

    public int getHuntPhase() {
        return (Integer)this.entityData.get(HUNT_PHASE);
    }

    public void setDemonState(int state) {
        this.entityData.get(DEMON_STATE, (Object)state);
    }

    public int getDemonState() {
        return (Integer)this.entityData.get(DEMON_STATE);
    }

    public boolean isEating() {
        return (Boolean)this.entityData.get(IS_EATING);
    }

    public boolean isGrabbing() {
        return (Boolean)this.entityData.get(IS_GRABBING);
    }

    public void startEating() {
        this.eatTicks = 60;
        this.entityData.get(IS_EATING, (Object)true);
    }

    public void startGrabbing(LivingEntity target) {
        this.entityData.get(IS_GRABBING, (Object)true);
        this.entityData.get(GRABBED_ENTITY_ID, (Object)target.getId());
        this.grabTicks = 0;
    }

    @Nullable
    public LivingEntity getGrabbedEntity() {
        int id = (Integer)this.entityData.get(GRABBED_ENTITY_ID);
        if (id == 0) {
            return null;
        }
        Entity entity = this.level().m_6815_(id);
        return entity instanceof LivingEntity ? (LivingEntity)entity : null;
    }

    protected void registerGoals() {
        this.goalSelector.m_25352_(1, (Goal)new DemonGlassBreakAndLeapGoal(this));
        this.goalSelector.m_25352_(2, (Goal)new DemonBreakDoorGoal(this));
        this.goalSelector.m_25352_(3, (Goal)new DemonWindowStalkGoal(this));
        this.goalSelector.m_25352_(4, (Goal)new DemonStareAndBreakGoal(this));
        this.goalSelector.m_25352_(5, (Goal)new DemonAttackGoal(this));
        this.goalSelector.m_25352_(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.goalSelector.m_25352_(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0f, 1.0f));
        this.targetSelector.m_25352_(0, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.targetSelector.m_25352_(1, (Goal)new Goal() { public boolean canUse() { return false; } });
        this.targetSelector.m_25352_(2, (Goal)new Goal() { public boolean canUse() { return false; } });
    }

    public void forceCrawl(int ticks) {
        this.crawlTimer = Math.max(this.crawlTimer, ticks);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 400.0).add(Attributes.MOVEMENT_SPEED, 0.45).add(Attributes.FOLLOW_RANGE, 512.0).add(Attributes.ATTACK_DAMAGE, 19.0).add(Attributes.ATTACK_KNOCKBACK, 1.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        Player player;
        LivingEntity attacker;
        Entity entity;
        if (source.getFoodExhaustion(DamageTypes.FALL) || source.getFoodExhaustion(DamageTypes.IN_WALL)) {
            return false;
        }
        boolean wasHurt = super.m_6469_(source, amount);
        if (wasHurt && (entity = source.getEntity()) instanceof LivingEntity && (!((attacker = (LivingEntity)entity) instanceof Player) || !(player = (Player)attacker).m_7500_() && !player.m_5833_())) {
            if (this.isEating()) {
                this.eatTicks = 0;
                this.entityData.get(IS_EATING, (Object)false);
            }
            if (this.isGrabbing()) {
                this.entityData.get(IS_GRABBING, (Object)false);
                this.entityData.get(GRABBED_ENTITY_ID, (Object)0);
            }
            if (this.getDemonState() == 0) {
                this.setDemonState(1);
                this.setHuntPhase(1);
            }
        }
        return wasHurt;
    }

    public boolean m_214076_(ServerLevel level, LivingEntity killedEntity) {
        this.startEating();
        return super.m_214076_(level, killedEntity);
    }

    public boolean hasLineOfSightThroughGlass(Player player) {
        Vec3 end;
        Vec3 start = new Vec3(player.getX(), player.getEyeY(), player.getZ());
        double distance = start.m_82554_(end = new Vec3(this.getX(), this.getEyeY(), this.getZ()));
        if (distance > 128.0) {
            return false;
        }
        Vec3 dir = end.m_82546_(start).m_82541_();
        for (double d = 0.0; d < distance; d += 1.0) {
            String name;
            Vec3 point = start.m_82549_(dir.m_82490_(d));
            BlockPos pos = BlockPos.offset((Position)point);
            BlockState state = this.level().getBlockState(pos);
            if (!state.m_280555_() || (name = state.m_60734_().getDescriptionId().toLowerCase()).contains("glass") || name.contains("pane") || state.m_204336_(BlockTags.LEAVES)) continue;
            return false;
        }
        return true;
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.level().isClientSide) {
            if (this.getDemonState() == 1 && !this.isPlayingChaseSound) {
                this.isPlayingChaseSound = true;
                ClientSoundHandler.playDemonChaseSound((VerityDemonEntity)this);
            }
            return;
        }
        ServerLevel level = (ServerLevel)this.level();
        ChunkPos currentChunk = this.chunkPosition();
        if (this.lastForcedChunk == null || !this.lastForcedChunk.equals((Object)currentChunk)) {
            if (this.lastForcedChunk != null) {
                level.m_8602_(this.lastForcedChunk.x, this.lastForcedChunk.z, false);
            }
            level.m_8602_(currentChunk.x, currentChunk.z, true);
            this.lastForcedChunk = currentChunk;
        }
        if (this.isGrabbing()) {
            ++this.grabTicks;
            LivingEntity grabbed = this.getGrabbedEntity();
            if (grabbed == null || !grabbed.isAlive()) {
                this.entityData.get(IS_GRABBING, (Object)false);
                this.entityData.get(GRABBED_ENTITY_ID, (Object)0);
                return;
            }
            this.getNavigation().stop();
            this.m_20334_(0.0, 0.0, 0.0);
            grabbed.m_20334_(0.0, 0.0, 0.0);
            double liftProgress = Math.min(1.0, (double)this.grabTicks / 38.0);
            Vec3 targetPos = this.position().m_82549_(this.getLookAngle().m_82490_(1.5)).m_82520_(0.0, 2.0 + liftProgress, 0.0);
            if (grabbed instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)grabbed;
                serverPlayer.connection.m_9774_(targetPos.x, targetPos.y, targetPos.z, grabbed.getYRot(), grabbed.getXRot());
            } else {
                grabbed.isColliding(targetPos);
            }
            if (this.grabTicks >= 38) {
                Vec3 launchVector = grabbed.position().m_82546_(this.position()).m_82541_().m_82542_(4.0, 1.2, 4.0).m_82520_(0.0, 0.2, 0.0);
                grabbed.setDeltaMovement(launchVector);
                if (grabbed instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)grabbed;
                    serverPlayer.level().createTick(null, this.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.HOSTILE, 1.5f, 1.0f);
                    serverPlayer.connection.send((Packet)new ClientboundSetEntityMotionPacket((Entity)serverPlayer));
                }
                this.getGrabbedEntity().m_6469_(this.getGrabbedEntity().damageSources().fall(), 5.0f);
                this.entityData.get(IS_GRABBING, (Object)false);
                this.entityData.get(GRABBED_ENTITY_ID, (Object)0);
            }
            return;
        }
        if (this.isEating()) {
            level.m_8767_((ParticleOptions)DustParticleOptions.REDSTONE, this.getX(), this.getY(), this.getZ(), 20, 0.5, 0.5, 0.5, 0.05);
            BlockPos centerPos = this.blockPosition();
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    BlockPos currentPos = centerPos.offset(i, 0, j);
                    BlockPos belowPos = currentPos.m_7495_();
                    if (!level.getBlockState(currentPos).m_247087_() || !level.getBlockState(belowPos).m_60783_((BlockGetter)level, belowPos, Direction.UP)) continue;
                    level.setBlock(currentPos, Blocks.REDSTONE_WIRE.defaultBlockState(), 3);
                }
            }
        }
        if (this.eatTicks > 0) {
            --this.eatTicks;
            this.getNavigation().stop();
            this.m_20334_(0.0, this.getDeltaMovement().y, 0.0);
            this.defineSynchedData(this.yRotO);
            this.playerTouch(this.xRotO);
            this.setYHeadRot(this.yHeadRotO);
            this.setYBodyRot(this.yBodyRotO);
            if (this.eatTicks <= 0) {
                this.entityData.get(IS_EATING, (Object)false);
            }
        }
        LivingEntity target = this.getTarget();
        if (!(this.getDemonState() != 1 || target != null && target.isAlive())) {
            this.setTarget(null);
            this.setHuntPhase(0);
            this.getNavigation().stop();
            return;
        }
        if (target != null) {
            if (this.lastPos == null) {
                this.lastPos = this.position();
            }
            if (this.tickCount % 10 == 0) {
                this.stuckTicks = this.position().m_82557_(this.lastPos) < 0.05 ? (this.stuckTicks += 10) : 0;
                this.lastPos = this.position();
            }
        }
        BlockPos feet = this.blockPosition();
        if (this.getDemonState() == 1 && target != null) {
            AABB box = this.getBoundingBox().setMinZ(0.5, 0.5, 0.5);
            BlockPos.getY((BlockPos)BlockPos.offset((double)box.minX, (double)box.minY, (double)box.minZ), (BlockPos)BlockPos.offset((double)box.maxX, (double)box.maxY, (double)box.maxZ)).forEach(pos -> {
                BlockState state = this.level().getBlockState(pos);
                String blockName = state.m_60734_().getDescriptionId().toLowerCase();
                if (state.m_204336_(BlockTags.LEAVES) || blockName.contains("glass") || blockName.contains("pane")) {
                    this.level().destroyBlock(pos, true);
                }
            });
            if (this.getDemonState() == 1 && target != null) {
                double dz;
                double dx;
                double horizontalDistance;
                if (target.getY() > this.getY() && (horizontalDistance = Math.sqrt((dx = target.getX() - this.getX()) * dx + (dz = target.getZ() - this.getZ()) * dz)) < 3.0) {
                    this.getMoveControl().tick(target.getX(), target.getY(), target.getZ(), 1.2);
                }
                if (this.horizontalCollision && !this.isEating()) {
                    this.entityData.get(IS_CLIMBING, (Object)true);
                    int headHeightOffset = (int)Math.ceil(this.getBbHeight());
                    BlockPos overhead1 = feet.m_6630_(headHeightOffset);
                    BlockPos overhead2 = overhead1.m_7494_();
                    for (BlockPos overhead : new BlockPos[]{overhead1, overhead2}) {
                        BlockState state2 = this.level().getBlockState(overhead);
                        if (!state2.m_280555_() || !(state2.m_60800_((BlockGetter)this.level(), overhead) >= 0.0f)) continue;
                        this.level().destroyBlock(overhead, true);
                        this.triggerAttack();
                    }
                } else {
                    this.entityData.get(IS_CLIMBING, (Object)false);
                }
            }
        }
        boolean needsToCrouch = false;
        Predicate<BlockState> isHardCeiling = state -> {
            String name = state.m_60734_().getDescriptionId().toLowerCase();
            return state.m_280555_() && !state.m_204336_(BlockTags.LEAVES) && !name.contains("glass") && !name.contains("pane");
        };
        if (isHardCeiling.test(this.level().getBlockState(feet.m_6630_(2))) || isHardCeiling.test(this.level().getBlockState(feet.m_6630_(3)))) {
            needsToCrouch = true;
        }
        if (!needsToCrouch && this.getDemonState() == 1 && target != null) {
            Vec3 dir = target.position().m_82546_(this.position());
            Vec3 horizDir = new Vec3(dir.x, 0.0, dir.z);
            if (horizDir.m_82556_() > 0.01) {
                horizDir = horizDir.m_82541_();
                for (int i = 1; i <= 5; ++i) {
                    BlockPos forwardPos = BlockPos.offset((double)(this.getX() + horizDir.x * (double)i), (double)this.getY(), (double)(this.getZ() + horizDir.z * (double)i));
                    if (isHardCeiling.test(this.level().getBlockState(forwardPos)) || isHardCeiling.test(this.level().getBlockState(forwardPos.m_6630_(1)))) break;
                    if (!isHardCeiling.test(this.level().getBlockState(forwardPos.m_6630_(2))) && !isHardCeiling.test(this.level().getBlockState(forwardPos.m_6630_(3)))) continue;
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
        this.entityData.get(IS_CRAWLING, (Object)(this.crawlTimer > 0 ? 1 : 0));
        if (wasCrawling != this.crawlTimer > 0) {
            this.refreshDimensions();
        }
    }

    public boolean isPersistenceRequired() {
        return true;
    }

    public EntityDimensions m_6972_(Pose pose) {
        return EntityDimensions.fixed((float)0.4f, (float)((Boolean)this.entityData.get(IS_CRAWLING) != false ? 1.8f : 4.8f));
    }

    public boolean onClimbable() {
        return (Boolean)this.entityData.get(IS_CLIMBING);
    }

    public void triggerAttack() {
        this.triggerAnim("action_controller", "attack_trigger");
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "movement_controller", 5, state -> {
            if (this.isDeadOrDying()) {
                return state.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.model.death"));
            }
            if (this.isGrabbing()) {
                return state.setAndContinue(RawAnimation.begin().thenPlay("animation.model.grab"));
            }
            if (((Boolean)this.entityData.get(IS_CLIMBING)).booleanValue()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("model.animation.climb"));
            }
            if (state.isMoving() || ((Boolean)this.entityData.get(IS_CLIMBING)).booleanValue()) {
                if (this.getDemonState() == 1) {
                    return state.setAndContinue(RawAnimation.begin().thenLoop("animation.model.sprint"));
                }
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation.model.walk"));
            }
            state.getController().setAnimationSpeed(1.0);
            return PlayState.STOP;
        })});
        AnimationController actionController = new AnimationController((GeoAnimatable)this, "action_controller", 5, state -> {
            if (((Boolean)this.entityData.get(IS_EATING)).booleanValue()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation.model.eat"));
            }
            return PlayState.STOP;
        });
        actionController.triggerableAnim("attack_trigger", RawAnimation.begin().thenPlay("animation.model.attack"));
        controllers.add(new AnimationController[]{actionController});
    }

    public boolean onSyncedDataUpdated(FluidState f) {
        return f.m_205070_(FluidTags.WATER);
    }

    public boolean hasCustomName() {
        return false;
    }

    public boolean m_6052_() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public void push(@NotNull Entity e) {
    }

    public Iterable<ItemStack> m_6168_() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    public ItemStack m_6844_(EquipmentSlot s) {
        return ItemStack.EMPTY;
    }

    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

