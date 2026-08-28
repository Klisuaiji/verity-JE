/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  dev.isxander.yacl3.api.ConfigCategory
 *  dev.isxander.yacl3.api.Option
 *  dev.isxander.yacl3.api.OptionDescription
 *  dev.isxander.yacl3.api.YetAnotherConfigLib
 *  dev.isxander.yacl3.api.controller.EnumControllerBuilder
 *  dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
 *  dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
 *  dev.isxander.yacl3.api.controller.StringControllerBuilder
 *  dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.ForgeConfigSpec$BooleanValue
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 */
package varmite.verity;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import varmite.verity.VerityConfig;
import varmite.verity.client.VerityPreviewTexture;
import varmite.verity.event.ModBusClientSetup;
import varmite.verity.types.AiProvider;
import varmite.verity.types.KokoroVoice;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;
import varmite.verity.types.VerityVoice;

@Mod.EventBusSubscriber(modid="verity", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class VerityConfigUI {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(ModBusClientSetup::registerRenderers);
        forgeBus.addListener(ModBusClientSetup::onModifyBakingResult);
        MinecraftForge.registerConfigScreen((minecraft, previousScreen) -> VerityConfigUI.createYACLScreen(previousScreen));
    }

    public static Screen createYACLScreen(Screen previousScreen) {
        return YetAnotherConfigLib.createBuilder().title((Component)Component.m_237113_((String)"Verity Configuration")).category(ConfigCategory.createBuilder().name((Component)Component.m_237113_((String)"General")).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Can Crash")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Allow Verity to kick you from the server.")})).binding((Object)true, (Supplier)VerityConfig.CAN_CRASH, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.CAN_CRASH).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Play Video")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Play the Verity Edit on the\nstartup of the client.")})).binding((Object)true, (Supplier)VerityConfig.PLAY_VIDEO, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.PLAY_VIDEO).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Require Verity")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Require 'Verity' in every\nsentence to speak to him.")})).binding((Object)false, (Supplier)VerityConfig.REQUIRE_VERITY, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.REQUIRE_VERITY).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"True Darkness")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Toggle the extreme darkness.")})).binding((Object)true, (Supplier)VerityConfig.TRUE_DARKNESS, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.TRUE_DARKNESS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Kill Entities")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Toggles if Verity can kill entities.")})).binding((Object)true, (Supplier)VerityConfig.KILL_ENTITIES, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.KILL_ENTITIES).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Kill Wildlife")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Toggles if Verity should kill randomly wildlife.\nPigs, sheep, etc")})).binding((Object)true, (Supplier)VerityConfig.KILL_WILDLIFE, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.KILL_WILDLIFE).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Kill Villagers")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Toggles if Verity should kill villagers.")})).binding((Object)true, (Supplier)VerityConfig.KILL_VILLAGERS, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.KILL_VILLAGERS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Immersive Mode")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Disables mod specific UIs to resemble the original series.")})).binding((Object)false, (Supplier)VerityConfig.IMMERSIVE_MODE, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.IMMERSIVE_MODE).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Hostile Day Threshold")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"The amount of in-game days before\nVerity turns into a demon.")})).binding((Object)5, VerityConfig.DAY_COUNT, arg_0 -> VerityConfig.DAY_COUNT.set(arg_0)).controller(opt -> ((IntegerFieldControllerBuilder)IntegerFieldControllerBuilder.create((Option)opt).min((Number)1)).max((Number)100)).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.m_237113_((String)"AI Settings")).option(Option.createBuilder().name((Component)Component.m_237113_((String)"AI Provider")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"The provider used to power the AI.")})).binding((Object)AiProvider.OPENAI, VerityConfig.AI_PROVIDER, arg_0 -> VerityConfig.AI_PROVIDER.set(arg_0)).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(AiProvider.class)).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"API Key")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"API Key of the provider.\nOnly needed if the provider requests one.")})).binding((Object)"", VerityConfig.API_KEY, arg_0 -> VerityConfig.API_KEY.set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"AI Model")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Model to use with the provider")})).binding((Object)"", VerityConfig.AI_MODEL, arg_0 -> VerityConfig.AI_MODEL.set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Thinking Mode")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"(On supported models)\nMakes the AI think longer before answering")})).binding((Object)true, (Supplier)VerityConfig.AI_THINK, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.AI_THINK).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.m_237113_((String)"Voice Settings")).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Use Text To Speech (TTS)")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Whenever Verity speaks using Text To Speech")})).binding((Object)true, (Supplier)VerityConfig.USE_TTS, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.USE_TTS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Text To Speech Provider")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Provider to use for Text To Speech")})).binding((Object)TTSProvider.LOCAL, VerityConfig.TTS_PROVIDER, arg_0 -> VerityConfig.TTS_PROVIDER.set(arg_0)).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(TTSProvider.class)).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Native TTS Voice")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Voice with native TTS.")})).binding((Object)VerityVoice.DANIEL, () -> VerityVoice.valueOf(((String)VerityConfig.VOICE.get()).toUpperCase(Locale.ROOT)), newValue -> VerityConfig.VOICE.set((Object)newValue.name())).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(VerityVoice.class)).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Kokoro TTS Voice")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Voice used with Kokoro.")})).binding((Object)KokoroVoice.am_fenrir, VerityConfig.KOKORO_VOICE, arg_0 -> VerityConfig.KOKORO_VOICE.set(arg_0)).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(KokoroVoice.class)).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.m_237113_((String)"Speech Recognition")).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Recognition Provider")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Provider to use for Text To Speech")})).binding((Object)STTProvider.NATIVE, VerityConfig.STT_PROVIDER, arg_0 -> VerityConfig.STT_PROVIDER.set(arg_0)).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(STTProvider.class)).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Groq API Key")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"API Key for the groq Speech Recognition")})).binding((Object)"", VerityConfig.GROQ_KEY, arg_0 -> VerityConfig.GROQ_KEY.set(arg_0)).controller(StringControllerBuilder::create).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.m_237113_((String)"Personalisation")).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Custom Color [HUE]")).description(OptionDescription.createBuilder().text(new Component[]{Component.m_237113_((String)"0 = Original Colors. 1-360 = Hue Tint.")}).image(VerityPreviewTexture.id(), 100, 100).build()).binding((Object)0, VerityConfig.COLOR, arg_0 -> VerityConfig.COLOR.set(arg_0)).controller(opt -> ((IntegerSliderControllerBuilder)((IntegerSliderControllerBuilder)IntegerSliderControllerBuilder.create((Option)opt).range((Number)0, (Number)360)).step((Number)1)).formatValue(value -> {
            VerityPreviewTexture.applyHue(value);
            if (value == 0) {
                return Component.m_237113_((String)"0 (Default)");
            }
            int rgb = Mth.m_14169_((float)((float)value.intValue() / 360.0f), (float)1.0f, (float)1.0f);
            return Component.m_237113_((String)("\u2588\u2588 " + value)).m_130948_(Style.f_131099_.m_178520_(rgb));
        })).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Custom Name")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Rename Verity to anything you want!")})).binding((Object)"Verity", VerityConfig.VERITY_CUSTOM_NAME, arg_0 -> VerityConfig.VERITY_CUSTOM_NAME.set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Personality")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Can conflict when changed multiple\ntimes in one world")})).binding((Object)"", VerityConfig.PERSONALITY, arg_0 -> VerityConfig.PERSONALITY.set(arg_0)).controller(StringControllerBuilder::create).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.m_237113_((String)"Advanced")).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Developer Mode")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Turns on developer mode (more logs)")})).binding((Object)false, (Supplier)VerityConfig.DEV_MODE, arg_0 -> ((ForgeConfigSpec.BooleanValue)VerityConfig.DEV_MODE).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"AI URL")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Custom URL for dedicated AI servers")})).binding((Object)"", VerityConfig.AI_ENDPOINT, arg_0 -> VerityConfig.AI_ENDPOINT.set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Text To Speech URL")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Custom URL for dedicated TTS servers.")})).binding((Object)"", VerityConfig.TTS_ENDPOINT, arg_0 -> VerityConfig.TTS_ENDPOINT.set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Speech Recognition URL")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Custom URL for dedicated SR servers.")})).binding((Object)"", VerityConfig.STT_ENDPOINT, arg_0 -> VerityConfig.STT_ENDPOINT.set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.m_237113_((String)"Kokoro Model")).description(OptionDescription.of((Component[])new Component[]{Component.m_237113_((String)"Model used by Kokoro.")})).binding((Object)"", VerityConfig.KOKORO_MODEL, arg_0 -> VerityConfig.KOKORO_MODEL.set(arg_0)).controller(StringControllerBuilder::create).build()).build()).build().generateScreen(previousScreen);
    }
}

