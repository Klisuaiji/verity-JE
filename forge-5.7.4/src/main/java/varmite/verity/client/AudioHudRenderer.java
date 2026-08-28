/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.RenderGuiOverlayEvent$Post
 *  net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package varmite.verity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import varmite.verity.VerityConfig;
import varmite.verity.client.KeybindHandler;

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
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91066_.f_92062_ || mc.f_91074_ == null) {
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
        int shadowColor = AudioHudRenderer.applyAlpha(0x66000000, alphaMultiplier);
        int bgOutlineColor = AudioHudRenderer.applyAlpha(-1728053248, alphaMultiplier);
        int bgTrackColor = AudioHudRenderer.applyAlpha(-14540254, alphaMultiplier);
        int activeFillColor = AudioHudRenderer.applyAlpha(-16711732, alphaMultiplier);
        graphics.m_280509_(x, y, x + barWidth + 2, y + barHeight + 2, shadowColor);
        graphics.m_280509_(x - 1, y - 1, x + barWidth + 1, y + barHeight + 1, bgOutlineColor);
        graphics.m_280509_(x, y, x + barWidth, y + barHeight, bgTrackColor);
        if (filledWidth > 0) {
            graphics.m_280509_(x, y, x + filledWidth, y + barHeight, activeFillColor);
        }
        String statusText = "\u25cf RECORDING";
        int textWidth = mc.f_91062_.m_92895_(statusText);
        int textX = (screenWidth - textWidth) / 2;
        int textY = y - 10;
        int textShadowColor = AudioHudRenderer.applyAlpha(-1442840576, alphaMultiplier);
        int textMainColor = AudioHudRenderer.applyAlpha(-52429, alphaMultiplier);
        graphics.m_280056_(mc.f_91062_, statusText, textX + 1, textY + 1, textShadowColor, false);
        graphics.m_280056_(mc.f_91062_, statusText, textX, textY, textMainColor, false);
    }

    private static int applyAlpha(int color, float alphaMultiplier) {
        int originalAlpha = color >> 24 & 0xFF;
        int newAlpha = (int)((float)originalAlpha * alphaMultiplier);
        newAlpha = Math.max(0, Math.min(255, newAlpha));
        return newAlpha << 24 | color & 0xFFFFFF;
    }
}

