/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.neoforged.neoforge.client.model.BakedModelWrapper
 *  org.jetbrains.annotations.Nullable
 *  varmite.verity.environment.items.UnshadedBakedModel
 */
package varmite.verity.environment.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

public class UnshadedBakedModel
extends BakedModelWrapper<BakedModel> {
    public UnshadedBakedModel(BakedModel originalModel) {
        super(originalModel);
    }

    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        List<BakedQuad> originalQuads = super.getQuads(state, side, rand);
        ArrayList<BakedQuad> glowingQuads = new ArrayList<BakedQuad>();
        for (BakedQuad quad : originalQuads) {
            int[] vertexData = (int[])quad.getVertices().clone();
            for (int i = 0; i < 4; ++i) {
                int packedUpNormal;
                // DefaultVertexFormat.BLOCK = Position(3 int) + Color(1) + UV0(2) + UV2(1) + Normal(1)，共 8 int/顶点。
                // 偏移依次为 0..2=pos、3=color、4..5=u/v、6=lightmap、7=normal。
                vertexData[i * 8 + 3] = -1;                    // color = white (0xFFFFFFFF ABGR)
                vertexData[i * 8 + 6] = 0xF000F0;              // lightmap = fullbright
                vertexData[i * 8 + 7] = packedUpNormal = 32512; // normal = (0, 1, 0) packed
            }
            glowingQuads.add(new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), false));
        }
        return glowingQuads;
    }
}

