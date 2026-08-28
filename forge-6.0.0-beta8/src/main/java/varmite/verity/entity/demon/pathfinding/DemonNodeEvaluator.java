/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 *  net.minecraftforge.common.Tags$Blocks
 */
package varmite.verity.entity.demon.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraftforge.common.Tags;

public class DemonNodeEvaluator
extends WalkNodeEvaluator {
    private static final int CRAWL_CLEARANCE = 2;

    public void m_6028_(PathNavigationRegion region, Mob mob) {
        super.m_6028_(region, mob);
        this.f_77316_ = Math.min(this.f_77316_, 2);
    }

    public BlockPathTypes m_8086_(BlockGetter level, int x, int y, int z) {
        BlockState state = level.m_8055_(new BlockPos(x, y, z));
        if (DemonNodeEvaluator.isSoftCover(state)) {
            return BlockPathTypes.OPEN;
        }
        return super.m_8086_(level, x, y, z);
    }

    private static boolean isSoftCover(BlockState state) {
        return state.m_204336_(BlockTags.f_13035_) || state.m_204336_(Tags.Blocks.GLASS) || state.m_204336_(Tags.Blocks.GLASS_PANES);
    }
}

