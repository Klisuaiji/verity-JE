/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.world.entity.Entity
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
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
import varmite.verity.entity.demon.VerityDemonEntity;
import varmite.verity.entity.demon.rendering.VerityDemonModel;

public class VerityDemonRenderer
extends GeoEntityRenderer<VerityDemonEntity> {
    private static final float SCALE = 1.5f;

    public VerityDemonRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new VerityDemonModel());
        this.withScale(1.5f);
    }

    public void render(VerityDemonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        int actualDeathTime = entity.f_20919_;
        entity.f_20919_ = 0;
        super.m_7392_((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        entity.f_20919_ = actualDeathTime;
    }

    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Map<String, float[]> meshes = BedrockPolyMesh.forModel(VerityDemonModel.MESH);
        float[] vertices = meshes.get(bone.getName());
        if (vertices == null) {
            return;
        }
        PoseStack.Pose pose = poseStack.m_85850_();
        Matrix4f position = pose.m_252922_();
        Matrix3f normal = pose.m_252943_();
        for (int i = 0; i < vertices.length; i += 8) {
            buffer.m_252986_(position, vertices[i], vertices[i + 1], vertices[i + 2]).m_85950_(red, green, blue, alpha).m_7421_(vertices[i + 3], vertices[i + 4]).m_86008_(packedOverlay).m_85969_(packedLight).m_252939_(normal, vertices[i + 5], vertices[i + 6], vertices[i + 7]).m_5752_();
        }
    }
}

