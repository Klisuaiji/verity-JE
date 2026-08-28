/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.ViewportEvent$ComputeFogColor
 *  net.minecraftforge.client.event.ViewportEvent$RenderFog
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package varmite.verity.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="verity", value={Dist.CLIENT})
public class VerityVisuals {
    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        ClientLevel level = Minecraft.m_91087_().f_91073_;
        if (level != null && level.m_46472_() == Level.f_46428_) {
            float partialTick = (float)event.getPartialTick();
            float angle = level.m_46942_(partialTick);
            float brightness = Mth.m_14089_((float)(angle * ((float)Math.PI * 2))) * 2.0f + 0.5f;
            brightness = Mth.m_14036_((float)brightness, (float)0.0f, (float)1.0f);
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
        if (Minecraft.m_91087_().f_91073_ != null && Minecraft.m_91087_().f_91073_.m_46472_() == Level.f_46428_) {
            float farDistance = event.getFarPlaneDistance();
            event.setNearPlaneDistance(farDistance * 0.1f);
            event.setFarPlaneDistance(farDistance * 0.75f);
            event.setCanceled(true);
        }
    }
}

