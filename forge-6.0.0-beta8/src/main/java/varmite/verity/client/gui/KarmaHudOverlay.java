/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.client.gui.overlay.IGuiOverlay
 */
package varmite.verity.client.gui;

import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import varmite.verity.VerityConfig;
import varmite.verity.network.ClientKarmaData;

public class KarmaHudOverlay {
    private static final ResourceLocation EMPTY_BAR = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/karma_bar/empty.png");
    private static final ResourceLocation FULL_BAR = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/karma_bar/full.png");
    private static final ResourceLocation FACE_HAPPY = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/karma_bar/happy.png");
    private static final ResourceLocation FACE_NEUTRAL = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/karma_bar/neutral.png");
    private static final ResourceLocation FACE_ANGRY = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/karma_bar/angry.png");
    public static final IGuiOverlay HUD_KARMA = (gui, guiGraphics, partialTick, width, height) -> {
        int karma = ClientKarmaData.getPlayerKarma();
        int maxKarma = 20;
        if (!((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            int texWidth = 182;
            int texHeight = 5;
            int faceSize = 16;
            int paddingRight = 15;
            int gap = 2;
            int totalVisualHeight = texWidth + faceSize + gap;
            int startY = (height - totalVisualHeight) / 2;
            int drawX = width - paddingRight - texHeight;
            int faceX = drawX + texHeight / 2 - faceSize / 2;
            int barBottomY = startY + faceSize + gap + texWidth;
            if (karma < 7) {
                guiGraphics.m_280163_(FACE_ANGRY, faceX, startY, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            } else if (karma < 14) {
                guiGraphics.m_280163_(FACE_NEUTRAL, faceX, startY, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            } else {
                guiGraphics.m_280163_(FACE_HAPPY, faceX, startY, 0.0f, 0.0f, faceSize, faceSize, faceSize, faceSize);
            }
            int fillWidth = (int)((float)karma / (float)maxKarma * (float)texWidth);
            guiGraphics.m_280168_().m_85836_();
            guiGraphics.m_280168_().m_252880_((float)drawX, (float)barBottomY, 0.0f);
            guiGraphics.m_280168_().m_252781_(Axis.f_252403_.m_252977_(-90.0f));
            guiGraphics.m_280163_(EMPTY_BAR, 0, 0, 0.0f, 0.0f, texWidth, texHeight, texWidth, texHeight);
            if (fillWidth > 0) {
                guiGraphics.m_280163_(FULL_BAR, 0, 0, 0.0f, 0.0f, fillWidth, texHeight, texWidth, texHeight);
            }
            guiGraphics.m_280168_().m_85849_();
        }
    };
}

