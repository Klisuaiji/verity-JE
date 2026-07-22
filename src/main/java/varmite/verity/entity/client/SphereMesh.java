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
 *  varmite.verity.entity.client.SphereMesh
 */
package varmite.verity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/*
 * Exception performing whole class analysis ignored.
 */
public final class SphereMesh {
    private static final float TEXTURE_SCALE = 1.3f;

    private SphereMesh() {
    }

    public static void render(PoseStack poseStack, MultiBufferSource bufferSource, ResourceLocation texture, float radius, int latitudes, int longitudes, int packedLight, int r, int g, int b, int a) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull((ResourceLocation)texture));
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        SphereMesh.drawSphere((Matrix4f)pose, (Matrix3f)normal, (VertexConsumer)consumer, (float)radius, (int)latitudes, (int)longitudes, (int)packedLight, (int)r, (int)g, (int)b, (int)a);
    }

    private static void drawSphere(Matrix4f pose, Matrix3f normal, VertexConsumer consumer, float radius, int latitudes, int longitudes, int light, int r, int g, int b, int a) {
        for (int lat = 0; lat <= latitudes; ++lat) {
            float theta1 = (float)((double)lat * Math.PI / (double)latitudes);
            float theta2 = (float)((double)(lat + 1) * Math.PI / (double)latitudes);
            for (int lon = 0; lon <= longitudes; ++lon) {
                float phi1 = (float)((double)(lon * 2) * Math.PI / (double)longitudes);
                float phi2 = (float)((double)((lon + 1) * 2) * Math.PI / (double)longitudes);
                SphereMesh.addVertex((Matrix4f)pose, (Matrix3f)normal, (VertexConsumer)consumer, (float)radius, (float)theta1, (float)phi1, (int)light, (int)r, (int)g, (int)b, (int)a);
                SphereMesh.addVertex((Matrix4f)pose, (Matrix3f)normal, (VertexConsumer)consumer, (float)radius, (float)theta2, (float)phi1, (int)light, (int)r, (int)g, (int)b, (int)a);
                SphereMesh.addVertex((Matrix4f)pose, (Matrix3f)normal, (VertexConsumer)consumer, (float)radius, (float)theta2, (float)phi2, (int)light, (int)r, (int)g, (int)b, (int)a);
                SphereMesh.addVertex((Matrix4f)pose, (Matrix3f)normal, (VertexConsumer)consumer, (float)radius, (float)theta1, (float)phi2, (int)light, (int)r, (int)g, (int)b, (int)a);
            }
        }
    }

    private static void addVertex(Matrix4f pose, Matrix3f normalMatrix, VertexConsumer consumer, float radius, float theta, float phi, int light, int r, int g, int b, int a) {
        float x = (float)((double)radius * Math.sin(theta) * Math.cos(phi));
        float y = (float)((double)radius * Math.cos(theta));
        float z = (float)((double)radius * Math.sin(theta) * Math.sin(phi));
        float u = (float)((double)phi / (Math.PI * 2));
        float v = 0.25f + (float)((double)theta / Math.PI) * 0.5f;
        u = Mth.sin((float)(0.5f + (u - 0.5f) * 1.3f), (float)0.0f, (float)1.0f);
        v = Mth.sin((float)(0.5f + (v - 0.5f) * 1.3f), (float)0.0f, (float)1.0f);
        float length = (float)Math.sqrt(x * x + y * y + z * z);
        float nx = x / length;
        float ny = y / length;
        float nz = z / length;
        consumer.vertex(pose, x, y, z).vertex(r, g, b, a).vertex(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).vertex(normalMatrix, nx, ny, nz).endVertex();
    }
}

