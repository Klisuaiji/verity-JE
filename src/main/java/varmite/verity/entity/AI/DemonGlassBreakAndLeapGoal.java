/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  varmite.verity.entity.AI.DemonGlassBreakAndLeapGoal
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.entity.AI;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.sounds.ModSounds;

public class DemonGlassBreakAndLeapGoal
extends Goal {
    private final VerityDemonEntity demon;
    private LivingEntity target;
    private int leapCooldown = 0;
    private BlockPos targetedGlass = null;

    public DemonGlassBreakAndLeapGoal(VerityDemonEntity demon) {
        this.demon = demon;
        this.canBeReplacedBy(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        this.target = this.demon.getTarget();
        if (this.target == null || !this.target.isAlive()) {
            return false;
        }
        if (this.leapCooldown > 0) {
            --this.leapCooldown;
            return false;
        }
        this.targetedGlass = this.findGlassInWay();
        return this.targetedGlass != null;
    }

    public void start() {
        if (this.targetedGlass != null) {
            this.shatterGlassArea(this.targetedGlass);
            this.demon.triggerAttack();
            this.demon.level().createTick(null, this.demon.blockPosition(), (SoundEvent)ModSounds.JUMPSCARE.get(), SoundSource.HOSTILE, 2.0f, 1.0f);
            double heightDiff = (double)this.targetedGlass.getY() - this.demon.getY();
            double yBoost = 0.5;
            if (heightDiff > 0.0) {
                yBoost += heightDiff * 0.25;
            }
            Vec3 jumpDir = this.target.position().subtract(this.demon.position()).normalize();
            this.demon.setDeltaMovement(jumpDir.x * 1.8, yBoost, jumpDir.z * 1.8);
            this.demon.forceCrawl(30);
            this.leapCooldown = 100;
        }
    }

    private void shatterGlassArea(BlockPos center) {
        Level level = this.demon.level();
        for (int x = -1; x <= 2; ++x) {
            for (int y = -1; y <= 2; ++y) {
                for (int z = -1; z <= 2; ++z) {
                    BlockPos pos = center.offset(x, y, z);
                    if (!this.isGlass(pos)) continue;
                    level.destroyBlock(pos, false);
                }
            }
        }
    }

    private BlockPos findGlassInWay() {
        Vec3 dir = this.target.position().subtract(this.demon.position()).normalize();
        BlockPos feet = this.demon.blockPosition();
        for (int i = 1; i <= 4; ++i) {
            for (int yOffset = 0; yOffset <= 3; ++yOffset) {
                BlockPos scanPos = BlockPos.offset((double)(this.demon.getX() + dir.x * (double)i), (double)(feet.getY() + yOffset), (double)(this.demon.getZ() + dir.z * (double)i));
                if (!this.isGlass(scanPos)) continue;
                return scanPos;
            }
        }
        return null;
    }

    private boolean isGlass(BlockPos pos) {
        BlockState state = this.demon.level().getBlockState(pos);
        String blockName = state.getBlock().getDescriptionId().toLowerCase();
        return state.is(Blocks.GLASS) || state.is(Blocks.GLASS_PANE) || blockName.contains("glass") || blockName.contains("pane");
    }
}

