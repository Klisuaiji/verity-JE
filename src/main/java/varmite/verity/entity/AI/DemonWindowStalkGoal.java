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
 *  varmite.verity.entity.AI.DemonWindowStalkGoal
 *  varmite.verity.entity.custom.VerityDemonEntity
 */
package varmite.verity.entity.AI;

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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.custom.VerityDemonEntity;

public class DemonWindowStalkGoal
extends Goal {
    private final VerityDemonEntity demon;
    private Player targetPlayer;
    private int stareTicks;
    private final TargetingConditions targetingConditions;

    public DemonWindowStalkGoal(VerityDemonEntity demon) {
        this.demon = demon;
        this.stareTicks = 0;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.forCombat().range(128.0).ignoreLineOfSight();
    }

    public boolean canContinueToUse() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 0) {
            return false;
        }
        return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isCreative() && !this.targetPlayer.isSpectator();
    }

    public boolean canUse() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 0) {
            return false;
        }
        double cx = this.demon.getX();
        double cy = this.demon.getEyeY();
        double cz = this.demon.getZ();
        AABB aabb = new AABB(cx - 128.0, cy - 128.0, cz - 128.0, cx + 128.0, cy + 128.0, cz + 128.0);
        this.targetPlayer = this.demon.level().getNearestEntity(Player.class, this.targetingConditions, (LivingEntity)this.demon, cx, cy, cz, aabb);
        if (this.targetPlayer != null && (this.targetPlayer.isCreative() || this.targetPlayer.isSpectator())) {
            return false;
        }
        return this.targetPlayer != null;
    }

    public void start() {
        this.stareTicks = 0;
    }

    public void tick() {
        if (this.targetPlayer == null) {
            return;
        }
        this.demon.getLookControl().setLookAt((Entity)this.targetPlayer, 30.0f, 30.0f);
        this.demon.getNavigation().stop();
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
        Vec3 playerView = player.getViewVector(1.0f).normalize();
        return playerView.dot(playerToDemon = new Vec3(this.demon.getX() - player.getX(), this.demon.getEyeY() - player.getEyeY(), this.demon.getZ() - player.getZ()).normalize()) > 0.98 && this.demon.hasLineOfSightThroughGlass(player);
    }

    private void disappear() {
        Level level = this.demon.level();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.sendParticles((ParticleOptions)ParticleTypes.LARGE_SMOKE, this.demon.getX(), this.demon.getY() + 1.5, this.demon.getZ(), 30, 0.5, 1.0, 0.5, 0.01);
            serverLevel.playSound((Player)null, this.demon.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0f, 0.6f);
        }
        this.demon.setHuntPhase(1);
        this.teleportAway();
    }

    private void teleportAway() {
        for (int i = 0; i < 15; ++i) {
            double distance = 16.0 + this.demon.getRandom().nextDouble() * 16.0;
            double angle = this.demon.getRandom().nextDouble() * 2.0 * Math.PI;
            double newX = this.demon.getX() + Math.cos(angle) * distance;
            double newZ = this.demon.getZ() + Math.sin(angle) * distance;
            int newY = this.demon.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)newX, (int)newZ);
            BlockPos groundPos = new BlockPos((int)newX, newY - 1, (int)newZ);
            if (!this.demon.level().getFluidState(groundPos).isEmpty()) continue;
            this.demon.teleportTo(newX, (double)newY, newZ);
            this.demon.getNavigation().stop();
            return;
        }
        this.demon.discard();
    }
}

