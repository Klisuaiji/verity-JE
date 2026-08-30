/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.world.entity.Entity
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  varmite.verity.entity.demon.rendering.VerityDemonModel
 *  varmite.verity.entity.demon.rendering.VerityDemonRenderer
 *  varmite.verity.entity.demon.VerityDemonEntity
 */
package varmite.verity.entity.demon.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import varmite.verity.client.render.BedrockPolyMesh;
import varmite.verity.entity.demon.rendering.VerityDemonModel;
import varmite.verity.entity.demon.VerityDemonEntity;

public class VerityDemonRenderer
extends GeoEntityRenderer<VerityDemonEntity> {
    private static final float SCALE = 1.5f;

    public VerityDemonRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new VerityDemonModel());
        this.withScale(SCALE);
    }

    public void render(VerityDemonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        int actualDeathTime = entity.deathTime;
        entity.deathTime = 0;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        entity.deathTime = actualDeathTime;
    }

    /**
     * 6.0.0-beta.8 — the upstream geo.json is bone-hierarchy only (no cubes), so the
     * normal GeckoLib cube pass draws nothing. The visible surface comes from the
     * Bedrock poly_mesh file, emitted here per bone on top of the default pass.
     */
    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Map<String, float[]> meshes = BedrockPolyMesh.forModel(VerityDemonModel.MESH);
        float[] vertices = meshes.get(bone.getName());
        if (vertices == null) {
            return;
        }
        PoseStack.Pose pose = poseStack.last();
        Matrix4f position = pose.pose();
        Matrix3f normal = pose.normal();
        // STRIDE = 8: position xyz, uv, normal xyz
        for (int i = 0; i < vertices.length; i += BedrockPolyMesh.STRIDE) {
            buffer.vertex(position, vertices[i], vertices[i + 1], vertices[i + 2])
                    .color(red, green, blue, alpha)
                    .uv(vertices[i + 3], vertices[i + 4])
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(normal, vertices[i + 5], vertices[i + 6], vertices[i + 7])
                    .endVertex();
        }
    }
}

