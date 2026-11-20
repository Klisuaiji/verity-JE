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

/**
 * Ported from the official 6.0.0-beta.8 distribution (CFR decompile) to NeoForge 1.21.1.
 *
 * Lets the demon scale vertical terrain to reach a player standing well above it:
 * it paths to the foot of a tall enough column, then hauls itself up while smashing
 * any block blocking its ascent, and lunges at the target on arrival.
 *
 * Only fires in demon state 1 (hunt), when not eating/grabbing, when the height gain
 * is at least {@link #MIN_HEIGHT_GAIN} blocks and the target is closer vertically than
 * horizontally (i.e. actually worth climbing to).
 */
public class DemonClimbGoal extends Goal {
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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
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
        this.target = this.demon.getTarget();
        if (this.target == null || !this.target.isAlive()) {
            return false;
        }
        double gain = this.target.getY() - this.demon.getY();
        if (gain < MIN_HEIGHT_GAIN) {
            return false;
        }
        double dx = this.target.getX() - this.demon.getX();
        double dz = this.target.getZ() - this.demon.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        // Not worth climbing if the target is farther away horizontally than vertically.
        if (horizontal > MAX_HORIZONTAL_RANGE || gain < horizontal) {
            return false;
        }
        this.column = this.findClimbableColumn();
        return this.column != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.demon.getDemonState() != 1 || this.column == null) {
            return false;
        }
        if (this.target == null || !this.target.isAlive()) {
            return false;
        }
        if (this.demon.isEating() || this.demon.isGrabbing()) {
            return false;
        }
        if (this.stallTicks > STALL_LIMIT || this.runTicks > MAX_ATTEMPT_TICKS) {
            return false;
        }
        return this.demon.getY() < this.target.getY() - 0.5;
    }

    @Override
    public void start() {
        this.lastY = this.demon.getY();
        this.stallTicks = 0;
        this.runTicks = 0;
        this.demon.setClimbGoalActive(true);
    }

    @Override
    public void stop() {
        boolean reachedThem = this.target != null && this.demon.getY() >= this.target.getY() - 0.5;
        if (reachedThem) {
            // Broke through to the target's level — lunge at them.
            Vec3 lunge = new Vec3(this.target.getX() - this.demon.getX(), 0.0, this.target.getZ() - this.demon.getZ());
            if (lunge.lengthSqr() > 1.0E-4) {
                lunge = lunge.normalize().scale(0.45);
                this.demon.setDeltaMovement(lunge.x, 0.25, lunge.z);
                this.demon.hurtMarked = true;
            }
        } else {
            // Gave up (stalled / timed out / target moved) — back off before retrying.
            this.cooldown = RETRY_COOLDOWN;
        }
        this.demon.setClimbGoalActive(false);
        this.demon.setClimbing(false);
        this.demon.getNavigation().stop();
        this.column = null;
        this.target = null;
        this.stallTicks = 0;
        this.runTicks = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.column == null || this.target == null) {
            return;
        }
        ++this.runTicks;
        this.demon.getLookControl().setLookAt((Entity) this.target, 30.0f, 30.0f);
        double dx = (double) this.column.getX() + 0.5 - this.demon.getX();
        double dz = (double) this.column.getZ() + 0.5 - this.demon.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal > HUG_DISTANCE) {
            this.approachFoot(dx, dz);
            return;
        }
        this.haulUp(dx, dz, horizontal);
    }

    /** Walk to the base of the column before starting the ascent. */
    private void approachFoot(double dx, double dz) {
        this.demon.setClimbing(false);
        if (this.demon.getNavigation().isDone()) {
            this.demon.getNavigation().moveTo(
                    (double) this.column.getX() + 0.5, this.demon.getY(), (double) this.column.getZ() + 0.5, 1.2);
        }
        this.lastY = this.demon.getY();
    }

    /** Haul straight up while hugging the column, breaking anything in the way. */
    private void haulUp(double dx, double dz, double horizontal) {
        this.demon.getNavigation().stop();
        this.demon.setClimbing(true);
        // `f_19789_` in the 1.20.1 decompile == Entity#yBodyRot. Pinned while climbing so
        // the demon hugs the wall instead of spinning around to face its target.
        this.demon.yBodyRot = 0.0f;
        double hugX = 0.0;
        double hugZ = 0.0;
        if (horizontal > 1.0E-4) {
            hugX = dx / horizontal * HUG_SPEED;
            hugZ = dz / horizontal * HUG_SPEED;
        }
        this.demon.setDeltaMovement(hugX, CLIMB_SPEED, hugZ);
        this.demon.hurtMarked = true;
        this.clearAscent();
        this.stallTicks = this.demon.getY() - this.lastY < 0.04 ? ++this.stallTicks : 0;
        this.lastY = this.demon.getY();
    }

    /** Smash the two blocks directly overhead, so the demon can keep rising. */
    private void clearAscent() {
        Level level = this.demon.level();
        BlockPos head = this.demon.blockPosition().above((int) Math.ceil(this.demon.getBbHeight()));
        for (BlockPos pos : new BlockPos[]{head, head.above()}) {
            BlockState state = level.getBlockState(pos);
            // Skip air, and skip anything unbreakable (destroy speed < 0, e.g. bedrock).
            if (state.getCollisionShape((BlockGetter) level, pos).isEmpty()
                    || !(state.getDestroySpeed((BlockGetter) level, pos) >= 0.0f)) {
                continue;
            }
            level.destroyBlock(pos, true);
            this.demon.triggerAttack();
        }
    }

    /** Find a column under the target with at least MIN_HEIGHT_GAIN blocks of clearance. */
    private BlockPos findClimbableColumn() {
        Level level = this.demon.level();
        BlockPos below = this.target.blockPosition().below();
        if (this.isSolid(below)) {
            return null;
        }
        int run = 0;
        int floor = Math.max(this.demon.getBlockY(), level.getMinBuildHeight());
        for (int y = below.getY(); y >= floor && !this.isSolid(new BlockPos(below.getX(), y, below.getZ())); --y) {
            ++run;
        }
        if ((double) run < MIN_HEIGHT_GAIN) {
            return null;
        }
        return new BlockPos(below.getX(), this.demon.getBlockY(), below.getZ());
    }

    private boolean isSolid(BlockPos pos) {
        BlockState state = this.demon.level().getBlockState(pos);
        return !state.getCollisionShape((BlockGetter) this.demon.level(), pos).isEmpty();
    }
}
