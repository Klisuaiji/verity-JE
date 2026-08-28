/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
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

    public boolean m_8036_() {
        return super.m_8036_() && !this.demon.isEating() && !this.demon.isGrabbing();
    }

    public boolean m_8045_() {
        return super.m_8045_() && !this.demon.isEating() && !this.demon.isGrabbing();
    }

    public void m_8056_() {
        super.m_8056_();
        this.attackCooldown = 0;
    }

    public void m_8041_() {
        super.m_8041_();
    }

    public void m_8037_() {
        super.m_8037_();
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
    }

    protected void m_6739_(LivingEntity target, double distance) {
        if (distance <= this.m_6639_(target) && this.attackCooldown <= 0) {
            this.m_25563_();
            if (this.demon.m_217043_().m_188501_() < 0.5f) {
                this.demon.startGrabbing(target);
            } else {
                this.demon.triggerAttack();
                this.f_25540_.m_7327_((Entity)target);
            }
        }
    }

    protected void m_25563_() {
        this.attackCooldown = this.m_183277_(20);
    }
}

