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
        this.canBeReplacedBy(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.forNonCombat().forCombat(64.0).ignoreLineOfSight();
    }

    public boolean canUse() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 1) {
            return false;
        }
        if (this.hasTriggeredTransformation && this.delayTicks <= 0) {
            return false;
        }
        this.targetPlayer = this.demon.level().getEntities(this.targetingConditions, (LivingEntity)this.demon, this.demon.getX(), this.demon.getEyeY(), this.demon.getZ());
        if (this.targetPlayer != null && (this.targetPlayer.m_7500_() || this.targetPlayer.m_5833_())) {
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
        return this.targetPlayer != null && this.targetPlayer.isAlive() && !this.targetPlayer.m_7500_() && !this.targetPlayer.m_5833_();
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
        double distanceSqr = this.demon.m_20280_((Entity)this.targetPlayer);
        if (distanceSqr > 1024.0) {
            this.demon.getNavigation().createPath((Entity)this.targetPlayer, 1.0);
        } else {
            this.demon.getNavigation().stop();
        }
        if (this.isPlayerLookingAtDemon(this.targetPlayer)) {
            this.demon.level().createTick(null, this.demon.blockPosition(), (SoundEvent)ModSounds.BONE_0.get(), SoundSource.HOSTILE, 1.5f, 1.0f);
            this.hasTriggeredTransformation = true;
            this.delayTicks = 10;
        }
    }

    public void stop() {
        if (this.hasTriggeredTransformation && this.targetPlayer != null && this.targetPlayer.isAlive()) {
            this.demon.setDemonState(1);
            this.demon.setTarget((LivingEntity)this.targetPlayer);
            this.targetPlayer.addAdditionalSaveData(new MobEffectInstance(MobEffects.DARKNESS, 1200, 0, false, false, false));
        }
    }

    private boolean isPlayerLookingAtDemon(Player player) {
        Vec3 playerToDemon;
        Vec3 playerView = player.m_20252_(1.0f).m_82541_();
        return playerView.m_82526_(playerToDemon = new Vec3(this.demon.getX() - player.getX(), this.demon.getEyeY() - player.getEyeY(), this.demon.getZ() - player.getZ()).m_82541_()) > 0.98 && this.demon.hasLineOfSightThroughGlass(player);
    }
}

