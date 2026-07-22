/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.config.ModConfigEvent$Reloading
 *  varmite.verity.VerityConfig
 *  varmite.verity.event.ConfigEventHandler
 */
package varmite.verity.event;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import varmite.verity.VerityConfig;

@Mod.EventBusSubscriber(modid="verity", bus=Mod.EventBusSubscriber.Bus.MOD)
public class ConfigEventHandler {
    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (Minecraft.getInstance().player == null) {
            return;
        }
        if (event.getConfig().getSpec() == VerityConfig.SPEC) {
            Minecraft.getInstance().execute(() -> Minecraft.getInstance().levelRenderer.allChanged());
            System.out.println("Your mod config was saved and reloaded!");
        }
    }
}

