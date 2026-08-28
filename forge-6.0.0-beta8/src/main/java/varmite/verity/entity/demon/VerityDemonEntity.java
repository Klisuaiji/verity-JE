/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
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
 *  net.minecraft.world.entity.Entity$RemovalReason
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
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.EmptyBlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.Animation$LoopType
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package varmite.verity.entity.demon;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import varmite.verity.client.sound.ClientSoundHandler;
import varmite.verity.entity.demon.goals.DemonAttackGoal;
import varmite.verity.entity.demon.goals.DemonBreakDoorGoal;
import varmite.verity.entity.demon.goals.DemonClimbGoal;
import varmite.verity.entity.demon.goals.DemonGlassBreakAndLeapGoal;
import varmite.verity.entity.demon.goals.DemonStareAndBreakGoal;
import varmite.verity.entity.demon.goals.DemonWindowStalkGoal;
import varmite.verity.entity.demon.pathfinding.DemonPathNavigation;

public class VerityDemonEntity
extends PathfinderMob
implements GeoEntity,
Enemy {
    private ChunkPos lastForcedChunk = null;
    private int crawlTimer = 0;
    private int stuckTicks = 0;
    private int eatTicks = 0;
    private int grabTicks = 0;
    private static final int GRAB_LIFT_TICKS = 20;
    private static final int GRAB_HOLD_TICKS = 28;
    private static final double GRAB_MAX_CARRY = 1.5;
    private Vec3 lastPos = null;
    public boolean isPlayingChaseSound = false;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final boolean LOG_ANIMATION_CHANGES = false;
    private static final Logger ANIM_LOG = LogUtils.getLogger();
    private String lastLoggedAnimation = "";
    private double lastLoggedAt = -1.0;
    public float animHeadPitch;
    public float animHeadYaw;
    public float writtenHeadPitch;
    public float writtenHeadYaw;
    private static final int IDLE_DELAY_TICKS = 15;
    private double stoppedSinceTick = -1.0;
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WINDOW = RawAnimation.begin().thenLoop("window");
    private static final RawAnimation CHASE = RawAnimation.begin().thenLoop("chase");
    private static final RawAnimation CLIMB = RawAnimation.begin().thenLoop("climb");
    private static final RawAnimation EAT = RawAnimation.begin().thenLoop("eat");
    private static final RawAnimation ATTACK = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation THROW = RawAnimation.begin().then("attack_throw", Animation.LoopType.PLAY_ONCE);
    public static final String THROW_TRIGGER = "attack_throw";
    private static final RawAnimation PREPARE_FOR_CHASE = RawAnimation.begin().then("prepare_for_chase", Animation.LoopType.PLAY_ONCE);
    public static final String PREPARE_FOR_CHASE_TRIGGER = "prepare_for_chase";
    private static final int MOVEMENT_TRANSITION_TICKS = 5;
    private static final double PREPARE_FOR_CHASE_SECONDS = 2.25;
    public static final int PREPARE_FOR_CHASE_TICKS = (int)Math.round(45.0);
    private static final EntityDataAccessor<Integer> DEMON_STATE = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> IS_CLIMBING = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> HUNT_PHASE = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> IS_CRAWLING = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> IS_EATING = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> IS_GRABBING = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> GRABBED_ENTITY_ID = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> IS_STALKING_WINDOW = SynchedEntityData.m_135353_(VerityDemonEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private boolean climbGoalActive;
    private static final float STANDING_HEIGHT = 4.8f;
    private static final float CRAWLING_HEIGHT = 1.8f;
    private static final int STUCK_REPATH_TICKS = 20;
    private static final int STUCK_CHEW_TICKS = 40;
    private static final int STUCK_SHOVE_TICKS = 100;

    public boolean isStuck() {
        return this.stuckTicks > 40;
    }

    public void resetStuckTimer() {
        this.stuckTicks = 0;
    }

    public VerityDemonEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.m_274367_(1.5f);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.m_21441_(BlockPathTypes.WATER_BORDER, 0.0f);
        this.m_21530_();
    }

    protected PathNavigation m_6037_(Level level) {
        return new DemonPathNavigation((Mob)this, level);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DEMON_STATE, (Object)0);
        this.f_19804_.m_135372_(IS_CLIMBING, (Object)false);
        this.f_19804_.m_135372_(IS_CRAWLING, (Object)false);
        this.f_19804_.m_135372_(HUNT_PHASE, (Object)0);
        this.f_19804_.m_135372_(IS_EATING, (Object)false);
        this.f_19804_.m_135372_(IS_GRABBING, (Object)false);
        this.f_19804_.m_135372_(GRABBED_ENTITY_ID, (Object)0);
        this.f_19804_.m_135372_(IS_STALKING_WINDOW, (Object)false);
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        if (IS_CRAWLING.equals(key)) {
            this.m_6210_();
        }
        super.m_7350_(key);
    }

    public void setHuntPhase(int phase) {
        this.f_19804_.m_135381_(HUNT_PHASE, (Object)phase);
    }

    public int getHuntPhase() {
        return (Integer)this.f_19804_.m_135370_(HUNT_PHASE);
    }

    public void setDemonState(int state) {
        this.f_19804_.m_135381_(DEMON_STATE, (Object)state);
    }

    public int getDemonState() {
        return (Integer)this.f_19804_.m_135370_(DEMON_STATE);
    }

    public boolean isEating() {
        return (Boolean)this.f_19804_.m_135370_(IS_EATING);
    }

    public boolean isGrabbing() {
        return (Boolean)this.f_19804_.m_135370_(IS_GRABBING);
    }

    public void setClimbing(boolean climbing) {
        this.f_19804_.m_135381_(IS_CLIMBING, (Object)climbing);
    }

    public void setClimbGoalActive(boolean active) {
        this.climbGoalActive = active;
    }

    public void setStalkingWindow(boolean stalking) {
        this.f_19804_.m_135381_(IS_STALKING_WINDOW, (Object)stalking);
    }

    public boolean isStalkingWindow() {
        return (Boolean)this.f_19804_.m_135370_(IS_STALKING_WINDOW);
    }

    public void triggerPrepareForChase() {
        this.triggerAnim("movement_controller", PREPARE_FOR_CHASE_TRIGGER);
    }

    public void startEating() {
        this.eatTicks = 60;
        this.f_19804_.m_135381_(IS_EATING, (Object)true);
    }

    public void startGrabbing(LivingEntity target) {
        this.f_19804_.m_135381_(IS_GRABBING, (Object)true);
        this.f_19804_.m_135381_(GRABBED_ENTITY_ID, (Object)target.m_19879_());
        this.grabTicks = 0;
        this.triggerAnim("movement_controller", THROW_TRIGGER);
    }

    @Nullable
    public LivingEntity getGrabbedEntity() {
        int id = (Integer)this.f_19804_.m_135370_(GRABBED_ENTITY_ID);
        if (id == 0) {
            return null;
        }
        Entity entity = this.m_9236_().m_6815_(id);
        return entity instanceof LivingEntity ? (LivingEntity)entity : null;
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(1, (Goal)new DemonGlassBreakAndLeapGoal(this));
        this.f_21345_.m_25352_(2, (Goal)new DemonBreakDoorGoal(this));
        this.f_21345_.m_25352_(3, (Goal)new DemonWindowStalkGoal(this));
        this.f_21345_.m_25352_(4, (Goal)new DemonStareAndBreakGoal(this));
        this.f_21345_.m_25352_(4, (Goal)new DemonClimbGoal(this));
        this.f_21345_.m_25352_(5, (Goal)new DemonAttackGoal(this));
        this.f_21345_.m_25352_(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.f_21345_.m_25352_(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0f, 1.0f));
        this.f_21346_.m_25352_(0, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(1, (Goal)new NearestAttackableTargetGoal<Villager>((Mob)this, Villager.class, false){

            public boolean m_8036_() {
                return !VerityDemonEntity.this.isEating() && !VerityDemonEntity.this.isGrabbing() && super.m_8036_();
            }

            public void m_8056_() {
                super.m_8056_();
                VerityDemonEntity.this.setDemonState(1);
            }
        });
        this.f_21346_.m_25352_(2, (Goal)new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, false){

            public boolean m_8036_() {
                boolean noVillagers = VerityDemonEntity.this.m_9236_().m_45976_(Villager.class, VerityDemonEntity.this.m_20191_().m_82400_(64.0)).isEmpty();
                return VerityDemonEntity.this.getDemonState() == 1 && noVillagers && !VerityDemonEntity.this.isEating() && !VerityDemonEntity.this.isGrabbing() && super.m_8036_();
            }
        });
    }

    public void forceCrawl(int ticks) {
        this.crawlTimer = Math.max(this.crawlTimer, ticks);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22276_, 400.0).m_22268_(Attributes.f_22279_, 0.52).m_22268_(Attributes.f_22277_, 512.0).m_22268_(Attributes.f_22281_, 19.0).m_22268_(Attributes.f_22282_, 1.0).m_22268_(Attributes.f_22278_, 1.0);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        Player player;
        LivingEntity attacker;
        Entity entity;
        if (source.m_276093_(DamageTypes.f_268671_) || source.m_276093_(DamageTypes.f_268612_)) {
            return false;
        }
        boolean wasHurt = super.m_6469_(source, amount);
        if (wasHurt && (entity = source.m_7639_()) instanceof LivingEntity && (!((attacker = (LivingEntity)entity) instanceof Player) || !(player = (Player)attacker).m_7500_() && !player.m_5833_())) {
            if (this.isEating()) {
                this.eatTicks = 0;
                this.f_19804_.m_135381_(IS_EATING, (Object)false);
            }
            if (this.isGrabbing()) {
                this.f_19804_.m_135381_(IS_GRABBING, (Object)false);
                this.f_19804_.m_135381_(GRABBED_ENTITY_ID, (Object)0);
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
        Vec3 start = new Vec3(player.m_20185_(), player.m_20188_(), player.m_20189_());
        double distance = start.m_82554_(end = new Vec3(this.m_20185_(), this.getLookAnchorY(), this.m_20189_()));
        if (distance > 128.0) {
            return false;
        }
        Vec3 dir = end.m_82546_(start).m_82541_();
        Level level = this.m_9236_();
        for (double d = 0.0; d < distance; d += 1.0) {
            String name;
            Vec3 point = start.m_82549_(dir.m_82490_(d));
            BlockPos pos = BlockPos.m_274446_((Position)point);
            BlockState state = level.m_8055_(pos);
            if (state.m_60812_((BlockGetter)level, pos).m_83281_() || (name = state.m_60734_().m_7705_().toLowerCase()).contains("glass") || name.contains("pane") || state.m_204336_(BlockTags.f_13035_)) continue;
            return false;
        }
        return true;
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            if (this.getDemonState() == 1 && !this.isPlayingChaseSound) {
                this.isPlayingChaseSound = true;
                ClientSoundHandler.playDemonChaseSound(this);
            }
            return;
        }
        ServerLevel level = (ServerLevel)this.m_9236_();
        ChunkPos currentChunk = this.m_146902_();
        if (this.getDemonState() == 1 && this.m_5448_() != null) {
            if (this.lastForcedChunk == null || !this.lastForcedChunk.equals((Object)currentChunk)) {
                if (this.lastForcedChunk != null) {
                    level.m_8602_(this.lastForcedChunk.f_45578_, this.lastForcedChunk.f_45579_, false);
                }
                level.m_8602_(currentChunk.f_45578_, currentChunk.f_45579_, true);
                this.lastForcedChunk = currentChunk;
            }
        } else {
            this.releaseForcedChunk();
        }
        if (this.isGrabbing()) {
            ++this.grabTicks;
            LivingEntity grabbed = this.getGrabbedEntity();
            if (grabbed == null || !grabbed.m_6084_()) {
                this.f_19804_.m_135381_(IS_GRABBING, (Object)false);
                this.f_19804_.m_135381_(GRABBED_ENTITY_ID, (Object)0);
                return;
            }
            this.m_21573_().m_26573_();
            this.m_20334_(0.0, 0.0, 0.0);
            double liftProgress = Math.min(1.0, (double)this.grabTicks / 20.0);
            Vec3 held = this.m_20182_().m_82549_(this.m_20154_().m_82490_(1.5)).m_82520_(0.0, 2.0 + liftProgress, 0.0);
            Vec3 carry = held.m_82546_(grabbed.m_20182_());
            if (carry.m_82556_() > 2.25) {
                carry = carry.m_82541_().m_82490_(1.5);
            }
            grabbed.m_20256_(carry);
            grabbed.f_19864_ = true;
            grabbed.f_19789_ = 0.0f;
            if (this.grabTicks >= 28) {
                Vec3 launchVector = grabbed.m_20182_().m_82546_(this.m_20182_()).m_82541_().m_82542_(4.0, 1.2, 4.0).m_82520_(0.0, 0.2, 0.0);
                grabbed.m_20256_(launchVector);
                if (grabbed instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)grabbed;
                    serverPlayer.m_9236_().m_5594_(null, this.m_20183_(), SoundEvents.f_12230_, SoundSource.HOSTILE, 1.5f, 1.0f);
                    serverPlayer.f_8906_.m_9829_((Packet)new ClientboundSetEntityMotionPacket((Entity)serverPlayer));
                }
                this.getGrabbedEntity().m_6469_(this.getGrabbedEntity().m_269291_().m_268989_(), 5.0f);
                this.f_19804_.m_135381_(IS_GRABBING, (Object)false);
                this.f_19804_.m_135381_(GRABBED_ENTITY_ID, (Object)0);
            }
            return;
        }
        if (this.isEating()) {
            level.m_8767_((ParticleOptions)DustParticleOptions.f_123656_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 20, 0.5, 0.5, 0.5, 0.05);
            BlockPos centerPos = this.m_20183_();
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    BlockPos currentPos = centerPos.m_7918_(i, 0, j);
                    BlockPos belowPos = currentPos.m_7495_();
                    if (!level.m_8055_(currentPos).m_247087_() || !level.m_8055_(belowPos).m_60783_((BlockGetter)level, belowPos, Direction.UP)) continue;
                    level.m_7731_(currentPos, Blocks.f_50088_.m_49966_(), 3);
                }
            }
        }
        if (this.eatTicks > 0) {
            --this.eatTicks;
            this.m_21573_().m_26573_();
            this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
            this.m_146922_(this.f_19859_);
            this.m_146926_(this.f_19860_);
            this.m_5616_(this.f_20886_);
            this.m_5618_(this.f_20884_);
            if (this.eatTicks <= 0) {
                this.f_19804_.m_135381_(IS_EATING, (Object)false);
            }
        }
        LivingEntity target = this.m_5448_();
        if (!(this.getDemonState() != 1 || target != null && target.m_6084_())) {
            this.m_6710_(null);
            this.setHuntPhase(0);
            this.m_21573_().m_26573_();
            return;
        }
        if (target != null) {
            if (this.lastPos == null) {
                this.lastPos = this.m_20182_();
            }
            if (this.f_19797_ % 10 == 0) {
                if (this.m_20182_().m_82557_(this.lastPos) < 0.05) {
                    this.stuckTicks += 10;
                    if (this.getDemonState() == 1) {
                        this.tryUnstick(target);
                    }
                } else {
                    this.stuckTicks = 0;
                }
                this.lastPos = this.m_20182_();
            }
        } else {
            this.stuckTicks = 0;
        }
        BlockPos feet = this.m_20183_();
        if (this.getDemonState() == 1 && target != null) {
            AABB box = this.m_20191_().m_82377_(0.5, 0.5, 0.5);
            BlockPos.m_121990_((BlockPos)BlockPos.m_274561_((double)box.f_82288_, (double)box.f_82289_, (double)box.f_82290_), (BlockPos)BlockPos.m_274561_((double)box.f_82291_, (double)box.f_82292_, (double)box.f_82293_)).forEach(pos -> {
                BlockState state = this.m_9236_().m_8055_(pos);
                String blockName = state.m_60734_().m_7705_().toLowerCase();
                if (state.m_204336_(BlockTags.f_13035_) || blockName.contains("glass") || blockName.contains("pane")) {
                    this.m_9236_().m_46961_(pos, true);
                }
            });
        }
        this.updateClimbing(target);
        boolean needsToCrouch = false;
        Predicate<BlockState> isHardCeiling = state -> {
            String name = state.m_60734_().m_7705_().toLowerCase();
            return !state.m_60812_((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.f_121853_).m_83281_() && !state.m_204336_(BlockTags.f_13035_) && !name.contains("glass") && !name.contains("pane");
        };
        if (isHardCeiling.test(this.m_9236_().m_8055_(feet.m_6630_(2))) || isHardCeiling.test(this.m_9236_().m_8055_(feet.m_6630_(3)))) {
            needsToCrouch = true;
        }
        if (!needsToCrouch && this.getDemonState() == 1 && target != null) {
            Vec3 dir = target.m_20182_().m_82546_(this.m_20182_());
            Vec3 horizDir = new Vec3(dir.f_82479_, 0.0, dir.f_82481_);
            if (horizDir.m_82556_() > 0.01) {
                horizDir = horizDir.m_82541_();
                for (int i = 1; i <= 5; ++i) {
                    BlockPos forwardPos = BlockPos.m_274561_((double)(this.m_20185_() + horizDir.f_82479_ * (double)i), (double)this.m_20186_(), (double)(this.m_20189_() + horizDir.f_82481_ * (double)i));
                    if (isHardCeiling.test(this.m_9236_().m_8055_(forwardPos)) || isHardCeiling.test(this.m_9236_().m_8055_(forwardPos.m_6630_(1)))) break;
                    if (!isHardCeiling.test(this.m_9236_().m_8055_(forwardPos.m_6630_(2))) && !isHardCeiling.test(this.m_9236_().m_8055_(forwardPos.m_6630_(3)))) continue;
                    needsToCrouch = true;
                    break;
                }
            }
        }
        boolean wasCrawling = (Boolean)this.f_19804_.m_135370_(IS_CRAWLING);
        if (needsToCrouch && !((Boolean)this.f_19804_.m_135370_(IS_CLIMBING)).booleanValue()) {
            this.crawlTimer = 15;
        }
        if (this.crawlTimer > 0) {
            --this.crawlTimer;
        }
        this.f_19804_.m_135381_(IS_CRAWLING, (Object)(this.crawlTimer > 0 ? 1 : 0));
        if (wasCrawling != this.crawlTimer > 0) {
            this.m_6210_();
        }
    }

    private void updateClimbing(@Nullable LivingEntity target) {
        if (this.climbGoalActive) {
            return;
        }
        boolean climbing = false;
        if (target != null && this.getDemonState() == 1 && !this.isEating() && !this.isGrabbing()) {
            boolean targetIsAbove = target.m_20186_() > this.m_20186_() + 1.0;
            boolean bl = climbing = this.f_19862_ && (targetIsAbove || this.m_21573_().m_26571_());
            if (climbing) {
                if (targetIsAbove) {
                    this.m_21566_().m_6849_(target.m_20185_(), target.m_20186_(), target.m_20189_(), 1.2);
                }
                this.clearOverhead();
            }
        }
        this.f_19804_.m_135381_(IS_CLIMBING, (Object)climbing);
    }

    private void clearOverhead() {
        Level level = this.m_9236_();
        BlockPos head = this.m_20183_().m_6630_((int)Math.ceil(this.m_20206_()));
        for (BlockPos pos : new BlockPos[]{head, head.m_7494_()}) {
            BlockState state = level.m_8055_(pos);
            if (state.m_60812_((BlockGetter)level, pos).m_83281_() || !(state.m_60800_((BlockGetter)level, pos) >= 0.0f)) continue;
            level.m_46961_(pos, true);
            this.triggerAttack();
        }
    }

    private void tryUnstick(LivingEntity target) {
        if (this.climbGoalActive) {
            this.resetStuckTimer();
            return;
        }
        if (this.stuckTicks < 20) {
            return;
        }
        this.m_21573_().m_26573_();
        this.m_21573_().m_5624_((Entity)target, 1.0);
        if (this.stuckTicks < 40) {
            return;
        }
        this.chewForward(target);
        if (this.stuckTicks < 100) {
            return;
        }
        Vec3 dir = target.m_20182_().m_82546_(this.m_20182_());
        Vec3 horizontal = new Vec3(dir.f_82479_, 0.0, dir.f_82481_);
        if (horizontal.m_82556_() > 1.0E-4) {
            horizontal = horizontal.m_82541_();
            this.m_20334_(horizontal.f_82479_ * 0.5, 0.55, horizontal.f_82481_ * 0.5);
            this.f_19812_ = true;
        }
        this.resetStuckTimer();
    }

    private void chewForward(LivingEntity target) {
        Vec3 dir = target.m_20182_().m_82546_(this.m_20182_());
        Vec3 horizontal = new Vec3(dir.f_82479_, 0.0, dir.f_82481_);
        if (horizontal.m_82556_() < 1.0E-4) {
            return;
        }
        horizontal = horizontal.m_82541_();
        Level level = this.m_9236_();
        BlockPos ahead = BlockPos.m_274561_((double)(this.m_20185_() + horizontal.f_82479_), (double)this.m_20186_(), (double)(this.m_20189_() + horizontal.f_82481_));
        for (int y = 0; y <= 2; ++y) {
            BlockPos pos = ahead.m_6630_(y);
            BlockState state = level.m_8055_(pos);
            if (state.m_60812_((BlockGetter)level, pos).m_83281_() || !(state.m_60800_((BlockGetter)level, pos) >= 0.0f)) continue;
            level.m_46961_(pos, true);
            this.triggerAttack();
        }
    }

    public boolean m_21532_() {
        return true;
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128405_("DemonState", this.getDemonState());
        tag.m_128405_("HuntPhase", this.getHuntPhase());
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        this.setDemonState(tag.m_128451_("DemonState"));
        this.setHuntPhase(tag.m_128451_("HuntPhase"));
    }

    public void m_142687_(Entity.RemovalReason reason) {
        this.releaseForcedChunk();
        super.m_142687_(reason);
    }

    private void releaseForcedChunk() {
        Level level;
        if (this.lastForcedChunk != null && (level = this.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_8602_(this.lastForcedChunk.f_45578_, this.lastForcedChunk.f_45579_, false);
            this.lastForcedChunk = null;
        }
    }

    public EntityDimensions m_6972_(Pose pose) {
        return EntityDimensions.m_20398_((float)0.4f, (float)((Boolean)this.f_19804_.m_135370_(IS_CRAWLING) != false ? 1.8f : 4.8f));
    }

    public double getLookAnchorY() {
        return this.m_20186_() + (double)2.4f;
    }

    public boolean m_6147_() {
        return (Boolean)this.f_19804_.m_135370_(IS_CLIMBING);
    }

    public void triggerAttack() {
        this.triggerAnim("action_controller", "attack_trigger");
    }

    private PlayState play(AnimationState<VerityDemonEntity> state, RawAnimation animation, String name) {
        return state.setAndContinue(animation);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController movementController = new AnimationController((GeoAnimatable)this, "movement_controller", 5, state -> {
            if (this.m_21224_()) {
                return PlayState.STOP;
            }
            if (this.isEating()) {
                return PlayState.STOP;
            }
            if (((Boolean)this.f_19804_.m_135370_(IS_CLIMBING)).booleanValue()) {
                return this.play((AnimationState<VerityDemonEntity>)state, CLIMB, "climb");
            }
            if (state.isMoving()) {
                this.stoppedSinceTick = -1.0;
                return this.play((AnimationState<VerityDemonEntity>)state, CHASE, "chase");
            }
            double now = state.getAnimationTick();
            if (this.stoppedSinceTick < 0.0) {
                this.stoppedSinceTick = now;
            }
            if (this.getDemonState() == 1 && now - this.stoppedSinceTick < 15.0) {
                return this.play((AnimationState<VerityDemonEntity>)state, CHASE, "chase");
            }
            state.getController().setAnimationSpeed(1.0);
            if (this.isStalkingWindow()) {
                return this.play((AnimationState<VerityDemonEntity>)state, WINDOW, "window");
            }
            return this.play((AnimationState<VerityDemonEntity>)state, IDLE, "idle");
        });
        movementController.triggerableAnim(PREPARE_FOR_CHASE_TRIGGER, PREPARE_FOR_CHASE);
        movementController.triggerableAnim(THROW_TRIGGER, THROW);
        controllers.add(new AnimationController[]{movementController});
        AnimationController actionController = new AnimationController((GeoAnimatable)this, "action_controller", 5, state -> {
            if (((Boolean)this.f_19804_.m_135370_(IS_EATING)).booleanValue()) {
                return state.setAndContinue(EAT);
            }
            return PlayState.STOP;
        });
        actionController.triggerableAnim("attack_trigger", ATTACK);
        controllers.add(new AnimationController[]{actionController});
    }

    public boolean m_203441_(FluidState f) {
        return f.m_205070_(FluidTags.f_13131_);
    }

    public boolean m_8077_() {
        return false;
    }

    public boolean m_6052_() {
        return false;
    }

    public boolean m_6094_() {
        return false;
    }

    public void m_7334_(@NotNull Entity e) {
    }

    public Iterable<ItemStack> m_6168_() {
        return Collections.singleton(ItemStack.f_41583_);
    }

    public ItemStack m_6844_(EquipmentSlot s) {
        return ItemStack.f_41583_;
    }

    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

