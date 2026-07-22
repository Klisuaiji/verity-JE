/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  dev.isxander.yacl3.api.ConfigCategory
 *  dev.isxander.yacl3.api.Option
 *  dev.isxander.yacl3.api.OptionDescription
 *  dev.isxander.yacl3.api.YetAnotherConfigLib
 *  dev.isxander.yacl3.api.controller.BooleanControllerBuilder
 *  dev.isxander.yacl3.api.controller.EnumControllerBuilder
 *  dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
 *  dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
 *  dev.isxander.yacl3.api.controller.StringControllerBuilder
 *  dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.Mth
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.ConfigScreenHandler$ConfigScreenFactory
 *  net.neoforged.neoforge.common.ModConfigSpec$BooleanValue
 *  net.neoforged.neoforge.common.ModConfigSpec$ConfigValue
 *  net.neoforged.neoforge.common.ModConfigSpec$EnumValue
 *  net.neoforged.neoforge.NeoForge
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.ModLoadingContext
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  varmite.verity.AiModel
 *  varmite.verity.AiProvider
 *  varmite.verity.KokoroVoice
 *  varmite.verity.VerityClient
 *  varmite.verity.VerityConfig
 *  varmite.verity.VerityVoice
 *  varmite.verity.client.VerityPreviewTexture
 *  varmite.verity.event.ModBusClientSetup
 */
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
import java.util.Locale;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import varmite.verity.AiModel;
import varmite.verity.AiProvider;
import varmite.verity.KokoroVoice;
import varmite.verity.VerityConfig;
import varmite.verity.VerityVoice;
import varmite.verity.client.VerityPreviewTexture;
import varmite.verity.event.ModBusClientSetup;

/*
 * Exception performing whole class analysis ignored.
 */
