/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.minecraft.server.MinecraftServer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.server.ServerStartingEvent
 *  net.minecraftforge.event.server.ServerStoppingEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.ModLoadingContext
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.config.IConfigSpec
 *  net.minecraftforge.fml.config.ModConfig$Type
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.core.LoggerContext
 *  org.slf4j.Logger
 */
package varmite.verity;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import varmite.verity.VerityConfig;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.llm.store.chat.ChatMemoryManager;
import varmite.verity.entity.llm.store.memory.VerityMemoryManager;
import varmite.verity.environment.block.ModBlocks;
import varmite.verity.environment.items.ModCreativeModeTabs;
import varmite.verity.environment.items.ModItems;
import varmite.verity.environment.sounds.ModSounds;
import varmite.verity.event.ModBusCommonSetup;
import varmite.verity.event.ModEvents;
import varmite.verity.network.ModMessages;
import varmite.verity.network.ModNetwork;
import varmite.verity.triggers.ModTriggers;

@Mod(value="verity")
public class Verity {
    public static final String MOD_ID = "verity";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Verity() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec)VerityConfig.SPEC);
        ModNetwork.register();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register((Object)this);
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
        event.enqueueWork(ModMessages::register);
        Verity.quietRetryLogging();
    }

    private static void quietRetryLogging() {
        if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
            return;
        }
        try {
            ClassLoader owner = Class.forName("dev.langchain4j.internal.RetryUtils").getClassLoader();
            LoggerContext context = (LoggerContext)LogManager.getContext((ClassLoader)owner, (boolean)false);
            context.getLogger("dev.langchain4j.internal.RetryUtils").setLevel(Level.ERROR);
            context.updateLoggers();
        }
        catch (Throwable t) {
            LOGGER.debug("Couldn't quiet LangChain4j retry logging", t);
        }
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        VerityMemoryManager.get().save();
        ChatMemoryManager.getGlobalStorage().save();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MinecraftServer Server = event.getServer();
        VerityMemoryManager.init(Server);
        ChatMemoryManager.init(event.getServer());
        System.out.println("Verity JE has loaded !");
    }
}

