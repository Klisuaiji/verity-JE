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
 *  varmite.verity.VerityConfig
 *  varmite.verity.entity.client.SphereEntityRenderer
 *  varmite.verity.entity.client.SphereRenderHelper
 *  varmite.verity.entity.client.VerityAnimation
 *  varmite.verity.entity.custom.VerityEntity
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
import varmite.verity.VerityConfig;
import varmite.verity.entity.client.SphereRenderHelper;
import varmite.verity.entity.client.VerityAnimation;
import varmite.verity.entity.custom.VerityEntity;

public class SphereEntityRenderer
extends EntityRenderer<VerityEntity> {
    public SphereEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.3f;
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
        super.render((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        LocalPlayer player = Minecraft.getInstance().player;
        float stretchY = 1.0f;
        float stretchXZ = 1.0f;
        double visualYOffset = 0.0;
        boolean isTalking = entity.clientIsTalking || entity.clientIntroTicks > 0;
        boolean bl = isAnimating = entity.clientAnimationTicks >= 0;
        if (isAnimating) {
            float exactTick = (float)(50 - entity.clientAnimationTicks) + partialTick;
            stretchY = VerityAnimation.getScaleY((float)exactTick);
            stretchXZ = VerityAnimation.getScaleXZ((float)exactTick);
            visualYOffset = VerityAnimation.getYOffset((float)exactTick);
        } else if (isTalking && entity.clientIntroDelay <= 0) {
            float talkTime = (float)entity.tickCount + partialTick;
            stretchY = 1.0f + Mth.sin((float)(talkTime * 0.6f)) * 0.12f;
            stretchXZ = 1.0f + (1.0f - stretchY) * 0.5f;
        }
        double entityX = Mth.floor((double)partialTick, (double)entity.xo, (double)entity.getX());
        double entityY = Mth.floor((double)partialTick, (double)entity.yo, (double)entity.getY()) + (double)entity.getBbHeight() * 0.5 + visualYOffset;
        double entityZ = Mth.floor((double)partialTick, (double)entity.zo, (double)entity.getZ());
        double viewerX = entityX;
        double viewerY = entityY + 1.0;
        double viewerZ = entityZ;
        if (player != null) {
            viewerX = Mth.floor((double)partialTick, (double)player.xo, (double)player.getX());
            viewerY = Mth.floor((double)partialTick, (double)player.yo, (double)player.getY()) + (double)player.getEyeHeight();
            viewerZ = Mth.floor((double)partialTick, (double)player.zo, (double)player.getZ());
        }
        int hueValue = (Integer)VerityConfig.COLOR.get();
        int r = 255;
        int g = 255;
        int b = 255;
        int a = 255;
        if (hueValue != 0) {
            int rgb = Mth.frac((float)((float)hueValue / 360.0f), (float)1.0f, (float)1.0f);
            r = rgb >> 16 & 0xFF;
            g = rgb >> 8 & 0xFF;
            b = rgb & 0xFF;
        }
        float rollAngle = Mth.invSqrt((float)partialTick, (float)entity.clientRollAngleO, (float)entity.clientRollAngle);
        float bodyYaw = Mth.invSqrt((float)partialTick, (float)entity.yBodyRotO, (float)entity.yBodyRot);
        poseStack.translate();
        poseStack.translate(0.0, visualYOffset, 0.0);
        SphereRenderHelper.renderEntityBillboard((PoseStack)poseStack, (MultiBufferSource)bufferSource, (ResourceLocation)this.getTextureLocation(entity), (int)packedLight, (double)entityX, (double)entityY, (double)entityZ, (double)viewerX, (double)viewerY, (double)viewerZ, (float)stretchY, (float)stretchXZ, (int)r, (int)g, (int)b, (int)a, (float)rollAngle, (float)bodyYaw);
        poseStack.scale();
    }
}