@Mod.EventBusSubscriber(modid="verity", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class VerityClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.addListener(ModBusClientSetup::registerRenderers);
        forgeBus.addListener(ModBusClientSetup::onModifyBakingResult);
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, previousScreen) -> VerityClient.createYACLScreen((Screen)previousScreen)));
    }

    public static Screen createYACLScreen(Screen previousScreen) {
        return YetAnotherConfigLib.createBuilder().title((Component)Component.getContents((String)"Verity Configuration")).category(ConfigCategory.createBuilder().name((Component)Component.getContents((String)"General")).option(Option.createBuilder().name((Component)Component.getContents((String)"Can Crash")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Allow Verity to kick you from the server.")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.CAN_CRASH).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.CAN_CRASH).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Play Video")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Play the Verity Edit on the\nstartup of the client.")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.PLAY_VIDEO).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.PLAY_VIDEO).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Require Verity")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Require 'Verity' in every\nsentence to speak to him.")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.REQUIRE_VERITY).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.REQUIRE_VERITY).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"True Darkness")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Toggle the extreme darkness.")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.TRUE_DARKNESS).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.TRUE_DARKNESS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Kill Villagers")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Toggles if Verity should kill villagers.")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.KILL_VILLAGERS).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.KILL_VILLAGERS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Show Karma")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Toggles the Karma bar above your hotbar.")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.SHOW_VERITYS_KARMA).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.SHOW_VERITYS_KARMA).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Hostile Day Threshold")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The amount of in-game days before\nVerity turns into a demon.")})).binding((Object)5, () -> ((ModConfigSpec.ConfigValue)VerityConfig.DAY_COUNT).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.DAY_COUNT).set(arg_0)).controller(opt -> ((IntegerFieldControllerBuilder)IntegerFieldControllerBuilder.create((Option)opt).min((Number)1)).max((Number)100)).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Use text to speech")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Use the text to speech at all")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_TTS).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_TTS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Immersive Mode")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Hide all Verity UI\n(and chat if the server host has this on)")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.IMMERSIVE_MODE).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.IMMERSIVE_MODE).set(arg_0)).controller(BooleanControllerBuilder::create).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.getContents((String)"AI Settings")).option(Option.createBuilder().name((Component)Component.getContents((String)"API Key")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Put your API Key here\nto give Verity a brain.")})).binding((Object)"", () -> ((ModConfigSpec.ConfigValue)VerityConfig.API_KEY).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.API_KEY).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"AI Provider")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Choose between Groq and OpenRouter.")})).binding((Object)AiProvider.GROQ, () -> ((ModConfigSpec.EnumValue)VerityConfig.AI_PROVIDER).get(), arg_0 -> ((ModConfigSpec.EnumValue)VerityConfig.AI_PROVIDER).set(arg_0)).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(AiProvider.class)).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"AI Intelligence Level")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Choose the AI intelligence level.")})).binding((Object)AiModel.FAST, () -> ((ModConfigSpec.EnumValue)VerityConfig.AI_MODEL).get(), newValue -> {
            if (VerityConfig.AI_PROVIDER.get() == AiProvider.OPENROUTER) {
                VerityConfig.AI_MODEL.set((Object)AiModel.FAST);
            } else {
                VerityConfig.AI_MODEL.set(newValue);
            }
        }).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(AiModel.class)).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Use Native TTS")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Use the OS's built-in narrator\ninstead of cloud TTS.")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_NATIVE_TTS).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_NATIVE_TTS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"TTS Voice")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Click to cycle through available voices.")})).binding((Object)VerityVoice.DANIEL, () -> VerityVoice.valueOf((String)((String)VerityConfig.VOICE.get()).toUpperCase(Locale.ROOT)), newValue -> VerityConfig.VOICE.set((Object)newValue.name())).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(VerityVoice.class)).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Use Offline AI Voice")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Use the Built-In TTS model. (Runs on CPU)")})).binding((Object)true, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_LOCAL_TTS).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_LOCAL_TTS).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Use Offline STT")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Use the Built-In STT model. (ENGLISH)")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_LOCAL_STT).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_LOCAL_STT).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.getContents((String)"Local LLM")).tooltip(new Component[]{Component.getContents((String)"Requires beefy PC")}).option(Option.createBuilder().name((Component)Component.getContents((String)"Use Ollama")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"If true the mod will always use\nOllama instead of an AI Provider.")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_OLLAMA).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_OLLAMA).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"LiteLLM URL")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The URL of the LiteLLM Server.")})).binding((Object)"http://127.0.0.1:4000/v1/", () -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_URL).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_URL).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Ollama AI Model")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The AI model the mod uses.")})).binding((Object)"ollama/qwen2.5:1.5b", () -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_AI_MODEL).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_AI_MODEL).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Thinking Mode (Qwen3 Models only)")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Toggles thinking mode for smarter or\nfaster responsens. (Qwen3 Models only)")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.THINKING_MODE).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.THINKING_MODE).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Use Kokoro")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"If true the mod will always use Kokoro TTS instead\nof an AI Provider or build in TTS.")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_KOKORO).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_KOKORO).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Kokoro Fast API URL")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The URL of the FastKokoro Server.")})).binding((Object)"http://127.0.0.1:8880/v1/", () -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_TTS_URL).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_TTS_URL).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"TTS AI Model")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The TTS model the mod uses.")})).binding((Object)"kokoro", () -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_TTS_MODEL).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_TTS_MODEL).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Kokoro Voice")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The voice Kokoro uses.")})).binding((Object)KokoroVoice.am_fenrir, () -> KokoroVoice.valueOf((String)((String)VerityConfig.OLLAMA_TTS_VOICE.get()).toLowerCase(Locale.ROOT)), newValue -> VerityConfig.OLLAMA_TTS_VOICE.set((Object)newValue.name())).controller(opt -> EnumControllerBuilder.create((Option)opt).enumClass(KokoroVoice.class)).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Use Local Whisper")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"If true the mod will always use local STT instead of an AI Provider.")})).binding((Object)false, () -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_LOCAL_WHISPER).get(), arg_0 -> ((ModConfigSpec.BooleanValue)VerityConfig.USE_LOCAL_WHISPER).set(arg_0)).controller(TickBoxControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Local Whisper URL")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The URL of the Whisper Server.")})).binding((Object)"http://127.0.0.1:9000/v1/", () -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_STT_URL).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_STT_URL).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"TTS AI Model")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"The STT model the mod uses.")})).binding((Object)"models/ggml-large-v3-turbo.bin", () -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_STT_MODEL).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.OLLAMA_STT_MODEL).set(arg_0)).controller(StringControllerBuilder::create).build()).build()).category(ConfigCategory.createBuilder().name((Component)Component.getContents((String)"Custom")).option(Option.createBuilder().name((Component)Component.getContents((String)"Color Hue")).description(OptionDescription.createBuilder().text(new Component[]{Component.getContents((String)"0 = Original Colors. 1-360 = Hue Tint.")}).image(VerityPreviewTexture.id(), 100, 100).build()).binding((Object)0, () -> ((ModConfigSpec.ConfigValue)VerityConfig.COLOR).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.COLOR).set(arg_0)).controller(opt -> ((IntegerSliderControllerBuilder)((IntegerSliderControllerBuilder)IntegerSliderControllerBuilder.create((Option)opt).range((Number)0, (Number)360)).step((Number)1)).formatValue(value -> {
            VerityPreviewTexture.applyHue((int)value);
            if (value == 0) {
                return Component.getContents((String)"0 (Disabled)");
            }
            int rgb = Mth.frac((float)((float)value.intValue() / 360.0f), (float)1.0f, (float)1.0f);
            return Component.getContents((String)("\u2588\u2588 " + value)).withStyle(Style.EMPTY.withColor(rgb));
        })).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Custom Name")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Rename Verity to anything you want!")})).binding((Object)"Verity", () -> ((ModConfigSpec.ConfigValue)VerityConfig.VERITY_CUSTOM_NAME).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.VERITY_CUSTOM_NAME).set(arg_0)).controller(StringControllerBuilder::create).build()).option(Option.createBuilder().name((Component)Component.getContents((String)"Personality")).description(OptionDescription.of((Component[])new Component[]{Component.getContents((String)"Can conflict when changed multiple\ntimes in one world")})).binding((Object)"normal", () -> ((ModConfigSpec.ConfigValue)VerityConfig.PERSONALITY).get(), arg_0 -> ((ModConfigSpec.ConfigValue)VerityConfig.PERSONALITY).set(arg_0)).controller(StringControllerBuilder::create).build()).build()).build().generateScreen(previousScreen);
    }
}

