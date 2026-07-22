/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.minecraft.world.item.CreativeModeTabs
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.ModContainer
 *  net.neoforged.fml.common.Mod
 *  net.neoforged.fml.config.IConfigSpec
 *  net.neoforged.fml.config.ModConfig$Type
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.fml.loading.FMLEnvironment
 *  net.neoforged.neoforge.common.NeoForge
 *  net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
 *  net.neoforged.neoforge.event.server.ServerStartingEvent
 *  org.slf4j.Logger
 */
package varmite.verity;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import varmite.verity.VerityConfig;
import varmite.verity.entity.ModEntities;
import varmite.verity.event.ModBusClientSetup;
import varmite.verity.event.ModBusCommonSetup;
import varmite.verity.event.ModEvents;
import varmite.verity.item.ModCreativeModeTabs;
import varmite.verity.item.ModItems;
import varmite.verity.sounds.ModSounds;

@Mod(value="verity")
public class Verity {
    public static final String MOD_ID = "verity";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Verity(IEventBus modEventBus, ModContainer modContainer) {
        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        NeoForge.EVENT_BUS.register(ModEvents.class);
        modContainer.registerConfig(ModConfig.Type.COMMON, (IConfigSpec)VerityConfig.SPEC);
        modEventBus.addListener(ModBusCommonSetup::registerAttributes);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ModBusClientSetup::registerRenderers);
            modEventBus.addListener(ModBusClientSetup::registerClientExtensions);
            modEventBus.addListener(ModBusClientSetup::onModifyBakingResult);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.VERITY_ITEM);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hello! Im Verity.");
    }
}

