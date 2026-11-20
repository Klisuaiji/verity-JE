/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  varmite.verity.entity.demon.goals.DemonAttackGoal
 *  varmite.verity.entity.demon.VerityDemonEntity
 */
package varmite.verity.entity.demon.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import varmite.verity.entity.demon.VerityDemonEntity;

public class DemonAttackGoal
extends MeleeAttackGoal {
    private final VerityDemonEntity demon;
    private int attackCooldown = 0;

    public DemonAttackGoal(VerityDemonEntity demon) {
        super((PathfinderMob)demon, 1.0, true);
        this.demon = demon;
    }

    public boolean canUse() {
        return super.canUse() && !this.demon.isEating() && !this.demon.isGrabbing();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.demon.isEating() && !this.demon.isGrabbing();
    }

    public void start() {
        super.start();
        this.attackCooldown = 0;
    }

    public void stop() {
        super.stop();
    }

    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        if (distance <= this.getAttackReachSqr(target) && this.attackCooldown <= 0) {
            this.resetAttackCooldown();
            if (this.demon.getRandom().nextFloat() < 0.5f) {
                this.demon.startGrabbing(target);
            } else {
                this.demon.triggerAttack();
                this.mob.doHurtTarget((Entity)target);
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return (double)(this.mob.getBbWidth() * 2.0f * this.mob.getBbWidth() * 2.0f + target.getBbWidth());
    }

    @Override
    protected void resetAttackCooldown() {
        this.attackCooldown = this.adjustedTickDelay(20);
    }
}

