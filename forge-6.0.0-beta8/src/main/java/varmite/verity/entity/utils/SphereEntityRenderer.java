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
package varmite.verity.entity.utils;

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
import varmite.verity.entity.utils.SphereRenderHelper;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.entity.verity.rendering.VerityAnimation;

public class SphereEntityRenderer
extends EntityRenderer<VerityEntity> {
    public SphereEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.f_114477_ = 0.3f;
    }

    public ResourceLocation getTextureLocation(VerityEntity entity) {
        return entity.getTextureRL();
    }

    public void render(VerityEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        boolean isAnimating;
        int startTick = (Integer)entity.m_20088_().m_135370_(VerityEntity.BOUNCE_START_TICK);
        if (entity.f_19797_ < 3 && entity.clientAnimationTicks < 0 && startTick > 0) {
            return;
        }
        super.m_7392_((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
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
            float talkTime = (float)entity.f_19797_ + partialTick;
            stretchY = 1.0f + Mth.m_14031_((float)(talkTime * 0.6f)) * 0.12f;
            stretchXZ = 1.0f + (1.0f - stretchY) * 0.5f;
        }
        double entityX = Mth.m_14139_((double)partialTick, (double)entity.f_19854_, (double)entity.m_20185_());
        double entityY = Mth.m_14139_((double)partialTick, (double)entity.f_19855_, (double)entity.m_20186_()) + (double)entity.m_20206_() * 0.5 + visualYOffset;
        double entityZ = Mth.m_14139_((double)partialTick, (double)entity.f_19856_, (double)entity.m_20189_());
        double viewerX = entityX;
        double viewerY = entityY + 1.0;
        double viewerZ = entityZ;
        if (player != null) {
            viewerX = Mth.m_14139_((double)partialTick, (double)player.f_19854_, (double)player.m_20185_());
            viewerY = Mth.m_14139_((double)partialTick, (double)player.f_19855_, (double)player.m_20186_()) + (double)player.m_20192_();
            viewerZ = Mth.m_14139_((double)partialTick, (double)player.f_19856_, (double)player.m_20189_());
        }
        int hueValue = (Integer)VerityConfig.COLOR.get();
        int r = 255;
        int g = 255;
        int b = 255;
        int a = 255;
        if (hueValue != 0) {
            int rgb = Mth.m_14169_((float)((float)hueValue / 360.0f), (float)1.0f, (float)1.0f);
            r = rgb >> 16 & 0xFF;
            g = rgb >> 8 & 0xFF;
            b = rgb & 0xFF;
        }
        float rollAngle = Mth.m_14179_((float)partialTick, (float)entity.clientRollAngleO, (float)entity.clientRollAngle);
        float bodyYaw = Mth.m_14179_((float)partialTick, (float)entity.f_20884_, (float)entity.f_20883_);
        poseStack.m_85836_();
        poseStack.m_85837_(0.0, visualYOffset, 0.0);
        SphereRenderHelper.renderEntityBillboard(poseStack, bufferSource, this.getTextureLocation(entity), packedLight, entityX, entityY, entityZ, viewerX, viewerY, viewerZ, stretchY, stretchXZ, r, g, b, a, rollAngle, bodyYaw);
        poseStack.m_85849_();
    }
}

