/*
 * Rewritten for NeoForge 1.21.1 GUI Layers API.
 */
package varmite.verity.gui;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.ResourceLocation;
import varmite.verity.VerityConfig;
import varmite.verity.network.ClientKarmaData;

public class KarmaHudOverlay {
    private static final ResourceLocation EMPTY_BAR = ResourceLocation.fromNamespaceAndPath("verity", "textures/karma_bar/empty.png");
    private static final ResourceLocation FULL_BAR = ResourceLocation.fromNamespaceAndPath("verity", "textures/karma_bar/full.png");
    private static final ResourceLocation FACE_HAPPY = ResourceLocation.fromNamespaceAndPath("verity", "textures/karma_bar/happy.png");
    private static final ResourceLocation FACE_NEUTRAL = ResourceLocation.fromNamespaceAndPath("verity", "textures/karma_bar/neutral.png");
    private static final ResourceLocation FACE_ANGRY = ResourceLocation.fromNamespaceAndPath("verity", "textures/karma_bar/angry.png");

    public static final LayeredDraw.Layer HUD_KARMA = (guiGraphics, partialTick) -> {
        int karma = ClientKarmaData.getPlayerKarma();
        int maxKarma = 20;
        if (((Boolean) VerityConfig.SHOW_VERITYS_KARMA.get()).booleanValue() && !((Boolean) VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            int texWidth = 182;
            int texHeight = 5;
            int faceSize = 16;
            int paddingRight = 15;
            int gap = 2;
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            int totalVisualHeight = texWidth + faceSize + gap;
            int startY = (height - totalVisualHeight) / 2;
            int drawX = width - paddingRight - texHeight;
            int faceX = drawX + texHeight / 2 - faceSize / 2;
            int faceY = startY;
            int barBottomY = startY + faceSize + gap + texWidth;
            ResourceLocation face = karma < 7 ? FACE_ANGRY : (karma < 14 ? FACE_NEUTRAL : FACE_HAPPY);
            guiGraphics.blit(face, faceX, faceY, 0, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            int fillWidth = (int) ((float) karma / (float) maxKarma * (float) texWidth);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((double) drawX, (double) barBottomY, 0.0);
            guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-90.0f));
            guiGraphics.blit(EMPTY_BAR, 0, 0, 0, 0.0f, 0.0f, texWidth, texHeight, texWidth, texHeight);
            if (fillWidth > 0) {
                guiGraphics.blit(FULL_BAR, 0, 0, 0, 0.0f, 0.0f, fillWidth, texHeight, texWidth, texHeight);
            }
            guiGraphics.pose().popPose();
        }
    };
}
