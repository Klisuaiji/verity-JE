/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.resources.ResourceLocation
 */
package varmite.verity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import varmite.verity.entity.client.SphereMesh;

public final class SphereRenderHelper {
    private SphereRenderHelper() {
    }

    public static void renderEntityBillboard(PoseStack poseStack, MultiBufferSource bufferSource, ResourceLocation texture, int packedLight, double entityX, double entityY, double entityZ, double viewerX, double viewerY, double viewerZ, float stretchY, float stretchXZ, int r, int g, int b, int a, float rollAngle, float bodyYaw) {
        poseStack.m_85836_();
        poseStack.m_85837_(0.0, 0.5, 0.0);
        double dx = viewerX - entityX;
        double dy = viewerY - entityY;
        double dz = viewerZ - entityZ;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float finalYaw = (float)Math.toDegrees(Math.atan2(dx, dz));
        float finalPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));
        poseStack.m_252781_(Axis.f_252436_.m_252977_(finalYaw));
        poseStack.m_252781_(Axis.f_252529_.m_252977_(finalPitch));
        poseStack.m_252781_(Axis.f_252436_.m_252977_(90.0f));
        poseStack.m_85841_(0.5f * stretchXZ, 0.5f * stretchY, 0.5f * stretchXZ);
        poseStack.m_85837_(0.0, -0.25, 0.0);
        if (rollAngle != 0.0f) {
            poseStack.m_252781_(Axis.f_252436_.m_252977_(-90.0f));
            poseStack.m_252781_(Axis.f_252529_.m_252977_(-finalPitch));
            poseStack.m_252781_(Axis.f_252436_.m_252977_(-finalYaw));
            poseStack.m_252781_(Axis.f_252436_.m_252977_(-bodyYaw));
            poseStack.m_252781_(Axis.f_252529_.m_252977_(rollAngle));
            poseStack.m_252781_(Axis.f_252436_.m_252977_(bodyYaw));
            poseStack.m_252781_(Axis.f_252436_.m_252977_(finalYaw));
            poseStack.m_252781_(Axis.f_252529_.m_252977_(finalPitch));
            poseStack.m_252781_(Axis.f_252436_.m_252977_(90.0f));
        }
        SphereMesh.render(poseStack, bufferSource, texture, 0.5f, 16, 16, packedLight, r, g, b, a);
        poseStack.m_85849_();
    }
}

