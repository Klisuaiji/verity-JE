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
 */
package varmite.verity.environment.items.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import varmite.verity.VerityConfig;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.utils.SphereMesh;
import varmite.verity.environment.items.VerityVariants;

public class VerityItemRenderer
extends BlockEntityWithoutLevelRenderer {
    public VerityItemRenderer() {
        super(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_108829_(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        String variant = VerityVariants.fromStack(stack);
        ResourceLocation texture = VerityVariants.entityTexture(variant);
        if (VerityState.isClientTalking) {
            String talkingFile;
            switch (variant) {
                case "happy": {
                    String string = "happy_talking";
                    break;
                }
                case "crazy": {
                    String string = "crazy_talking";
                    break;
                }
                case "evil": {
                    String string = "evil_talking";
                    break;
                }
                case "serious_1": 
                case "serious_2": 
                case "serious_3": {
                    String string = "serious_talking";
                    break;
                }
                default: {
                    String string = talkingFile = null;
                }
            }
            if (talkingFile != null) {
                texture = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)("textures/entity/" + talkingFile + ".png"));
            }
        }
        float stretchY = 1.0f;
        float stretchXZ = 1.0f;
        if (VerityState.isClientTalking) {
            float talkTime = (float)(Minecraft.m_91087_().f_91074_ != null ? Minecraft.m_91087_().f_91074_.f_19797_ : 0) + Minecraft.m_91087_().m_91296_();
            stretchY = 1.0f + Mth.m_14031_((float)(talkTime * 0.6f)) * 0.12f;
            stretchXZ = 1.0f + (1.0f - stretchY) * 0.5f;
        }
        poseStack.m_85836_();
        try {
            int b;
            int g;
            int r;
            poseStack.m_85837_(0.5, 0.5, 0.5);
            poseStack.m_85841_(0.5f * stretchXZ, 0.5f * stretchY, 0.5f * stretchXZ);
            int hueValue = (Integer)VerityConfig.COLOR.get();
            int a = 255;
            if (hueValue == 0) {
                r = 255;
                g = 255;
                b = 255;
            } else {
                int rgb = Mth.m_14169_((float)((float)hueValue / 360.0f), (float)1.0f, (float)1.0f);
                r = rgb >> 16 & 0xFF;
                g = rgb >> 8 & 0xFF;
                b = rgb & 0xFF;
            }
            poseStack.m_85836_();
            try {
                if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                    poseStack.m_85837_(0.0, 0.25, 0.0);
                    poseStack.m_252781_(Axis.f_252436_.m_252977_(45.0f));
                }
                SphereMesh.render(poseStack, bufferSource, texture, 0.5f, 16, 16, packedLight, r, g, b, a);
            }
            finally {
                poseStack.m_85849_();
            }
        }
        finally {
            poseStack.m_85849_();
        }
    }
}

