/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.entity.demon.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import varmite.verity.entity.demon.VerityDemonEntity;

public class DemonClimbGoal
extends Goal {
    private static final double MIN_HEIGHT_GAIN = 3.0;
    private static final double MAX_HORIZONTAL_RANGE = 16.0;
    private static final double HUG_DISTANCE = 1.6;
    private static final double CLIMB_SPEED = 0.35;
    private static final double HUG_SPEED = 0.2;
    private static final int STALL_LIMIT = 40;
    private static final int MAX_ATTEMPT_TICKS = 200;
    private static final int RETRY_COOLDOWN = 40;
    private final VerityDemonEntity demon;
    private LivingEntity target;
    private BlockPos column;
    private double lastY;
    private int stallTicks;
    private int runTicks;
    private int cooldown;

    public DemonClimbGoal(VerityDemonEntity demon) {
        this.demon = demon;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    public boolean m_8036_() {
        double dz;
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        if (this.demon.getDemonState() != 1) {
            return false;
        }
        if (this.demon.isEating() || this.demon.isGrabbing()) {
            return false;
        }
        this.target = this.demon.m_5448_();
        if (this.target == null || !this.target.m_6084_()) {
            return false;
        }
        double gain = this.target.m_20186_() - this.demon.m_20186_();
        if (gain < 3.0) {
            return false;
        }
        double dx = this.target.m_20185_() - this.demon.m_20185_();
        double horizontal = Math.sqrt(dx * dx + (dz = this.target.m_20189_() - this.demon.m_20189_()) * dz);
        if (horizontal > 16.0 || gain < horizontal) {
            return false;
        }
        this.column = this.findClimbableColumn();
        return this.column != null;
    }

    public boolean m_8045_() {
        if (this.demon.getDemonState() != 1 || this.column == null) {
            return false;
        }
        if (this.target == null || !this.target.m_6084_()) {
            return false;
        }
        if (this.demon.isEating() || this.demon.isGrabbing()) {
            return false;
        }
        if (this.stallTicks > 40 || this.runTicks > 200) {
            return false;
        }
        return this.demon.m_20186_() < this.target.m_20186_() - 0.5;
    }

    public void m_8056_() {
        this.lastY = this.demon.m_20186_();
        this.stallTicks = 0;
        this.runTicks = 0;
        this.demon.setClimbGoalActive(true);
    }

    public void m_8041_() {
        boolean reachedThem;
        boolean bl = reachedThem = this.target != null && this.demon.m_20186_() >= this.target.m_20186_() - 0.5;
        if (reachedThem) {
            Vec3 lunge = new Vec3(this.target.m_20185_() - this.demon.m_20185_(), 0.0, this.target.m_20189_() - this.demon.m_20189_());
            if (lunge.m_82556_() > 1.0E-4) {
                lunge = lunge.m_82541_().m_82490_(0.45);
                this.demon.m_20334_(lunge.f_82479_, 0.25, lunge.f_82481_);
                this.demon.f_19812_ = true;
            }
        } else {
            this.cooldown = 40;
        }
        this.demon.setClimbGoalActive(false);
        this.demon.setClimbing(false);
        this.demon.m_21573_().m_26573_();
        this.column = null;
        this.target = null;
        this.stallTicks = 0;
        this.runTicks = 0;
    }

    public boolean m_183429_() {
        return true;
    }

    public void m_8037_() {
        if (this.column == null || this.target == null) {
            return;
        }
        ++this.runTicks;
        this.demon.m_21563_().m_24960_((Entity)this.target, 30.0f, 30.0f);
        double dx = (double)this.column.m_123341_() + 0.5 - this.demon.m_20185_();
        double dz = (double)this.column.m_123343_() + 0.5 - this.demon.m_20189_();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal > 1.6) {
            this.approachFoot(dx, dz);
            return;
        }
        this.haulUp(dx, dz, horizontal);
    }

    private void approachFoot(double dx, double dz) {
        this.demon.setClimbing(false);
        if (this.demon.m_21573_().m_26571_()) {
            this.demon.m_21573_().m_26519_((double)this.column.m_123341_() + 0.5, this.demon.m_20186_(), (double)this.column.m_123343_() + 0.5, 1.2);
        }
        this.lastY = this.demon.m_20186_();
    }

    private void haulUp(double dx, double dz, double horizontal) {
        this.demon.m_21573_().m_26573_();
        this.demon.setClimbing(true);
        this.demon.f_19789_ = 0.0f;
        double hugX = 0.0;
        double hugZ = 0.0;
        if (horizontal > 1.0E-4) {
            hugX = dx / horizontal * 0.2;
            hugZ = dz / horizontal * 0.2;
        }
        this.demon.m_20334_(hugX, 0.35, hugZ);
        this.demon.f_19812_ = true;
        this.clearAscent();
        this.stallTicks = this.demon.m_20186_() - this.lastY < 0.04 ? ++this.stallTicks : 0;
        this.lastY = this.demon.m_20186_();
    }

    private void clearAscent() {
        Level level = this.demon.m_9236_();
        BlockPos head = this.demon.m_20183_().m_6630_((int)Math.ceil(this.demon.m_20206_()));
        for (BlockPos pos : new BlockPos[]{head, head.m_7494_()}) {
            BlockState state = level.m_8055_(pos);
            if (state.m_60812_((BlockGetter)level, pos).m_83281_() || !(state.m_60800_((BlockGetter)level, pos) >= 0.0f)) continue;
            level.m_46961_(pos, true);
            this.demon.triggerAttack();
        }
    }

    private BlockPos findClimbableColumn() {
        Level level = this.demon.m_9236_();
        BlockPos below = this.target.m_20183_().m_7495_();
        if (this.isSolid(below)) {
            return null;
        }
        int run = 0;
        int floor = Math.max(this.demon.m_146904_(), level.m_141937_());
        for (int y = below.m_123342_(); y >= floor && !this.isSolid(new BlockPos(below.m_123341_(), y, below.m_123343_())); --y) {
            ++run;
        }
        if ((double)run < 3.0) {
            return null;
        }
        return new BlockPos(below.m_123341_(), this.demon.m_146904_(), below.m_123343_());
    }

    private boolean isSolid(BlockPos pos) {
        BlockState state = this.demon.m_9236_().m_8055_(pos);
        return !state.m_60812_((BlockGetter)this.demon.m_9236_(), pos).m_83281_();
    }
}

