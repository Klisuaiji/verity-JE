package varmite.verity;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "verity", bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class VerityClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Note: registerRenderers (EntityRenderersEvent.RegisterRenderers) and
        // onModifyBakingResult (ModelEvent.ModifyBakingResult) are IModBusEvents and are
        // already auto-subscribed on the MOD bus via ModBusClientSetup's @EventBusSubscriber.
        // Re-registering them on the GAME bus throws "IModBusEvent events are not allowed on
        // the common NeoForge bus". Do NOT addListener them here.
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (minecraft, previousScreen) -> VerityClient.createYACLScreen(previousScreen));
    }

    public static Screen createYACLScreen(Screen previousScreen) {
        // ===== General Category =====
        Option<Boolean> canCrash = Option.<Boolean>createBuilder()
                .name(Component.literal("Can Crash"))
                .description(OptionDescription.of(
                        Component.literal("Allow Verity to kick you from the server.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.CAN_CRASH.get(),
                        v -> VerityConfig.CAN_CRASH.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> playVideo = Option.<Boolean>createBuilder()
                .name(Component.literal("Play Video"))
                .description(OptionDescription.of(
                        Component.literal("Play the Verity Edit on the startup of the client.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.PLAY_VIDEO.get(),
                        v -> VerityConfig.PLAY_VIDEO.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> requireVerity = Option.<Boolean>createBuilder()
                .name(Component.literal("Require Verity"))
                .description(OptionDescription.of(
                        Component.literal("Require 'Verity' in every sentence to speak to him.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.REQUIRE_VERITY.get(),
                        v -> VerityConfig.REQUIRE_VERITY.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> trueDarkness = Option.<Boolean>createBuilder()
                .name(Component.literal("True Darkness"))
                .description(OptionDescription.of(
                        Component.literal("Toggle the extreme darkness.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.TRUE_DARKNESS.get(),
                        v -> VerityConfig.TRUE_DARKNESS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> killVillagers = Option.<Boolean>createBuilder()
                .name(Component.literal("Kill Villagers"))
                .description(OptionDescription.of(
                        Component.literal("Toggles if Verity should kill villagers.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.KILL_VILLAGERS.get(),
                        v -> VerityConfig.KILL_VILLAGERS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> clearPeacefulMobs = Option.<Boolean>createBuilder()
                .name(Component.literal("Clear Peaceful Mobs"))
                .description(OptionDescription.of(
                        Component.literal("Toggles if Verity should clear peaceful mobs when turning hostile.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.CLEAR_PEACEFUL_MOBS.get(),
                        v -> VerityConfig.CLEAR_PEACEFUL_MOBS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> showKarma = Option.<Boolean>createBuilder()
                .name(Component.literal("Show Karma"))
                .description(OptionDescription.of(
                        Component.literal("Toggles the Karma bar above your hotbar.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.SHOW_VERITYS_KARMA.get(),
                        v -> VerityConfig.SHOW_VERITYS_KARMA.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> immersiveMode = Option.<Boolean>createBuilder()
                .name(Component.literal("Immersive Mode"))
                .description(OptionDescription.of(
                        Component.literal("Hide all Verity UI (and chat if the server host has this on).")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.IMMERSIVE_MODE.get(),
                        v -> VerityConfig.IMMERSIVE_MODE.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useTts = Option.<Boolean>createBuilder()
                .name(Component.literal("Use TTS"))
                .description(OptionDescription.of(
                        Component.literal("Use the text to speech at all.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.USE_TTS.get(),
                        v -> VerityConfig.USE_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Integer> dayCount = Option.<Integer>createBuilder()
                .name(Component.literal("Day Count"))
                .description(OptionDescription.of(
                        Component.literal("The amount of in-game days before Verity turns hostile/demon.")
                ))
                .binding(Binding.generic(5,
                        () -> VerityConfig.DAY_COUNT.get(),
                        v -> VerityConfig.DAY_COUNT.set(v)))
                .controller(IntegerFieldControllerBuilder::create)
                .build();

        ConfigCategory generalCategory = ConfigCategory.createBuilder()
                .name(Component.literal("General"))
                .option(canCrash)
                .option(playVideo)
                .option(requireVerity)
                .option(trueDarkness)
                .option(killVillagers)
                .option(clearPeacefulMobs)
                .option(showKarma)
                .option(immersiveMode)
                .option(useTts)
                .option(dayCount)
                .build();

        // ===== AI & Voice Category =====
        Option<String> voice = Option.<String>createBuilder()
                .name(Component.literal("Voice"))
                .description(OptionDescription.of(
                        Component.literal("Choose the voice Verity has.")
                ))
                .binding(Binding.generic("Daniel",
                        () -> VerityConfig.VOICE.get(),
                        v -> VerityConfig.VOICE.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> useNativeTts = Option.<Boolean>createBuilder()
                .name(Component.literal("Use Native TTS"))
                .description(OptionDescription.of(
                        Component.literal("Use the OS's built-in narrator (Windows, macOS, Linux) instead of Groq cloud TTS.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_NATIVE_TTS.get(),
                        v -> VerityConfig.USE_NATIVE_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useLocalTts = Option.<Boolean>createBuilder()
                .name(Component.literal("Use Offline AI Voice"))
                .description(OptionDescription.of(
                        Component.literal("Use the offline Kitten AI Voice instead of Groq cloud TTS.")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.USE_LOCAL_TTS.get(),
                        v -> VerityConfig.USE_LOCAL_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useLocalStt = Option.<Boolean>createBuilder()
                .name(Component.literal("Use Offline STT"))
                .description(OptionDescription.of(
                        Component.literal("Offline STT.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_LOCAL_STT.get(),
                        v -> VerityConfig.USE_LOCAL_STT.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> apiKey = Option.<String>createBuilder()
                .name(Component.literal("API Key"))
                .description(OptionDescription.of(
                        Component.literal("Put your Groq API Key here to give Verity a brain.")
                ))
                .binding(Binding.generic("",
                        () -> VerityConfig.API_KEY.get(),
                        v -> VerityConfig.API_KEY.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<AiModel> aiModel = Option.<AiModel>createBuilder()
                .name(Component.literal("AI Model"))
                .description(OptionDescription.of(
                        Component.literal("Choose the AI intelligence level.")
                ))
                .binding(Binding.generic(AiModel.FAST,
                        () -> VerityConfig.AI_MODEL.get(),
                        v -> VerityConfig.AI_MODEL.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(AiModel.class))
                .build();

        Option<AiProvider> aiProvider = Option.<AiProvider>createBuilder()
                .name(Component.literal("AI Provider"))
                .description(OptionDescription.of(
                        Component.literal("Choose the AI Provider to use for Verity.")
                ))
                .binding(Binding.generic(AiProvider.GROQ,
                        () -> VerityConfig.AI_PROVIDER.get(),
                        v -> VerityConfig.AI_PROVIDER.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(AiProvider.class))
                .build();

        Option<Boolean> useOllama = Option.<Boolean>createBuilder()
                .name(Component.literal("Use Ollama"))
                .description(OptionDescription.of(
                        Component.literal("If true the mod will always use Ollama instead of an AI Provider.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_OLLAMA.get(),
                        v -> VerityConfig.USE_OLLAMA.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> ollamaUrl = Option.<String>createBuilder()
                .name(Component.literal("Ollama URL"))
                .description(OptionDescription.of(
                        Component.literal("The URL of the LiteLLM Server.")
                ))
                .binding(Binding.generic("http://127.0.0.1:4000/v1/",
                        () -> VerityConfig.OLLAMA_URL.get(),
                        v -> VerityConfig.OLLAMA_URL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaAiModel = Option.<String>createBuilder()
                .name(Component.literal("Ollama AI Model"))
                .description(OptionDescription.of(
                        Component.literal("The AI model the mod uses.")
                ))
                .binding(Binding.generic("ollama/qwen2.5:1.5b",
                        () -> VerityConfig.OLLAMA_AI_MODEL.get(),
                        v -> VerityConfig.OLLAMA_AI_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> thinkingMode = Option.<Boolean>createBuilder()
                .name(Component.literal("Thinking Mode"))
                .description(OptionDescription.of(
                        Component.literal("Toggles thinking mode for smarter or faster responses. (Qwen3 Models only)")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.THINKING_MODE.get(),
                        v -> VerityConfig.THINKING_MODE.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useKokoro = Option.<Boolean>createBuilder()
                .name(Component.literal("Use Kokoro TTS"))
                .description(OptionDescription.of(
                        Component.literal("If true the mod will always use Kokoro TTS instead of an AI Provider or built-in TTS.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_KOKORO.get(),
                        v -> VerityConfig.USE_KOKORO.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> ollamaTtsUrl = Option.<String>createBuilder()
                .name(Component.literal("Kokoro TTS URL"))
                .description(OptionDescription.of(
                        Component.literal("The URL of the FastKokoro Server.")
                ))
                .binding(Binding.generic("http://127.0.0.1:8880/v1/",
                        () -> VerityConfig.OLLAMA_TTS_URL.get(),
                        v -> VerityConfig.OLLAMA_TTS_URL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaTtsModel = Option.<String>createBuilder()
                .name(Component.literal("Kokoro TTS Model"))
                .description(OptionDescription.of(
                        Component.literal("The TTS model the mod uses.")
                ))
                .binding(Binding.generic("kokoro",
                        () -> VerityConfig.OLLAMA_TTS_MODEL.get(),
                        v -> VerityConfig.OLLAMA_TTS_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaTtsVoice = Option.<String>createBuilder()
                .name(Component.literal("Kokoro TTS Voice"))
                .description(OptionDescription.of(
                        Component.literal("The voice Kokoro uses.")
                ))
                .binding(Binding.generic("am_fenrir",
                        () -> VerityConfig.OLLAMA_TTS_VOICE.get(),
                        v -> VerityConfig.OLLAMA_TTS_VOICE.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> useLocalWhisper = Option.<Boolean>createBuilder()
                .name(Component.literal("Use Local Whisper"))
                .description(OptionDescription.of(
                        Component.literal("If true the mod will always use local STT instead of an AI Provider.")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_LOCAL_WHISPER.get(),
                        v -> VerityConfig.USE_LOCAL_WHISPER.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> ollamaSttUrl = Option.<String>createBuilder()
                .name(Component.literal("Whisper STT URL"))
                .description(OptionDescription.of(
                        Component.literal("The URL of the Whisper Server.")
                ))
                .binding(Binding.generic("http://127.0.0.1:9000/v1/",
                        () -> VerityConfig.OLLAMA_STT_URL.get(),
                        v -> VerityConfig.OLLAMA_STT_URL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaSttModel = Option.<String>createBuilder()
                .name(Component.literal("Whisper STT Model"))
                .description(OptionDescription.of(
                        Component.literal("The STT model the mod uses.")
                ))
                .binding(Binding.generic("models/ggml-large-v3-turbo.bin",
                        () -> VerityConfig.OLLAMA_STT_MODEL.get(),
                        v -> VerityConfig.OLLAMA_STT_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        ConfigCategory aiVoiceCategory = ConfigCategory.createBuilder()
                .name(Component.literal("AI & Voice"))
                .option(voice)
                .option(useNativeTts)
                .option(useLocalTts)
                .option(useLocalStt)
                .option(apiKey)
                .option(aiModel)
                .option(aiProvider)
                .option(useOllama)
                .option(ollamaUrl)
                .option(ollamaAiModel)
                .option(thinkingMode)
                .option(useKokoro)
                .option(ollamaTtsUrl)
                .option(ollamaTtsModel)
                .option(ollamaTtsVoice)
                .option(useLocalWhisper)
                .option(ollamaSttUrl)
                .option(ollamaSttModel)
                .build();

        // ===== Customization Category =====
        Option<Integer> color = Option.<Integer>createBuilder()
                .name(Component.literal("Color"))
                .description(OptionDescription.of(
                        Component.literal("The Hue of Verity's texture color (0 to 360 degrees).")
                ))
                .binding(Binding.generic(0,
                        () -> VerityConfig.COLOR.get(),
                        v -> VerityConfig.COLOR.set(v)))
                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 360))
                .build();

        Option<String> customName = Option.<String>createBuilder()
                .name(Component.literal("Custom Name"))
                .description(OptionDescription.of(
                        Component.literal("The custom name for Verity. Leave empty to use default 'Verity'.")
                ))
                .binding(Binding.generic("Verity",
                        () -> VerityConfig.VERITY_CUSTOM_NAME.get(),
                        v -> VerityConfig.VERITY_CUSTOM_NAME.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> personality = Option.<String>createBuilder()
                .name(Component.literal("Personality"))
                .description(OptionDescription.of(
                        Component.literal("The custom personality for Verity.")
                ))
                .binding(Binding.generic("normal",
                        () -> VerityConfig.PERSONALITY.get(),
                        v -> VerityConfig.PERSONALITY.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        ConfigCategory customizationCategory = ConfigCategory.createBuilder()
                .name(Component.literal("Customization"))
                .option(color)
                .option(customName)
                .option(personality)
                .build();

        // ===== Assemble =====
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Verity Configuration"))
                .category(generalCategory)
                .category(aiVoiceCategory)
                .category(customizationCategory)
                .build()
                .generateScreen(previousScreen);
    }
}
