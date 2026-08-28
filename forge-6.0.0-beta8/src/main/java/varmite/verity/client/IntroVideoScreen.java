/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 */
package varmite.verity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class IntroVideoScreen
extends Screen {
    private static final int TOTAL_FRAMES = 248;
    private static final int FPS = 24;
    private final Screen previousScreen;
    private long startTime = 0L;
    private boolean videoStarted = false;

    public IntroVideoScreen(Screen previousScreen) {
        super((Component)Component.m_237113_((String)"Intro Video"));
        this.previousScreen = previousScreen;
    }

    protected void m_7856_() {
        super.m_7856_();
        if (!this.videoStarted) {
            this.startTime = System.currentTimeMillis();
            ResourceLocation soundId = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"intro_video_audio");
            SoundEvent event = SoundEvent.m_262856_((ResourceLocation)soundId, (float)1.0f);
            this.f_96541_.m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119755_((SoundEvent)event, (float)1.0f, (float)1.0f));
            Minecraft mc = Minecraft.m_91087_();
            mc.f_91066_.m_246669_(SoundSource.MUSIC).m_231514_((Object)0.0);
            mc.f_91066_.m_92169_();
            this.videoStarted = true;
        }
    }

    public void m_88315_(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
        long elapsedMillis = System.currentTimeMillis() - this.startTime;
        int currentFrame = (int)(elapsedMillis * 24L / 1000L) + 1;
        if (currentFrame > 248) {
            this.skip();
            return;
        }
        String path = String.format("textures/intro/frame_%04d.png", currentFrame);
        ResourceLocation frameLoc = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)path);
        float aspect = 1.7777778f;
        int drawW = this.f_96543_;
        int drawH = (int)((float)this.f_96543_ / aspect);
        if (drawH > this.f_96544_) {
            drawH = this.f_96544_;
            drawW = (int)((float)this.f_96544_ * aspect);
        }
        int drawX = (this.f_96543_ - drawW) / 2;
        int drawY = (this.f_96544_ - drawH) / 2;
        guiGraphics.m_280163_(frameLoc, drawX, drawY, 0.0f, 0.0f, drawW, drawH, drawW, drawH);
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.skip();
            return true;
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    private void skip() {
        this.f_96541_.m_91106_().m_120386_(ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"intro_video_audio"), SoundSource.MASTER);
        this.f_96541_.m_91152_(this.previousScreen);
    }
}

