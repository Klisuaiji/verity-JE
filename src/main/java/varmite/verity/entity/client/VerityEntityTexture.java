/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  varmite.verity.entity.client.VerityEntityTexture
 */
package varmite.verity.entity.client;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

public class VerityEntityTexture {
    private static final ResourceLocation ENTITY_ID = new ResourceLocation("verity", "dynamic/entity");
    private static NativeImage baseImage;
    private static DynamicTexture texture;
    private static int lastHue;
    private static ResourceLocation currentTexture;

    public static void init() {
        texture = new DynamicTexture(new NativeImage(1, 1, true));
        Minecraft.getInstance().getTextureManager().bindForSetup(ENTITY_ID, (AbstractTexture)texture);
    }

    public static void setBaseTexture(ResourceLocation textureLocation) {
        if (textureLocation.equals((Object)currentTexture)) {
            return;
        }
        currentTexture = textureLocation;
        try (InputStream stream = ((Resource)Minecraft.getInstance().getResourceManager().getResource(textureLocation).orElseThrow()).open();){
            baseImage = NativeImage.read((InputStream)stream);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load " + String.valueOf(textureLocation), e);
        }
        if (texture == null || texture.getPixels().read() != baseImage.read() || texture.getPixels().getHeight() != baseImage.getHeight()) {
            texture = new DynamicTexture(new NativeImage(baseImage.read(), baseImage.getHeight(), true));
            Minecraft.getInstance().getTextureManager().bindForSetup(ENTITY_ID, (AbstractTexture)texture);
        }
        lastHue = -1;
    }

    public static void applyHue(int hue) {
        if (baseImage == null) {
            return;
        }
        if (hue == lastHue) {
            return;
        }
        lastHue = hue;
        NativeImage out = texture.getPixels();
        float[] hsb = new float[3];
        for (int y = 0; y < baseImage.getHeight(); ++y) {
            for (int x = 0; x < baseImage.read(); ++x) {
                int abgr = baseImage.read(x, y);
                int a = abgr >> 24 & 0xFF;
                int b = abgr >> 16 & 0xFF;
                int g = abgr >> 8 & 0xFF;
                int r = abgr & 0xFF;
                Color.RGBtoHSB(r, g, b, hsb);
                if (hue == 0 || hsb[1] < 0.15f) {
                    out.read(x, y, abgr);
                    continue;
                }
                int rgb = Color.HSBtoRGB((float)hue / 360.0f, hsb[1], hsb[2]);
                int nr = rgb >> 16 & 0xFF;
                int ng = rgb >> 8 & 0xFF;
                int nb = rgb & 0xFF;
                out.read(x, y, a << 24 | nb << 16 | ng << 8 | nr);
            }
        }
        texture.upload();
    }

    public static ResourceLocation id() {
        return ENTITY_ID;
    }

    static {
        lastHue = -1;
        currentTexture = null;
    }
}

