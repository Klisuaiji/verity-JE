/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.RenderGuiOverlayEvent$Post
 *  net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  varmite.verity.VerityConfig
 *  varmite.verity.client.AudioHudRenderer
 *  varmite.verity.client.KeybindHandler
 */
package varmite.verity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.VerityConfig;
import varmite.verity.client.KeybindHandler;

/*
 * Exception performing whole class analysis ignored.
 */
@Mod.EventBusSubscriber(modid="verity", value={Dist.CLIENT}, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class AudioHudRenderer {
    private static boolean wasRecording = false;
    private static long fadeStartTime = 0L;
    private static final long FADE_DURATION = 500L;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals((Object)VanillaGuiOverlay.HOTBAR.id())) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) {
            return;
        }
        if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
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
                alphaMultiplier = 1.0f - (float)timeSinceStop / 500.0f;
            }
        }
        if (alphaMultiplier <= 0.0f) {
            return;
        }
        GuiGraphics graphics = event.getGuiGraphics();
        int screenWidth = event.getWindow().m_85445_();
        int screenHeight = event.getWindow().m_85446_();
        int barWidth = 80;
        int barHeight = 4;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 65;
        double level = KeybindHandler.getRecorder().getAudioLevel();
        int filledWidth = (int)((double)barWidth * level);
        int shadowColor = AudioHudRenderer.applyAlpha((int)0x66000000, (float)alphaMultiplier);
        int bgOutlineColor = AudioHudRenderer.applyAlpha((int)-1728053248, (float)alphaMultiplier);
        int bgTrackColor = AudioHudRenderer.applyAlpha((int)-14540254, (float)alphaMultiplier);
        int activeFillColor = AudioHudRenderer.applyAlpha((int)-16711732, (float)alphaMultiplier);
        graphics.drawManaged(x, y, x + barWidth + 2, y + barHeight + 2, shadowColor);
        graphics.drawManaged(x - 1, y - 1, x + barWidth + 1, y + barHeight + 1, bgOutlineColor);
        graphics.drawManaged(x, y, x + barWidth, y + barHeight, bgTrackColor);
        if (filledWidth > 0) {
            graphics.drawManaged(x, y, x + filledWidth, y + barHeight, activeFillColor);
        }
        String statusText = "\u25cf RECORDING";
        int textWidth = mc.font.m_92895_(statusText);
        int textX = (screenWidth - textWidth) / 2;
        int textY = y - 10;
        int textShadowColor = AudioHudRenderer.applyAlpha((int)-1442840576, (float)alphaMultiplier);
        int textMainColor = AudioHudRenderer.applyAlpha((int)-52429, (float)alphaMultiplier);
        graphics.drawManaged(mc.font, statusText, textX + 1, textY + 1, textShadowColor, false);
        graphics.drawManaged(mc.font, statusText, textX, textY, textMainColor, false);
    }

    private static int applyAlpha(int color, float alphaMultiplier) {
        int originalAlpha = color >> 24 & 0xFF;
        int newAlpha = (int)((float)originalAlpha * alphaMultiplier);
        newAlpha = Math.max(0, Math.min(255, newAlpha));
        return newAlpha << 24 | color & 0xFFFFFF;
    }
}

