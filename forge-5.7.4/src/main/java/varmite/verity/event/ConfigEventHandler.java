/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.config.ModConfigEvent$Reloading
 */
package varmite.verity.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import varmite.verity.VerityConfig;

@Mod.EventBusSubscriber(modid="verity", bus=Mod.EventBusSubscriber.Bus.MOD)
public class ConfigEventHandler {
    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (Minecraft.m_91087_().f_91074_ == null) {
            return;
        }
        if (event.getConfig().getSpec() == VerityConfig.SPEC) {
            Minecraft.m_91087_().execute(() -> Minecraft.m_91087_().f_91060_.m_109818_());
            System.out.println("Your mod config was saved and reloaded!");
        }
    }
}

