/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.level.pathfinder.PathFinder
 */
package varmite.verity.entity.demon.pathfinding;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import varmite.verity.entity.demon.pathfinding.DemonNodeEvaluator;

public class DemonPathNavigation
extends GroundPathNavigation {
    private static final float PATHFINDING_RANGE = 64.0f;

    public DemonPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    protected PathFinder m_5532_(int maxVisitedNodes) {
        this.f_26508_ = new DemonNodeEvaluator();
        this.f_26508_.m_77351_(true);
        this.f_26508_.m_77355_(true);
        return new PathFinder(this.f_26508_, maxVisitedNodes * 2);
    }

    protected Path m_26551_(Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy) {
        return this.m_148222_(targets, regionOffset, offsetUpward, accuracy, 64.0f);
    }
}

