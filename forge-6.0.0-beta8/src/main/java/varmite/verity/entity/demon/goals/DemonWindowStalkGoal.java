/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.entity.demon.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.demon.VerityDemonEntity;

public class DemonWindowStalkGoal
extends Goal {
    private final VerityDemonEntity demon;
    private Player targetPlayer;
    private int stareTicks;
    private final TargetingConditions targetingConditions;

    public DemonWindowStalkGoal(VerityDemonEntity demon) {
        this.demon = demon;
        this.stareTicks = 0;
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.m_148353_().m_26883_(128.0).m_148355_();
    }

    public boolean m_8045_() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 0) {
            return false;
        }
        return this.targetPlayer != null && this.targetPlayer.m_6084_() && !this.targetPlayer.m_7500_() && !this.targetPlayer.m_5833_();
    }

    public boolean m_8036_() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 0) {
            return false;
        }
        this.targetPlayer = this.demon.m_9236_().m_45949_(this.targetingConditions, (LivingEntity)this.demon, this.demon.m_20185_(), this.demon.getLookAnchorY(), this.demon.m_20189_());
        if (this.targetPlayer != null && (this.targetPlayer.m_7500_() || this.targetPlayer.m_5833_())) {
            return false;
        }
        return this.targetPlayer != null;
    }

    public void m_8056_() {
        this.stareTicks = 0;
        this.demon.setStalkingWindow(true);
    }

    public void m_8041_() {
        this.demon.setStalkingWindow(false);
        this.targetPlayer = null;
    }

    public void m_8037_() {
        if (this.targetPlayer == null) {
            return;
        }
        this.demon.m_21563_().m_24960_((Entity)this.targetPlayer, 30.0f, 30.0f);
        this.demon.m_21573_().m_26573_();
        if (this.isPlayerLookingAtDemon(this.targetPlayer)) {
            ++this.stareTicks;
            if (this.stareTicks >= 5) {
                this.disappear();
            }
        } else {
            this.stareTicks = 0;
        }
    }

    private boolean isPlayerLookingAtDemon(Player player) {
        Vec3 playerToDemon;
        Vec3 playerView = player.m_20252_(1.0f).m_82541_();
        return playerView.m_82526_(playerToDemon = new Vec3(this.demon.m_20185_() - player.m_20185_(), this.demon.getLookAnchorY() - player.m_20188_(), this.demon.m_20189_() - player.m_20189_()).m_82541_()) > 0.98 && this.demon.hasLineOfSightThroughGlass(player);
    }

    private void disappear() {
        Level level = this.demon.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123755_, this.demon.m_20185_(), this.demon.m_20186_() + 1.5, this.demon.m_20189_(), 30, 0.5, 1.0, 0.5, 0.01);
            serverLevel.m_5594_(null, this.demon.m_20183_(), SoundEvents.f_11852_, SoundSource.HOSTILE, 1.0f, 0.6f);
        }
        this.demon.setHuntPhase(1);
        this.teleportAway();
    }

    private void teleportAway() {
        for (int i = 0; i < 15; ++i) {
            double distance = 16.0 + this.demon.m_217043_().m_188500_() * 16.0;
            double angle = this.demon.m_217043_().m_188500_() * 2.0 * Math.PI;
            double newX = this.demon.m_20185_() + Math.cos(angle) * distance;
            double newZ = this.demon.m_20189_() + Math.sin(angle) * distance;
            int newY = this.demon.m_9236_().m_6924_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)newX, (int)newZ);
            BlockPos groundPos = BlockPos.m_274561_((double)newX, (double)(newY - 1), (double)newZ);
            if (!this.demon.m_9236_().m_6425_(groundPos).m_76178_()) continue;
            this.demon.m_6021_(newX, newY, newZ);
            this.demon.m_21573_().m_26573_();
            return;
        }
        this.demon.m_146870_();
    }
}

