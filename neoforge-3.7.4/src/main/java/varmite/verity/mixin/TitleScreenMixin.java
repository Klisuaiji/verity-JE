/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.resources.ResourceLocation
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package varmite.verity.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TitleScreen.class})
public class TitleScreenMixin {
    @Unique
    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/gui/title_background.png");

    @Inject(method={"renderBackground"}, at={@At(value="HEAD")}, cancellable=true)
    private void drawCustomBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        guiGraphics.blit(BG, 0, 0, width, height, 0.0f, 0.0f, width, height, width, height);
        ci.cancel();
    }
}

