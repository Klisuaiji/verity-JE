/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.pathfinder.PathType
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 *  varmite.verity.entity.AI.pathfinding.DemonNodeEvaluator
 */
package varmite.verity.entity.AI.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DemonNodeEvaluator
extends WalkNodeEvaluator {
    public PathType prepare(BlockGetter level, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        String blockName = level.getBlockState(pos).getBlock().getDescriptionId().toLowerCase();
        if (blockName.contains("glass") || blockName.contains("pane") || blockName.contains("leaves")) {
            return PathType.OPEN;
        }
        return super.prepare(level, x, y, z);
    }
}

