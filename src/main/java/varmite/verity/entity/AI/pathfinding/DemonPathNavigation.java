/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.PathFinder
 *  varmite.verity.entity.AI.pathfinding.DemonNodeEvaluator
 *  varmite.verity.entity.AI.pathfinding.DemonPathNavigation
 */
package varmite.verity.entity.AI.pathfinding;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import varmite.verity.entity.AI.pathfinding.DemonNodeEvaluator;

public class DemonPathNavigation
extends GroundPathNavigation {
    public DemonPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new DemonNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes * 2);
    }
}

