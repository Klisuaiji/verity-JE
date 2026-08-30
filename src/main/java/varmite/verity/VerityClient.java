package varmite.verity;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import varmite.verity.client.VerityPreviewTexture;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;

import java.util.Locale;
import varmite.verity.types.VerityVoice;
import varmite.verity.types.AiProvider;
import varmite.verity.types.KokoroVoice;

/**
 * YACL configuration screen, rebuilt for the Verity 6.1 configuration schema.
 *
 * <p>6.1 replaced the "one boolean per backend" layout (USE_OLLAMA / USE_KOKORO /
 * USE_LOCAL_TTS / ...) with provider enums plus a free-form endpoint + model string,
 * so every option below binds to the new {@link VerityConfig} keys.</p>
 */
public class VerityClient {

    private static VerityVoice readVoice() {
        String raw = VerityConfig.VOICE.get();
        if (raw != null) {
            for (VerityVoice v : VerityVoice.values()) {
                if (v.name().equalsIgnoreCase(raw.trim())) {
                    return v;
                }
            }
        }
        return VerityVoice.DANIEL;
    }

    private static void writeVoice(VerityVoice voice) {
        String name = voice.name();
        VerityConfig.VOICE.set(name.charAt(0) + name.substring(1).toLowerCase(Locale.ROOT));
    }

