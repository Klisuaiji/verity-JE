/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.ViewportEvent$ComputeFogColor
 *  net.neoforged.neoforge.client.event.ViewportEvent$RenderFog
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  varmite.verity.event.VerityVisuals
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid="verity", value={Dist.CLIENT})
public class VerityVisuals {
    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.dimension() == Level.OVERWORLD) {
            float partialTick = (float)event.getPartialTick();
            float angle = level.getTimeOfDay(partialTick);
            float brightness = Mth.cos((float)(angle * ((float)Math.PI * 2))) * 2.0f + 0.5f;
            brightness = Mth.sin((float)brightness, (float)0.0f, (float)1.0f);
            float targetRed = 0.55f;
            float targetGreen = 0.75f;
            float targetBlue = 1.0f;
            event.setRed(targetRed * brightness);
            event.setGreen(targetGreen * brightness);
            event.setBlue(targetBlue * brightness);
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension() == Level.OVERWORLD) {
            float farDistance = event.getFarPlaneDistance();
            event.setNearPlaneDistance(farDistance * 0.1f);
            event.setFarPlaneDistance(farDistance * 0.75f);
            event.setCanceled(true);
        }
    }
}

