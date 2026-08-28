/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.BreakDoorGoal
 */
package varmite.verity.entity.demon.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import varmite.verity.entity.demon.VerityDemonEntity;

public class DemonBreakDoorGoal
extends BreakDoorGoal {
    private final VerityDemonEntity demon;

    public DemonBreakDoorGoal(VerityDemonEntity demon) {
        super((Mob)demon, x -> true);
        this.demon = demon;
    }

    public boolean m_8036_() {
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        return super.m_8036_();
    }

    public boolean m_8045_() {
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        return super.m_8045_();
    }

    public void m_8037_() {
        super.m_8037_();
        if (this.demon.f_19797_ % 15 == 0) {
            this.demon.triggerAttack();
        }
    }
}

