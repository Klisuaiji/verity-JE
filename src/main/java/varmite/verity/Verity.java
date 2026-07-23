/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.neoforged.neoforge.NeoForge
 *  net.neoforged.neoforge.event.server.ServerStartingEvent
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.ModLoadingContext
 *  net.neoforged.fml.common.Mod
 *  net.neoforged.fml.config.IConfigSpec
 *  net.neoforged.fml.config.ModConfig$Type
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext
 *  org.slf4j.Logger
 *  varmite.verity.Verity
 *  varmite.verity.VerityConfig
 *  varmite.verity.block.ModBlocks
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.event.ModBusCommonSetup
 *  varmite.verity.event.ModEvents
 *  varmite.verity.item.ModCreativeModeTabs
 *  varmite.verity.item.ModItems
 *  varmite.verity.network.ModMessages
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.sounds.ModSounds
 *  varmite.verity.triggers.ModTriggers
 */
package varmite.verity;

import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.ModContainer;
import org.slf4j.Logger;
import varmite.verity.VerityConfig;
import varmite.verity.block.ModBlocks;
import varmite.verity.entity.ModEntities;
import varmite.verity.event.ModBusCommonSetup;
import varmite.verity.event.ModEvents;
import varmite.verity.gui.PlayerKarmaProvider;
import varmite.verity.item.ModCreativeModeTabs;
import varmite.verity.item.ModItems;
import varmite.verity.sounds.ModSounds;
import varmite.verity.triggers.ModTriggers;

@Mod(value="verity")
public class Verity {
    public static final String MOD_ID = "verity";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Verity(IEventBus modEventBus, ModContainer modContainer) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, (IConfigSpec)VerityConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec)VerityConfig.SPEC);
        PlayerKarmaProvider.register(modEventBus);
        modEventBus.addListener(arg_0 -> this.commonSetup(arg_0));
        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModTriggers.UNBOX_VERITY_TRIGGER.toString();
        NeoForge.EVENT_BUS.register(ModEvents.class);
        modEventBus.addListener(ModBusCommonSetup::registerAttributes);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hello! I'm Verity.");
    }
}