    public static Screen createYACLScreen(Screen previousScreen) {
        // ===================== General =====================
        Option<Boolean> canCrash = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.canCrash"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.canCrash.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.CAN_CRASH.get(),
                        v -> VerityConfig.CAN_CRASH.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> playVideo = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.playVideo"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.playVideo.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.PLAY_VIDEO.get(),
                        v -> VerityConfig.PLAY_VIDEO.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> requireVerity = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.requireVerity"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.requireVerity.tooltip")))
                .binding(Binding.generic(false,
                        () -> VerityConfig.REQUIRE_VERITY.get(),
                        v -> VerityConfig.REQUIRE_VERITY.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> trueDarkness = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.trueDarkness"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.trueDarkness.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.TRUE_DARKNESS.get(),
                        v -> VerityConfig.TRUE_DARKNESS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> killEntities = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.killEntities"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.killEntities.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.KILL_ENTITIES.get(),
                        v -> VerityConfig.KILL_ENTITIES.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> killWildlife = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.killWildlife"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.killWildlife.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.KILL_WILDLIFE.get(),
                        v -> VerityConfig.KILL_WILDLIFE.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> killVillagers = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.killVillagers"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.killVillagers.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.KILL_VILLAGERS.get(),
                        v -> VerityConfig.KILL_VILLAGERS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> clearPeacefulMobs = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.clearPeacefulMobs"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.clearPeacefulMobs.tooltip")))
                .binding(Binding.generic(false,
                        () -> VerityConfig.CLEAR_PEACEFUL_MOBS.get(),
                        v -> VerityConfig.CLEAR_PEACEFUL_MOBS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> showKarma = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.showKarma"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.showKarma.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.SHOW_VERITYS_KARMA.get(),
                        v -> VerityConfig.SHOW_VERITYS_KARMA.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> immersiveMode = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.immersiveMode"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.immersiveMode.tooltip")))
                .binding(Binding.generic(false,
                        () -> VerityConfig.IMMERSIVE_MODE.get(),
                        v -> VerityConfig.IMMERSIVE_MODE.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Integer> dayCount = Option.<Integer>createBuilder()
                .name(Component.translatable("verity.configuration.dayCount"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.dayCount.tooltip")))
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
                .option(killEntities)
                .option(killWildlife)
                .option(killVillagers)
                .option(clearPeacefulMobs)
                .option(showKarma)
                .option(immersiveMode)
                .option(dayCount)
                .build();

        // ===================== AI Settings =====================
        Option<AiProvider> aiProvider = Option.<AiProvider>createBuilder()
                .name(Component.translatable("verity.configuration.aiProvider"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.aiProvider.tooltip")))
                .binding(Binding.generic(AiProvider.GROQ,
                        () -> VerityConfig.AI_PROVIDER.get(),
                        v -> VerityConfig.AI_PROVIDER.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(AiProvider.class))
                .build();

        Option<String> apiKey = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.apiKey"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.apiKey.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.API_KEY.get(),
                        v -> VerityConfig.API_KEY.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> aiModel = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.aiModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.aiModel.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.AI_MODEL.get(),
                        v -> VerityConfig.AI_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<Boolean> aiThink = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.thinkingMode"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.thinkingMode.tooltip")))
                .binding(Binding.generic(false,
                        () -> VerityConfig.AI_THINK.get(),
                        v -> VerityConfig.AI_THINK.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        ConfigCategory aiSettingsCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.configuration.AISettings"))
                .option(aiProvider)
                .option(apiKey)
                .option(aiModel)
                .option(aiThink)
                .build();

        // ===================== Voice Settings =====================
        Option<Boolean> useTts = Option.<Boolean>createBuilder()
                .name(Component.translatable("verity.configuration.useTts"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.useTts.tooltip")))
                .binding(Binding.generic(true,
                        () -> VerityConfig.USE_TTS.get(),
                        v -> VerityConfig.USE_TTS.set(v)))
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<TTSProvider> ttsProvider = Option.<TTSProvider>createBuilder()
                .name(Component.translatable("verity.configuration.ttsProvider"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ttsProvider.tooltip")))
                .binding(Binding.generic(TTSProvider.LOCAL,
                        () -> VerityConfig.TTS_PROVIDER.get(),
                        v -> VerityConfig.TTS_PROVIDER.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(TTSProvider.class))
                .build();

        Option<VerityVoice> voice = Option.<VerityVoice>createBuilder()
                .name(Component.translatable("verity.configuration.voice"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.voice.tooltip")))
                .binding(Binding.generic(VerityVoice.DANIEL,
                        VerityClient::readVoice,
                        VerityClient::writeVoice))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(VerityVoice.class))
                .build();

        Option<KokoroVoice> kokoroVoice = Option.<KokoroVoice>createBuilder()
                .name(Component.translatable("verity.configuration.kokoroVoice"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.kokoroVoice.tooltip")))
                .binding(Binding.generic(KokoroVoice.am_fenrir,
                        () -> VerityConfig.KOKORO_VOICE.get(),
                        v -> VerityConfig.KOKORO_VOICE.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(KokoroVoice.class))
                .build();

        ConfigCategory voiceCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.config.voice"))
                .option(useTts)
                .option(ttsProvider)
                .option(voice)
                .option(kokoroVoice)
                .build();

        // ===================== Speech Recognition =====================
        Option<STTProvider> sttProvider = Option.<STTProvider>createBuilder()
                .name(Component.translatable("verity.configuration.sttProvider"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.sttProvider.tooltip")))
                .binding(Binding.generic(STTProvider.NATIVE,
                        () -> VerityConfig.STT_PROVIDER.get(),
                        v -> VerityConfig.STT_PROVIDER.set(v)))
                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(STTProvider.class))
                .build();

        Option<String> groqKey = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.groqKey"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.groqKey.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.GROQ_KEY.get(),
                        v -> VerityConfig.GROQ_KEY.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        ConfigCategory sttCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.config.speechRecognition"))
                .option(sttProvider)
                .option(groqKey)
                .build();

        // ===================== Personalisation =====================
        Option<Integer> color = Option.<Integer>createBuilder()
                .name(Component.translatable("verity.configuration.color"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.color.tooltip")))
                .binding(Binding.generic(0,
                        () -> VerityConfig.COLOR.get(),
                        v -> VerityConfig.COLOR.set(v)))
                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                        .range(0, 360)
                        .step(1)
                        .formatValue(value -> {
                            VerityPreviewTexture.applyHue(value);
                            if (value == 0) {
                                return Component.literal("0 (Disabled)");
                            }
                            int rgb = Mth.hsvToRgb(value / 360.0f, 1.0f, 1.0f);
                            return Component.literal("\u2588\u2588 " + value)
                                    .setStyle(Style.EMPTY.withColor(rgb));
                        }))
                .build();

        Option<String> customName = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.customName"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.customName.tooltip")))
                .binding(Binding.generic("Verity",
                        () -> VerityConfig.VERITY_CUSTOM_NAME.get(),
                        v -> VerityConfig.VERITY_CUSTOM_NAME.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> personality = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.personality"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.personality.tooltip")))
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

        // ===================== Advanced =====================
        Option<String> aiEndpoint = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.aiEndpoint"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.aiEndpoint.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.AI_ENDPOINT.get(),
                        v -> VerityConfig.AI_ENDPOINT.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> ttsEndpoint = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.ttsEndpoint"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.ttsEndpoint.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.TTS_ENDPOINT.get(),
                        v -> VerityConfig.TTS_ENDPOINT.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> sttEndpoint = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.sttEndpoint"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.sttEndpoint.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.STT_ENDPOINT.get(),
                        v -> VerityConfig.STT_ENDPOINT.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> sttModel = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.sttModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.sttModel.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.STT_MODEL.get(),
                        v -> VerityConfig.STT_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        Option<String> kokoroModel = Option.<String>createBuilder()
                .name(Component.translatable("verity.configuration.kokoroModel"))
                .description(OptionDescription.of(
                        Component.translatable("verity.configuration.kokoroModel.tooltip")))
                .binding(Binding.generic("",
                        () -> VerityConfig.KOKORO_MODEL.get(),
                        v -> VerityConfig.KOKORO_MODEL.set(v)))
                .controller(StringControllerBuilder::create)
                .build();

        ConfigCategory advancedCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("verity.config.advanced"))
                .option(aiEndpoint)
                .option(ttsEndpoint)
                .option(sttEndpoint)
                .option(sttModel)
                .option(kokoroModel)
                .build();

        // ===================== Assemble =====================
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("verity.config.title"))
                .category(generalCategory)
                .category(aiSettingsCategory)
                .category(voiceCategory)
                .category(sttCategory)
                .category(customizationCategory)
                .category(advancedCategory)
                // Persist changes to disk. ModConfigSpec.ConfigValue.set() only updates the
                // in-memory config; it does NOT write the .toml file (per its own Javadoc).
                // YACL calls this Runnable when the screen is closed, so we flush the spec here.
                .save(() -> VerityConfig.SPEC.save())
                .build()
                .generateScreen(previousScreen);
    }
}
