package varmite.verity;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class VerityClient {

    public static Screen createYACLScreen(Screen previousScreen) {
        // ===== General Category =====
        Option<Boolean> canCrash = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.canCrash"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.canCrash.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.CAN_CRASH.get(),
                        v -> VerityConfig.CAN_CRASH.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> playVideo = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.playVideo"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.playVideo.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.PLAY_VIDEO.get(),
                        v -> VerityConfig.PLAY_VIDEO.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> requireVerity = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.requireVerity"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.requireVerity.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.REQUIRE_VERITY.get(),
                        v -> VerityConfig.REQUIRE_VERITY.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> trueDarkness = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.trueDarkness"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.trueDarkness.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.TRUE_DARKNESS.get(),
                        v -> VerityConfig.TRUE_DARKNESS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> killVillagers = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.killVillagers"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.killVillagers.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.KILL_VILLAGERS.get(),
                        v -> VerityConfig.KILL_VILLAGERS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> clearPeacefulMobs = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.clearPeacefulMobs"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.clearPeacefulMobs.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.CLEAR_PEACEFUL_MOBS.get(),
                        v -> VerityConfig.CLEAR_PEACEFUL_MOBS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> showKarma = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.showKarma"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.showKarma.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.SHOW_VERITYS_KARMA.get(),
                        v -> VerityConfig.SHOW_VERITYS_KARMA.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> immersiveMode = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.immersiveMode"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.immersiveMode.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.IMMERSIVE_MODE.get(),
                        v -> VerityConfig.IMMERSIVE_MODE.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useTts = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useTts"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useTts.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.USE_TTS.get(),
                        v -> VerityConfig.USE_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Integer> dayCount = Option.<Integer>createBuilder()
                .name(Component.translatable("verity.configuration.dayCount"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.dayCount.tooltip")
                ))
                .binding(Binding.generic(5,
                        () -> VerityConfig.DAY_COUNT.get(),
                        v -> VerityConfig.DAY_COUNT.set(v)))
                .controller(IntegerFieldControllerBuilder::create)
                .build();

        ConfigCategory generalCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.config.general"))
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
                .name(Component.translatable("verity.configuration.voice"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.voice.tooltip")
                ))
                .binding(Binding.generic("Daniel",
                        () -> VerityConfig.VOICE.get(),
                        v -> VerityConfig.VOICE.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> useNativeTts = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useNativeTts"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useNativeTts.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_NATIVE_TTS.get(),
                        v -> VerityConfig.USE_NATIVE_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useLocalTts = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useLocalTts"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useLocalTts.tooltip")
                ))
                .binding(Binding.generic(true,
                        () -> VerityConfig.USE_LOCAL_TTS.get(),
                        v -> VerityConfig.USE_LOCAL_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useLocalStt = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useLocalStt"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useLocalStt.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_LOCAL_STT.get(),
                        v -> VerityConfig.USE_LOCAL_STT.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> apiKey = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.apiKey"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.apiKey.tooltip")
                ))
                .binding(Binding.generic("",
                        () -> VerityConfig.API_KEY.get(),
                        v -> VerityConfig.API_KEY.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<AiModel> aiModel = Option.<AiModel>createBuilder()
                .name(Component.translatable("verity.configuration.aiModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.aiModel.tooltip")
                ))
                .binding(Binding.generic(AiModel.FAST,
                        () -> VerityConfig.AI_MODEL.get(),
                        v -> VerityConfig.AI_MODEL.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(AiModel.class))
                .build();

        Option<AiProvider> aiProvider = Option.<AiProvider>createBuilder()
                .name(Component.translatable("verity.configuration.aiProvider"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.aiProvider.tooltip")
                ))
                .binding(Binding.generic(AiProvider.GROQ,
                        () -> VerityConfig.AI_PROVIDER.get(),
                        v -> VerityConfig.AI_PROVIDER.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(AiProvider.class))
                .build();

        Option<Boolean> useOllama = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useOllama"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useOllama.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_OLLAMA.get(),
                        v -> VerityConfig.USE_OLLAMA.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> ollamaUrl = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaUrl"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaUrl.tooltip")
                ))
                .binding(Binding.generic("http://127.0.0.1:4000/v1/",
                        () -> VerityConfig.OLLAMA_URL.get(),
                        v -> VerityConfig.OLLAMA_URL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaAiModel = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaAiModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaAiModel.tooltip")
                ))
                .binding(Binding.generic("ollama/qwen2.5:1.5b",
                        () -> VerityConfig.OLLAMA_AI_MODEL.get(),
                        v -> VerityConfig.OLLAMA_AI_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> thinkingMode = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.thinkingMode"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.thinkingMode.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.THINKING_MODE.get(),
                        v -> VerityConfig.THINKING_MODE.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> useKokoro = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useKokoro"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useKokoro.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_KOKORO.get(),
                        v -> VerityConfig.USE_KOKORO.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> ollamaTtsUrl = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaTtsUrl"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaTtsUrl.tooltip")
                ))
                .binding(Binding.generic("http://127.0.0.1:8880/v1/",
                        () -> VerityConfig.OLLAMA_TTS_URL.get(),
                        v -> VerityConfig.OLLAMA_TTS_URL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaTtsModel = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaTtsModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaTtsModel.tooltip")
                ))
                .binding(Binding.generic("kokoro",
                        () -> VerityConfig.OLLAMA_TTS_MODEL.get(),
                        v -> VerityConfig.OLLAMA_TTS_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaTtsVoice = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaTtsVoice"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaTtsVoice.tooltip")
                ))
                .binding(Binding.generic("am_fenrir",
                        () -> VerityConfig.OLLAMA_TTS_VOICE.get(),
                        v -> VerityConfig.OLLAMA_TTS_VOICE.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> useLocalWhisper = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useLocalWhisper"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useLocalWhisper.tooltip")
                ))
                .binding(Binding.generic(false,
                        () -> VerityConfig.USE_LOCAL_WHISPER.get(),
                        v -> VerityConfig.USE_LOCAL_WHISPER.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<String> ollamaSttUrl = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaSttUrl"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaSttUrl.tooltip")
                ))
                .binding(Binding.generic("http://127.0.0.1:9000/v1/",
                        () -> VerityConfig.OLLAMA_STT_URL.get(),
                        v -> VerityConfig.OLLAMA_STT_URL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ollamaSttModel = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ollamaSttModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ollamaSttModel.tooltip")
                ))
                .binding(Binding.generic("models/ggml-large-v3-turbo.bin",
                        () -> VerityConfig.OLLAMA_STT_MODEL.get(),
                        v -> VerityConfig.OLLAMA_STT_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        ConfigCategory aiVoiceCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.configuration.AISettings"))
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
                .name(Component.translatable("verity.configuration.color"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.color.tooltip")
                ))
                .binding(Binding.generic(0,
                        () -> VerityConfig.COLOR.get(),
                        v -> VerityConfig.COLOR.set(v)))
                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 360).step(1))
                .build();

        Option<String> customName = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.customName"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.customName.tooltip")
                ))
                .binding(Binding.generic("Verity",
                        () -> VerityConfig.VERITY_CUSTOM_NAME.get(),
                        v -> VerityConfig.VERITY_CUSTOM_NAME.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> personality = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.personality"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.personality.tooltip")
                ))
                .binding(Binding.generic("normal",
                        () -> VerityConfig.PERSONALITY.get(),
                        v -> VerityConfig.PERSONALITY.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        ConfigCategory customizationCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.config.customization"))
                .option(color)
                .option(customName)
                .option(personality)
                .build();

        // ===== Assemble =====
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("verity.config.title"))
                .category(generalCategory)
                .category(aiVoiceCategory)
                .category(customizationCategory)
                .build()
                .generateScreen(previousScreen);
    }
}
