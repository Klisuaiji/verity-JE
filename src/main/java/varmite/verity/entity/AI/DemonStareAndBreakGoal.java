/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  varmite.verity.entity.AI.DemonStareAndBreakGoal
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.entity.AI;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.sounds.ModSounds;

public class DemonStareAndBreakGoal
extends Goal {
    private final VerityDemonEntity demon;
    private Player targetPlayer;
    private boolean hasTriggeredTransformation;
    private int delayTicks;
    private final TargetingConditions targetingConditions;

    public DemonStareAndBreakGoal(VerityDemonEntity demon) {
        this.demon = demon;
        this.hasTriggeredTransformation = false;
        this.delayTicks = 0;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.forCombat().range(64.0).ignoreLineOfSight();
    }

    public boolean canUse() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 1) {
            return false;
        }
        if (this.hasTriggeredTransformation && this.delayTicks <= 0) {
            return false;
        }
        double cx = this.demon.getX();
        double cy = this.demon.getEyeY();
        double cz = this.demon.getZ();
        AABB aabb = new AABB(cx - 64.0, cy - 64.0, cz - 64.0, cx + 64.0, cy + 64.0, cz + 64.0);
        this.targetPlayer = this.demon.level().getNearestEntity(Player.class, this.targetingConditions, (LivingEntity)this.demon, cx, cy, cz, aabb);
        if (this.targetPlayer != null && (this.targetPlayer.isCreative() || this.targetPlayer.isSpectator())) {
            return false;
        }
        return this.targetPlayer != null;
    }

    public boolean canContinueToUse() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 1) {
            return false;
        }
        if (this.hasTriggeredTransformation) {
            return this.delayTicks > 0;
        }
        return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.isCreative() && !this.targetPlayer.isSpectator();
    }

    public void start() {
        this.demon.getNavigation().stop();
        if (this.demon.getDemonState() == 0) {
            this.hasTriggeredTransformation = false;
            this.delayTicks = 0;
        }
    }

    public void tick() {
        if (this.hasTriggeredTransformation) {
            this.demon.getNavigation().stop();
            --this.delayTicks;
            return;
        }
        if (this.targetPlayer == null) {
            return;
        }
        this.demon.getLookControl().setLookAt((Entity)this.targetPlayer, 30.0f, 30.0f);
        double distanceSqr = this.demon.distanceToSqr((Entity)this.targetPlayer);
        if (distanceSqr > 1024.0) {
            this.demon.getNavigation().createPath((Entity)this.targetPlayer, 0);
        } else {
            this.demon.getNavigation().stop();
        }
        if (this.isPlayerLookingAtDemon(this.targetPlayer)) {
            this.demon.level().playSound((Player)null, this.demon.blockPosition(), (SoundEvent)ModSounds.BONE_0.get(), SoundSource.HOSTILE, 1.5f, 1.0f);
            this.hasTriggeredTransformation = true;
            this.delayTicks = 10;
        }
    }

    public void stop() {
        if (this.hasTriggeredTransformation && this.targetPlayer != null && this.targetPlayer.isAlive()) {
            this.demon.setDemonState(1);
            this.demon.setTarget((LivingEntity)this.targetPlayer);
            this.targetPlayer.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 1200, 0, false, false, false));
        }
    }

    private boolean isPlayerLookingAtDemon(Player player) {
        Vec3 playerToDemon;
        Vec3 playerView = player.getViewVector(1.0f).normalize();
        return playerView.dot(playerToDemon = new Vec3(this.demon.getX() - player.getX(), this.demon.getEyeY() - player.getEyeY(), this.demon.getZ() - player.getZ()).normalize()) > 0.98 && this.demon.hasLineOfSightThroughGlass(player);
    }
}

