/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.world.effect.MobEffects
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  varmite.verity.VerityConfig
 *  varmite.verity.mixin.LightTextureMixin
 */
package varmite.verity.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import varmite.verity.VerityConfig;

@Mixin(value={LightTexture.class})
public class LightTextureMixin {
    @Shadow
    private DynamicTexture lightTexture;

    @Inject(method={"updateLightTexture"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/texture/DynamicTexture;upload()V")})
    private void verity_trueDarkness(float partialTicks, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.makeBrain(MobEffects.NIGHT_VISION)) {
            return;
        }
        if (!((Boolean)VerityConfig.TRUE_DARKNESS.get()).booleanValue() && player != null) {
            return;
        }
        NativeImage image = this.lightTexture.getPixels();
        if (image == null) {
            return;
        }
        int ambient = image.read(0, 0);
        int aR = ambient & 0xFF;
        int aG = ambient >> 8 & 0xFF;
        int aB = ambient >> 16 & 0xFF;
        int alpha = ambient >> 24 & 0xFF;
        int maxSkyColor = image.read(0, 15);
        int sR = maxSkyColor & 0xFF;
        int sG = maxSkyColor >> 8 & 0xFF;
        int sB = maxSkyColor >> 16 & 0xFF;
        int maxSky = Math.max(sR, Math.max(sG, sB));
        float skyFactor = ((float)maxSky - 120.0f) / 50.0f;
        skyFactor = Math.max(0.0f, Math.min(1.0f, skyFactor));
        for (int x = 0; x < 16; ++x) {
            int torchColor = image.read(x, 0);
            int tdR = Math.max(0, (torchColor & 0xFF) - aR);
            int tdG = Math.max(0, (torchColor >> 8 & 0xFF) - aG);
            int tdB = Math.max(0, (torchColor >> 16 & 0xFF) - aB);
            for (int y = 0; y < 16; ++y) {
                int orig = image.read(x, y);
                int vR = orig & 0xFF;
                int vG = orig >> 8 & 0xFF;
                int vB = orig >> 16 & 0xFF;
                vR = Math.max(0, vR - aR);
                vG = Math.max(0, vG - aG);
                vB = Math.max(0, vB - aB);
                int fR = (int)((float)tdR * (1.0f - skyFactor) + (float)vR * skyFactor);
                int fG = (int)((float)tdG * (1.0f - skyFactor) + (float)vG * skyFactor);
                int fB = (int)((float)tdB * (1.0f - skyFactor) + (float)vB * skyFactor);
                int newColor = alpha << 24 | fB << 16 | fG << 8 | fR;
                image.read(x, y, newColor);
            }
        }
    }
}

