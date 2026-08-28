/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package varmite.verity.entity.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public final class SphereMesh {
    private static final float TEXTURE_SCALE = 1.3f;
    private static final float TEXTURE_SQUISH_Y = 0.9f;

    private SphereMesh() {
    }

    public static void render(PoseStack poseStack, MultiBufferSource bufferSource, ResourceLocation texture, float radius, int latitudes, int longitudes, int packedLight, int r, int g, int b, int a) {
        VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110458_((ResourceLocation)texture));
        Matrix4f pose = poseStack.m_85850_().m_252922_();
        Matrix3f normal = poseStack.m_85850_().m_252943_();
        SphereMesh.drawSphere(pose, normal, consumer, radius, latitudes, longitudes, packedLight, r, g, b, a);
    }

    private static void drawSphere(Matrix4f pose, Matrix3f normal, VertexConsumer consumer, float radius, int latitudes, int longitudes, int light, int r, int g, int b, int a) {
        for (int lat = 0; lat <= latitudes; ++lat) {
            float theta1 = (float)((double)lat * Math.PI / (double)latitudes);
            float theta2 = (float)((double)(lat + 1) * Math.PI / (double)latitudes);
            for (int lon = 0; lon <= longitudes; ++lon) {
                float phi1 = (float)((double)(lon * 2) * Math.PI / (double)longitudes);
                float phi2 = (float)((double)((lon + 1) * 2) * Math.PI / (double)longitudes);
                SphereMesh.addVertex(pose, normal, consumer, radius, theta1, phi1, light, r, g, b, a);
                SphereMesh.addVertex(pose, normal, consumer, radius, theta2, phi1, light, r, g, b, a);
                SphereMesh.addVertex(pose, normal, consumer, radius, theta2, phi2, light, r, g, b, a);
                SphereMesh.addVertex(pose, normal, consumer, radius, theta1, phi2, light, r, g, b, a);
            }
        }
    }

    private static void addVertex(Matrix4f pose, Matrix3f normalMatrix, VertexConsumer consumer, float radius, float theta, float phi, int light, int r, int g, int b, int a) {
        float x = (float)((double)radius * Math.sin(theta) * Math.cos(phi));
        float y = (float)((double)radius * Math.cos(theta));
        float z = (float)((double)radius * Math.sin(theta) * Math.sin(phi));
        float u = (float)((double)phi / (Math.PI * 2));
        float v = 0.25f + (float)((double)theta / Math.PI) * 0.5f;
        u = Mth.m_14036_((float)(0.5f + (u - 0.5f) * 1.3f), (float)0.0f, (float)1.0f);
        v = Mth.m_14036_((float)(0.5f + (v - 0.5f) * 1.4444444f), (float)0.0f, (float)1.0f);
        float length = (float)Math.sqrt(x * x + y * y + z * z);
        float nx = x / length;
        float ny = y / length;
        float nz = z / length;
        consumer.m_252986_(pose, x, y, z).m_6122_(r, g, b, a).m_7421_(u, v).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_252939_(normalMatrix, nx, ny, nz).m_5752_();
    }
}

