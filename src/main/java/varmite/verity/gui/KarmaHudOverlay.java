/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.neoforged.neoforge.client.gui.overlay.IGuiOverlay
 *  varmite.verity.VerityConfig
 *  varmite.verity.gui.KarmaHudOverlay
 *  varmite.verity.network.ClientKarmaData
 */
package varmite.verity.gui;

import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import varmite.verity.VerityConfig;
import varmite.verity.network.ClientKarmaData;

public class KarmaHudOverlay {
    private static final ResourceLocation EMPTY_BAR = new ResourceLocation("verity", "textures/karma_bar/empty.png");
    private static final ResourceLocation FULL_BAR = new ResourceLocation("verity", "textures/karma_bar/full.png");
    private static final ResourceLocation FACE_HAPPY = new ResourceLocation("verity", "textures/karma_bar/happy.png");
    private static final ResourceLocation FACE_NEUTRAL = new ResourceLocation("verity", "textures/karma_bar/neutral.png");
    private static final ResourceLocation FACE_ANGRY = new ResourceLocation("verity", "textures/karma_bar/angry.png");
    public static final IGuiOverlay HUD_KARMA = (gui, guiGraphics, partialTick, width, height) -> {
        int karma = ClientKarmaData.getPlayerKarma();
        int maxKarma = 20;
        if (((Boolean)VerityConfig.SHOW_VERITYS_KARMA.get()).booleanValue() && !((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            int texWidth = 182;
            int texHeight = 5;
            int faceSize = 16;
            int paddingRight = 15;
            int gap = 2;
            int totalVisualHeight = texWidth + faceSize + gap;
            int startY = (height - totalVisualHeight) / 2;
            int drawX = width - paddingRight - texHeight;
            int faceX = drawX + texHeight / 2 - faceSize / 2;
            int faceY = startY;
            int barBottomY = startY + faceSize + gap + texWidth;
            if (karma < 7) {
                guiGraphics.drawManaged(FACE_ANGRY, faceX, faceY, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            } else if (karma < 14) {
                guiGraphics.drawManaged(FACE_NEUTRAL, faceX, faceY, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            } else {
                guiGraphics.drawManaged(FACE_HAPPY, faceX, faceY, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            }
            int fillWidth = (int)((float)karma / (float)maxKarma * (float)texWidth);
            guiGraphics.pose().translate();
            guiGraphics.pose().translate((float)drawX, (float)barBottomY, 0.0f);
            guiGraphics.pose().translate(Axis.ZP.rotationDegrees(-90.0f));
            guiGraphics.drawManaged(EMPTY_BAR, 0, 0, 0.0f, 0.0f, texWidth, texHeight, texWidth, texHeight);
            if (fillWidth > 0) {
                guiGraphics.drawManaged(FULL_BAR, 0, 0, 0.0f, 0.0f, fillWidth, texHeight, texWidth, texHeight);
            }
            guiGraphics.pose().scale();
        }
    };
}

