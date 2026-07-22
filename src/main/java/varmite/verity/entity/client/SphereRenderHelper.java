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

    public static void renderEntityBillboard(PoseStack poseStack, MultiBufferSource bufferSource, ResourceLocation texture, int packedLight, double entityX, double entityY, double entityZ, double viewerX, double viewerY, double viewerZ, float stretchY, float stretchXZ) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.5, 0.0);
        double dx = viewerX - entityX;
        double dy = viewerY - entityY;
        double dz = viewerZ - entityZ;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float finalYaw = (float)Math.toDegrees(Math.atan2(dx, dz));
        float finalPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));
        poseStack.mulPose(Axis.YP.rotationDegrees(finalYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(finalPitch));
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
        poseStack.scale(0.5f * stretchXZ, 0.5f * stretchY, 0.5f * stretchXZ);
        poseStack.translate(0.0, -0.25, 0.0);
        SphereMesh.render(poseStack, bufferSource, texture, 0.5f, 16, 16, packedLight);
        poseStack.popPose();
    }
}

