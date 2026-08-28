/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.entity.AI;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.custom.VerityDemonEntity;

public class DemonAttackGoal
extends Goal {
    private final VerityDemonEntity demon;
    private int animTimer;
    private int attackCooldown;

    public DemonAttackGoal(VerityDemonEntity demon) {
        this.demon = demon;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return this.demon.getDemonState() == 1 && this.demon.getTarget() != null && this.demon.getTarget().isAlive();
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void start() {
        this.animTimer = 0;
    }

    public void tick() {
        boolean isDirectlyUnderPillar;
        LivingEntity target = this.demon.getTarget();
        if (target == null) {
            return;
        }
        this.demon.getLookControl().setLookAt((Entity)target, 30.0f, 30.0f);
        if (this.animTimer > 0) {
            --this.animTimer;
            this.demon.getNavigation().stop();
            this.demon.resetStuckTimer();
            return;
        }
        boolean blockedOff = this.demon.getNavigation().isDone() && this.demon.distanceToSqr((Entity)target) > 16.0 && this.demon.horizontalCollision;
        double dx = target.getX() - this.demon.getX();
        double dz = target.getZ() - this.demon.getZ();
        double horizDistSqr = dx * dx + dz * dz;
        boolean targetIsAbove = target.getY() >= this.demon.getY() + 1.0;
        boolean bl = isDirectlyUnderPillar = targetIsAbove && horizDistSqr <= 12.0;
        if (this.demon.onClimbable() || isDirectlyUnderPillar && this.demon.horizontalCollision) {
            Direction facing = this.demon.getDirection();
            this.demon.getMoveControl().setWantedPosition(this.demon.getX() + (double)facing.getStepX(), target.getY(), this.demon.getZ() + (double)facing.getStepZ(), 1.6);
            this.demon.resetStuckTimer();
        } else if ((this.demon.isStuck() || blockedOff) && !isDirectlyUnderPillar) {
            if (this.demon.tickCount % 15 == 0) {
                this.breachWall(target);
            }
        } else if (this.demon.getNavigation().isDone() || this.demon.tickCount % 20 == 0) {
            this.demon.getNavigation().moveTo((Entity)target, 1.6);
        }
        this.attackCooldown = Math.max(this.attackCooldown - 1, 0);
        if (this.demon.distanceToSqr((Entity)target) <= ((double)this.demon.getBbWidth() + 1.2) * ((double)this.demon.getBbWidth() + 1.2) && this.attackCooldown <= 0) {
            this.demon.triggerAttack();
            this.animTimer = 30;
            this.demon.doHurtTarget((Entity)target);
            this.attackCooldown = 40;
        }
    }

    private void breachWall(LivingEntity target) {
        Vec3 dir = target.position().subtract(this.demon.position()).normalize();
        Direction facing = this.demon.getDirection();
        BlockPos targetPos = this.demon.blockPosition().relative(facing);
        boolean brokeBlock = false;
        int height = this.demon.getPose() == Pose.CROUCHING ? 2 : 3;
        for (int i = 0; i <= height; ++i) {
            BlockPos p = targetPos.above(i);
            BlockState state = this.demon.level().getBlockState(p);
            if (!state.blocksMotion() || !(state.getDestroySpeed((BlockGetter)this.demon.level(), p) >= 0.0f)) continue;
            this.demon.level().destroyBlock(p, true);
            brokeBlock = true;
        }
        if (brokeBlock) {
            this.demon.triggerAttack();
            this.demon.resetStuckTimer();
            this.animTimer = 15;
        } else if (this.demon.isStuck()) {
            this.demon.setPos(this.demon.getX() + dir.x * 0.5, this.demon.getY(), this.demon.getZ() + dir.z * 0.5);
            this.demon.resetStuckTimer();
        }
    }
}

