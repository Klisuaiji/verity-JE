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
 *  varmite.verity.client.VerityPreviewTexture
 */
package varmite.verity.client;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Exception performing whole class analysis ignored.
 */
public class VerityPreviewTexture {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerityPreviewTexture.class);
    private static final ResourceLocation PREVIEW_ID = ResourceLocation.fromNamespaceAndPath("verity", "dynamic/color_preview");
    private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath("verity", "textures/entity/preview.png");
    private static NativeImage baseImage;
    private static DynamicTexture texture;
    private static int lastHue;

    public static void init() {
        Optional<Resource> resourceOpt = Minecraft.getInstance().getResourceManager().getResource(BASE_TEXTURE);
        if (resourceOpt.isPresent()) {
            try (InputStream stream = resourceOpt.get().open()) {
                baseImage = NativeImage.read((InputStream)stream);
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to load Verity preview texture", e);
            }
        } else {
            LOGGER.warn("Verity preview texture {} not found; generating a fallback color-preview image", (Object)BASE_TEXTURE);
            baseImage = createFallbackBaseImage(64, 64);
        }
        texture = new DynamicTexture(baseImage.getWidth(), baseImage.getHeight(), true);
        Minecraft.getInstance().getTextureManager().register(PREVIEW_ID, texture);
        VerityPreviewTexture.applyHue(0);
    }

    private static NativeImage createFallbackBaseImage(int width, int height) {
        NativeImage image = new NativeImage(width, height, true);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int rgb = Color.HSBtoRGB(0.58f, 0.6f, 0.9f);
                int r = rgb >> 16 & 0xFF;
                int g = rgb >> 8 & 0xFF;
                int b = rgb & 0xFF;
                image.setPixelRGBA(x, y, 0xFF << 24 | b << 16 | g << 8 | r);
            }
        }
        return image;
    }

    public static void applyHue(int hue) {
        if (hue == lastHue) {
            return;
        }
        lastHue = hue;
        NativeImage out = texture.getPixels();
        float[] hsb = new float[3];
        for (int y = 0; y < baseImage.getHeight(); ++y) {
            for (int x = 0; x < baseImage.getWidth(); ++x) {
                int abgr = baseImage.getPixelRGBA(x, y);
                int a = abgr >> 24 & 0xFF;
                int b = abgr >> 16 & 0xFF;
                int g = abgr >> 8 & 0xFF;
                int r = abgr & 0xFF;
                Color.RGBtoHSB(r, g, b, hsb);
                if (hue == 0 || hsb[1] < 0.15f) {
                    out.setPixelRGBA(x, y, abgr);
                    continue;
                }
                int rgb = Color.HSBtoRGB((float)hue / 360.0f, hsb[1], hsb[2]);
                int nr = rgb >> 16 & 0xFF;
                int ng = rgb >> 8 & 0xFF;
                int nb = rgb & 0xFF;
                out.setPixelRGBA(x, y, a << 24 | nb << 16 | ng << 8 | nr);
            }
        }
        texture.upload();
    }

    public static ResourceLocation id() {
        return PREVIEW_ID;
    }

    static {
        lastHue = -1;
    }
}

