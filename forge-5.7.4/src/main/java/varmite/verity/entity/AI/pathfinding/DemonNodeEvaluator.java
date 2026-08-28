/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 */
package varmite.verity.entity.AI.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DemonNodeEvaluator
extends WalkNodeEvaluator {
    public BlockPathTypes m_8086_(BlockGetter level, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        String blockName = level.m_8055_(pos).m_60734_().m_7705_().toLowerCase();
        if (blockName.contains("glass") || blockName.contains("pane") || blockName.contains("leaves")) {
            return BlockPathTypes.OPEN;
        }
        return super.m_8086_(level, x, y, z);
    }
}

