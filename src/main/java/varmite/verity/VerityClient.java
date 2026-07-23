package varmite.verity;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import varmite.verity.event.ModBusClientSetup;

@EventBusSubscriber(modid = "verity", bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class VerityClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.addListener(ModBusClientSetup::registerRenderers);
        forgeBus.addListener(ModBusClientSetup::onModifyBakingResult);
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (minecraft, previousScreen) -> VerityClient.createYACLScreen(previousScreen));
    }

    // TODO: Port YACL config screen to YACL 3.7 API
    // The old chained builder pattern from 1.20.1 does not work with YACL 3.7's
    // generic Option.Builder<T>. Each option needs to be built separately
    // with explicit type witnesses and then assembled into categories.
    public static Screen createYACLScreen(Screen previousScreen) {
        return null;
    }
}
