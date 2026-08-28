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
 */
package varmite.verity.entity.verity.rendering;

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
    private static final ResourceLocation ENTITY_ID = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"dynamic/entity");
    private static NativeImage baseImage;
    private static DynamicTexture texture;
    private static int lastHue;
    private static ResourceLocation currentTexture;

    public static void init() {
        texture = new DynamicTexture(new NativeImage(1, 1, true));
        Minecraft.m_91087_().m_91097_().m_118495_(ENTITY_ID, (AbstractTexture)texture);
    }

    public static void setBaseTexture(ResourceLocation textureLocation) {
        if (textureLocation.equals((Object)currentTexture)) {
            return;
        }
        currentTexture = textureLocation;
        try (InputStream stream = ((Resource)Minecraft.m_91087_().m_91098_().m_213713_(textureLocation).orElseThrow()).m_215507_();){
            baseImage = NativeImage.m_85058_((InputStream)stream);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load " + String.valueOf(textureLocation), e);
        }
        if (texture == null || texture.m_117991_().m_84982_() != baseImage.m_84982_() || texture.m_117991_().m_85084_() != baseImage.m_85084_()) {
            texture = new DynamicTexture(new NativeImage(baseImage.m_84982_(), baseImage.m_85084_(), true));
            Minecraft.m_91087_().m_91097_().m_118495_(ENTITY_ID, (AbstractTexture)texture);
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
        NativeImage out = texture.m_117991_();
        float[] hsb = new float[3];
        for (int y = 0; y < baseImage.m_85084_(); ++y) {
            for (int x = 0; x < baseImage.m_84982_(); ++x) {
                int abgr = baseImage.m_84985_(x, y);
                int a = abgr >> 24 & 0xFF;
                int b = abgr >> 16 & 0xFF;
                int g = abgr >> 8 & 0xFF;
                int r = abgr & 0xFF;
                Color.RGBtoHSB(r, g, b, hsb);
                if (hue == 0 || hsb[1] < 0.15f) {
                    out.m_84988_(x, y, abgr);
                    continue;
                }
                int rgb = Color.HSBtoRGB((float)hue / 360.0f, hsb[1], hsb[2]);
                int nr = rgb >> 16 & 0xFF;
                int ng = rgb >> 8 & 0xFF;
                int nb = rgb & 0xFF;
                out.m_84988_(x, y, a << 24 | nb << 16 | ng << 8 | nr);
            }
        }
        texture.m_117985_();
    }

    public static ResourceLocation id() {
        return ENTITY_ID;
    }

    static {
        lastHue = -1;
        currentTexture = null;
    }
}

