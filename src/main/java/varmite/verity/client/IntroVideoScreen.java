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
 *  varmite.verity.client.IntroVideoScreen
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
    private static final int TOTAL_FRAMES = 532;
    private static final int FPS = 24;
    private final Screen previousScreen;
    private long startTime = 0L;
    private boolean videoStarted = false;

    public IntroVideoScreen(Screen previousScreen) {
        super((Component)Component.literal("Intro Video"));
        this.previousScreen = previousScreen;
    }

    protected void init() {
        super.init();
        if (!this.videoStarted) {
            this.startTime = System.currentTimeMillis();
            ResourceLocation soundId = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"intro_video_audio");
            SoundEvent event = SoundEvent.createVariableRangeEvent(soundId);
            this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((SoundEvent)event, (float)1.0f, (float)1.0f));
            Minecraft mc = Minecraft.getInstance();
            mc.options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(Double.valueOf(0.0));
            mc.options.save();
            this.videoStarted = true;
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        long elapsedMillis = System.currentTimeMillis() - this.startTime;
        int currentFrame = (int)(elapsedMillis * 24L / 1000L) + 1;
        if (currentFrame > TOTAL_FRAMES) {
            this.skip();
            return;
        }
        String path = String.format("textures/intro/frame_%04d.png", currentFrame);
        ResourceLocation frameLoc = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)path);
        float aspect = 1.7777778f;
        int drawW = this.width;
        int drawH = (int)((float)this.width / aspect);
        if (drawH > this.height) {
            drawH = this.height;
            drawW = (int)((float)this.height * aspect);
        }
        int drawX = (this.width - drawW) / 2;
        int drawY = (this.height - drawH) / 2;
        guiGraphics.blit(frameLoc, drawX, drawY, 0.0f, 0.0f, drawW, drawH, drawW, drawH);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.skip();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void skip() {
        this.minecraft.getSoundManager().stop(ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"intro_video_audio"), SoundSource.MASTER);
        this.minecraft.setScreen(this.previousScreen);
    }
}

