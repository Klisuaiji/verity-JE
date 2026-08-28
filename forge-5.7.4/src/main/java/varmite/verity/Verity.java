/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.server.ServerStartingEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.ModLoadingContext
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.config.IConfigSpec
 *  net.minecraftforge.fml.config.ModConfig$Type
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  org.slf4j.Logger
 */
package varmite.verity;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import varmite.verity.VerityConfig;
import varmite.verity.block.ModBlocks;
import varmite.verity.entity.ModEntities;
import varmite.verity.event.ModBusCommonSetup;
import varmite.verity.event.ModEvents;
import varmite.verity.item.ModCreativeModeTabs;
import varmite.verity.item.ModItems;
import varmite.verity.network.ModMessages;
import varmite.verity.network.ModNetwork;
import varmite.verity.sounds.ModSounds;
import varmite.verity.triggers.ModTriggers;

@Mod(value="verity")
public class Verity {
    public static final String MOD_ID = "verity";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Verity() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, (IConfigSpec)VerityConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec)VerityConfig.SPEC);
        ModNetwork.register();
        modEventBus.addListener(this::commonSetup);
        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);
        ((Object)((Object)ModTriggers.UNBOX_VERITY_TRIGGER)).toString();
        MinecraftForge.EVENT_BUS.register(ModEvents.class);
        modEventBus.addListener(ModBusCommonSetup::registerAttributes);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> ModMessages.register());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hello! I'm Verity.");
    }
}

