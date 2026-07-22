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
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 */
package varmite.verity.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import varmite.verity.entity.client.SphereMesh;
import varmite.verity.item.VerityVariants;

public class VerityItemRenderer
extends BlockEntityWithoutLevelRenderer {
    public VerityItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ResourceLocation texture = VerityVariants.entityTexture(VerityVariants.fromStack(stack));
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            poseStack.translate(0.0, 0.25, 0.0);
            poseStack.mulPose(Axis.YP.rotationDegrees(45.0f));
        }
        SphereMesh.render(poseStack, bufferSource, texture, 0.5f, 16, 16, packedLight);
        poseStack.popPose();
    }
}

