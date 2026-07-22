/*
 * Rewritten for NeoForge 1.21.1 GUI Layers API.
 */
package varmite.verity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.DeltaTracker;
import varmite.verity.VerityConfig;
import varmite.verity.client.KeybindHandler;

public class AudioHudRenderer {
    private static boolean wasRecording = false;
    private static long fadeStartTime = 0L;
    private static final long FADE_DURATION = 500L;

    public static final LayeredDraw.Layer LAYER = (graphics, partialTick) -> render(graphics);

    public static void render(GuiGraphics graphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) {
            return;
        }
        if (((Boolean) VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
            return;
        }
        boolean isRecording = KeybindHandler.isRecording();
        long currentTime = System.currentTimeMillis();
        float alphaMultiplier = 0.0f;
        if (isRecording) {
            alphaMultiplier = 1.0f;
            wasRecording = true;
        } else {
            long timeSinceStop;
            if (wasRecording) {
                fadeStartTime = currentTime;
                wasRecording = false;
            }
            if ((timeSinceStop = currentTime - fadeStartTime) < 500L) {
                alphaMultiplier = 1.0f - (float) timeSinceStop / 500.0f;
            }
        }
        if (alphaMultiplier <= 0.0f) {
            return;
        }
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int barWidth = 80;
        int barHeight = 4;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 65;
        double level = KeybindHandler.getRecorder().getAudioLevel();
        int filledWidth = (int) ((double) barWidth * level);
        int shadowColor = applyAlpha(0x66000000, alphaMultiplier);
        int bgOutlineColor = applyAlpha(-1728053248, alphaMultiplier);
        int bgTrackColor = applyAlpha(-14540254, alphaMultiplier);
        int activeFillColor = applyAlpha(-16711732, alphaMultiplier);
        graphics.fill(x, y, x + barWidth + 2, y + barHeight + 2, shadowColor);
        graphics.fill(x - 1, y - 1, x + barWidth + 1, y + barHeight + 1, bgOutlineColor);
        graphics.fill(x, y, x + barWidth, y + barHeight, bgTrackColor);
        if (filledWidth > 0) {
            graphics.fill(x, y, x + filledWidth, y + barHeight, activeFillColor);
        }
        String statusText = "\u25cf RECORDING";
        int textWidth = mc.font.width(statusText);
        int textX = (screenWidth - textWidth) / 2;
        int textY = y - 10;
        int textShadowColor = applyAlpha(-1442840576, alphaMultiplier);
        int textMainColor = applyAlpha(-52429, alphaMultiplier);
        graphics.drawString(mc.font, statusText, textX + 1, textY + 1, textShadowColor, false);
        graphics.drawString(mc.font, statusText, textX, textY, textMainColor, false);
    }

    private static int applyAlpha(int color, float alphaMultiplier) {
        int originalAlpha = color >> 24 & 0xFF;
        int newAlpha = (int) ((float) originalAlpha * alphaMultiplier);
        newAlpha = Math.max(0, Math.min(255, newAlpha));
        return newAlpha << 24 | color & 0xFFFFFF;
    }
}
