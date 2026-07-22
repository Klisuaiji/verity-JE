/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.Util
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.network.chat.Component
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  varmite.verity.mixin.TitleScreenMixin
 */
package varmite.verity.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TitleScreen.class})
public abstract class TitleScreenMixin
extends Screen {
    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method={"init"}, at={@At(value="RETURN")})
    private void addVarmiteButton(CallbackInfo ci) {
        AbstractWidget widget;
        int varmiteX = this.width / 2 - 100;
        int varmiteY = this.height / 4 + 48 + 72;
        int varmiteWidth = 200;
        boolean foundMods = false;
        for (GuiEventListener listener : this.children()) {
            if (!(listener instanceof AbstractWidget)) continue;
            widget = (AbstractWidget)listener;
            String widgetText = widget.getMessage().getString();
            if (widgetText.equalsIgnoreCase("multiplayer")) {
                varmiteX = widget.getX();
                varmiteWidth = widget.getWidth();
            }
            if (!widgetText.equalsIgnoreCase("mods")) continue;
            varmiteY = widget.getY() + widget.getHeight() + 4;
            foundMods = true;
        }
        if (foundMods) {
            for (GuiEventListener listener : this.children()) {
                if (!(listener instanceof AbstractWidget) || (widget = (AbstractWidget)listener).getY() < varmiteY) continue;
                widget.setY(widget.getY() + 24);
            }
        }
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.getContents((String)"Mod by Varmite").withStyle(ChatFormatting.BOLD), button -> Util.getPlatform().openUri("https://www.youtube.com/@varmite")).pos(varmiteX, varmiteY, varmiteWidth, 20).pos());
    }
}

