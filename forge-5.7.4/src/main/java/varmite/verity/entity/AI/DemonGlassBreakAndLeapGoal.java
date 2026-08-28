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
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        this.target = this.demon.m_5448_();
        if (this.target == null || !this.target.m_6084_()) {
            return false;
        }
        if (this.leapCooldown > 0) {
            --this.leapCooldown;
            return false;
        }
        this.targetedGlass = this.findGlassInWay();
        return this.targetedGlass != null;
    }

    public void m_8056_() {
        if (this.targetedGlass != null) {
            this.shatterGlassArea(this.targetedGlass);
            this.demon.triggerAttack();
            this.demon.m_9236_().m_5594_(null, this.demon.m_20183_(), (SoundEvent)ModSounds.JUMPSCARE.get(), SoundSource.HOSTILE, 2.0f, 1.0f);
            double heightDiff = (double)this.targetedGlass.m_123342_() - this.demon.m_20186_();
            double yBoost = 0.5;
            if (heightDiff > 0.0) {
                yBoost += heightDiff * 0.25;
            }
            Vec3 jumpDir = this.target.m_20182_().m_82546_(this.demon.m_20182_()).m_82541_();
            this.demon.m_20334_(jumpDir.f_82479_ * 1.8, yBoost, jumpDir.f_82481_ * 1.8);
            this.demon.forceCrawl(30);
            this.leapCooldown = 100;
        }
    }

    private void shatterGlassArea(BlockPos center) {
        Level level = this.demon.m_9236_();
        for (int x = -1; x <= 2; ++x) {
            for (int y = -1; y <= 2; ++y) {
                for (int z = -1; z <= 2; ++z) {
                    BlockPos pos = center.m_7918_(x, y, z);
                    if (!this.isGlass(pos)) continue;
                    level.m_46961_(pos, false);
                }
            }
        }
    }

    private BlockPos findGlassInWay() {
        Vec3 dir = this.target.m_20182_().m_82546_(this.demon.m_20182_()).m_82541_();
        BlockPos feet = this.demon.m_20183_();
        for (int i = 1; i <= 4; ++i) {
            for (int yOffset = 0; yOffset <= 3; ++yOffset) {
                BlockPos scanPos = BlockPos.m_274561_((double)(this.demon.m_20185_() + dir.f_82479_ * (double)i), (double)(feet.m_123342_() + yOffset), (double)(this.demon.m_20189_() + dir.f_82481_ * (double)i));
                if (!this.isGlass(scanPos)) continue;
                return scanPos;
            }
        }
        return null;
    }

    private boolean isGlass(BlockPos pos) {
        BlockState state = this.demon.m_9236_().m_8055_(pos);
        String blockName = state.m_60734_().m_7705_().toLowerCase();
        return state.m_60713_(Blocks.f_50058_) || state.m_60713_(Blocks.f_50185_) || blockName.contains("glass") || blockName.contains("pane");
    }
}

