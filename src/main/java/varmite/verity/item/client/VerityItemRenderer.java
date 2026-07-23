/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  varmite.verity.VerityConfig
 *  varmite.verity.entity.client.SphereMesh
 *  varmite.verity.item.VerityVariants
 *  varmite.verity.item.client.VerityItemRenderer
 */
package varmite.verity.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import varmite.verity.VerityConfig;
import varmite.verity.entity.client.SphereMesh;
import varmite.verity.item.VerityVariants;

public class VerityItemRenderer
extends BlockEntityWithoutLevelRenderer {
    public VerityItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public void onResourceManagerReload(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        int b;
        int g;
        int r;
        ResourceLocation texture = VerityVariants.entityTexture((String)VerityVariants.fromStack((ItemStack)stack));
        int hueValue = (Integer)VerityConfig.COLOR.get();
        int a = 255;
        if (hueValue == 0) {
            r = 255;
            g = 255;
            b = 255;
        } else {
            int rgb = Color.HSBtoRGB((float)hueValue / 360.0f, 1.0f, 1.0f);
            r = rgb >> 16 & 0xFF;
            g = rgb >> 8 & 0xFF;
            b = rgb & 0xFF;
        }
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            poseStack.translate(0.0, 0.25, 0.0);
            poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        }
        SphereMesh.render((PoseStack)poseStack, (MultiBufferSource)bufferSource, (ResourceLocation)texture, (float)0.5f, (int)16, (int)16, (int)packedLight, (int)r, (int)g, (int)b, (int)a);
        poseStack.popPose();
    }
}

