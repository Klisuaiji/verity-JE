/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  varmite.verity.entity.AI.DemonAttackGoal
 *  varmite.verity.entity.custom.VerityDemonEntity
 */
package varmite.verity.entity.AI;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import varmite.verity.entity.custom.VerityDemonEntity;

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

    protected void checkAndPerformAttack(LivingEntity target, double distance) {
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

    protected void resetAttackCooldown() {
        this.attackCooldown = this.canBeReplacedBy(20);
    }
}

