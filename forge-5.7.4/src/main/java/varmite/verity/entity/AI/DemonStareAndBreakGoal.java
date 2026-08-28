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
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.m_148353_().m_26883_(64.0).m_148355_();
    }

    public boolean m_8036_() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 1) {
            return false;
        }
        if (this.hasTriggeredTransformation && this.delayTicks <= 0) {
            return false;
        }
        this.targetPlayer = this.demon.m_9236_().m_45949_(this.targetingConditions, (LivingEntity)this.demon, this.demon.m_20185_(), this.demon.m_20188_(), this.demon.m_20189_());
        if (this.targetPlayer != null && (this.targetPlayer.m_7500_() || this.targetPlayer.m_5833_())) {
            return false;
        }
        return this.targetPlayer != null;
    }

    public boolean m_8045_() {
        if (this.demon.getDemonState() != 0 || this.demon.getHuntPhase() != 1) {
            return false;
        }
        if (this.hasTriggeredTransformation) {
            return this.delayTicks > 0;
        }
        return this.targetPlayer != null && this.targetPlayer.m_6084_() && !this.targetPlayer.m_7500_() && !this.targetPlayer.m_5833_();
    }

    public void m_8056_() {
        this.demon.m_21573_().m_26573_();
        if (this.demon.getDemonState() == 0) {
            this.hasTriggeredTransformation = false;
            this.delayTicks = 0;
        }
    }

    public void m_8037_() {
        if (this.hasTriggeredTransformation) {
            this.demon.m_21573_().m_26573_();
            --this.delayTicks;
            return;
        }
        if (this.targetPlayer == null) {
            return;
        }
        this.demon.m_21563_().m_24960_((Entity)this.targetPlayer, 30.0f, 30.0f);
        double distanceSqr = this.demon.m_20280_((Entity)this.targetPlayer);
        if (distanceSqr > 1024.0) {
            this.demon.m_21573_().m_5624_((Entity)this.targetPlayer, 1.0);
        } else {
            this.demon.m_21573_().m_26573_();
        }
        if (this.isPlayerLookingAtDemon(this.targetPlayer)) {
            this.demon.m_9236_().m_5594_(null, this.demon.m_20183_(), (SoundEvent)ModSounds.BONE_0.get(), SoundSource.HOSTILE, 1.5f, 1.0f);
            this.hasTriggeredTransformation = true;
            this.delayTicks = 10;
        }
    }

    public void m_8041_() {
        if (this.hasTriggeredTransformation && this.targetPlayer != null && this.targetPlayer.m_6084_()) {
            this.demon.setDemonState(1);
            this.demon.m_6710_((LivingEntity)this.targetPlayer);
            this.targetPlayer.m_7292_(new MobEffectInstance(MobEffects.f_216964_, 1200, 0, false, false, false));
        }
    }

    private boolean isPlayerLookingAtDemon(Player player) {
        Vec3 playerToDemon;
        Vec3 playerView = player.m_20252_(1.0f).m_82541_();
        return playerView.m_82526_(playerToDemon = new Vec3(this.demon.m_20185_() - player.m_20185_(), this.demon.m_20188_() - player.m_20188_(), this.demon.m_20189_() - player.m_20189_()).m_82541_()) > 0.98 && this.demon.hasLineOfSightThroughGlass(player);
    }
}

