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
 */
package varmite.verity.environment.mixins;

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

    @Inject(method={"init()V"}, at={@At(value="RETURN")})
    private void addVarmiteButton(CallbackInfo ci) {
        AbstractWidget widget;
        int varmiteX = this.f_96543_ / 2 - 100;
        int varmiteY = this.f_96544_ / 4 + 48 + 72;
        int varmiteWidth = 200;
        boolean foundMods = false;
        for (GuiEventListener listener : this.m_6702_()) {
            if (!(listener instanceof AbstractWidget)) continue;
            widget = (AbstractWidget)listener;
            String widgetText = widget.m_6035_().getString();
            if (widgetText.equalsIgnoreCase("multiplayer")) {
                varmiteX = widget.m_252754_();
                varmiteWidth = widget.m_5711_();
            }
            if (!widgetText.equalsIgnoreCase("mods")) continue;
            varmiteY = widget.m_252907_() + widget.m_93694_() + 4;
            foundMods = true;
        }
        if (foundMods) {
            for (GuiEventListener listener : this.m_6702_()) {
                if (!(listener instanceof AbstractWidget) || (widget = (AbstractWidget)listener).m_252907_() < varmiteY) continue;
                widget.m_253211_(widget.m_252907_() + 24);
            }
        }
        this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237113_((String)"Mod by Varmite").m_130940_(ChatFormatting.BOLD), button -> Util.m_137581_().m_137646_("https://www.youtube.com/@varmite")).m_252987_(varmiteX, varmiteY, varmiteWidth, 20).m_253136_());
    }
}

