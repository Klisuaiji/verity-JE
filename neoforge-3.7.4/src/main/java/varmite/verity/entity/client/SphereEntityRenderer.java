/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package varmite.verity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import varmite.verity.entity.client.SphereRenderHelper;
import varmite.verity.entity.client.VerityAnimation;
import varmite.verity.entity.custom.VerityEntity;

public class SphereEntityRenderer
extends EntityRenderer<VerityEntity> {
    public SphereEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    protected float getShadowRadius(VerityEntity entity) {
        return 0.3f;
    }

    public ResourceLocation getTextureLocation(VerityEntity entity) {
        return entity.getTextureRL();
    }

    public void render(VerityEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        boolean isAnimating;
        int startTick = (Integer)entity.getEntityData().get(VerityEntity.BOUNCE_START_TICK);
        if (entity.tickCount < 3 && entity.clientAnimationTicks < 0 && startTick > 0) {
            return;
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        LocalPlayer player = Minecraft.getInstance().player;
        float stretchY = 1.0f;
        float stretchXZ = 1.0f;
        double visualYOffset = 0.0;
        boolean isTalking = entity.clientIsTalking || entity.clientIntroTicks > 0;
        boolean bl = isAnimating = entity.clientAnimationTicks >= 0;
        if (isAnimating) {
            float exactTick = (float)(50 - entity.clientAnimationTicks) + partialTick;
            stretchY = VerityAnimation.getScaleY(exactTick);
            stretchXZ = VerityAnimation.getScaleXZ(exactTick);
            visualYOffset = VerityAnimation.getYOffset(exactTick);
        } else if (isTalking && entity.clientIntroDelay <= 0) {
            float talkTime = (float)entity.tickCount + partialTick;
            stretchY = 1.0f + Mth.sin((float)(talkTime * 0.6f)) * 0.12f;
            stretchXZ = 1.0f + (1.0f - stretchY) * 0.5f;
        }
        double entityX = Mth.lerp((double)partialTick, (double)entity.xo, (double)entity.getX());
        double entityY = Mth.lerp((double)partialTick, (double)entity.yo, (double)entity.getY()) + (double)entity.getBbHeight() * 0.5 + visualYOffset;
        double entityZ = Mth.lerp((double)partialTick, (double)entity.zo, (double)entity.getZ());
        double viewerX = entityX;
        double viewerY = entityY + 1.0;
        double viewerZ = entityZ;
        if (player != null) {
            viewerX = Mth.lerp((double)partialTick, (double)player.xo, (double)player.getX());
            viewerY = Mth.lerp((double)partialTick, (double)player.yo, (double)player.getY()) + (double)player.getEyeHeight();
            viewerZ = Mth.lerp((double)partialTick, (double)player.zo, (double)player.getZ());
        }
        poseStack.pushPose();
        poseStack.translate(0.0, visualYOffset, 0.0);
        SphereRenderHelper.renderEntityBillboard(poseStack, bufferSource, this.getTextureLocation(entity), packedLight, entityX, entityY, entityZ, viewerX, viewerY, viewerZ, stretchY, stretchXZ);
        poseStack.popPose();
    }
}

