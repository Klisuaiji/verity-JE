/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.BreakDoorGoal
 */
package varmite.verity.entity.AI;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import varmite.verity.entity.custom.VerityDemonEntity;

public class DemonBreakDoorGoal
extends BreakDoorGoal {
    private final VerityDemonEntity demon;

    public DemonBreakDoorGoal(VerityDemonEntity demon) {
        super((Mob)demon, x -> true);
        this.demon = demon;
    }

    public boolean canUse() {
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        return super.canContinueToUse();
    }

    public void tick() {
        super.tick();
        if (this.demon.tickCount % 15 == 0) {
            this.demon.triggerAttack();
        }
    }
}

