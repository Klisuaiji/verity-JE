package varmite.verity;

import net.neoforged.neoforge.common.ModConfigSpec;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;
import varmite.verity.types.AiProvider;
import varmite.verity.types.KokoroVoice;

/**
 * Verity 6.1 configuration.
 *
 * <p>6.1 replaced the 5.7.x "one boolean per backend" layout (useOllama / useKokoro /
 * useLocalTts / useLocalStt / useNativeTts ...) with explicit provider enums plus a free
 * endpoint + model string for each subsystem:
 *
 * <ul>
 *   <li>{@link #AI_PROVIDER} + {@link #AI_ENDPOINT} + {@link #AI_MODEL} + {@link #API_KEY}</li>
 *   <li>{@link #TTS_PROVIDER} + {@link #TTS_ENDPOINT} (+ Kokoro voice/model)</li>
 *   <li>{@link #STT_PROVIDER} + {@link #STT_ENDPOINT} + {@link #STT_MODEL} (+ {@link #GROQ_KEY})</li>
 * </ul>
 *
 * <p>Ported to NeoForge's {@code ModConfigSpec}. Two keys that only exist in this port
 * ({@code clearPeacefulMobs}, {@code showKarma}) are retained so the 1.21.1 specific
 * behaviour keeps working.
 */
public class VerityConfig {
    public static final ModConfigSpec SPEC;

    // ── General ───────────────────────────────────────────────────────────────
    public static final ModConfigSpec.ConfigValue<Integer> DAY_COUNT;
    public static final ModConfigSpec.BooleanValue CAN_CRASH;
    public static final ModConfigSpec.BooleanValue PLAY_VIDEO;
    public static final ModConfigSpec.BooleanValue REQUIRE_VERITY;
    public static final ModConfigSpec.BooleanValue TRUE_DARKNESS;
    public static final ModConfigSpec.BooleanValue KILL_WILDLIFE;
    public static final ModConfigSpec.BooleanValue KILL_ENTITIES;
    public static final ModConfigSpec.BooleanValue KILL_VILLAGERS;
    public static final ModConfigSpec.BooleanValue CLEAR_PEACEFUL_MOBS;
    public static final ModConfigSpec.BooleanValue SHOW_VERITYS_KARMA;
    public static final ModConfigSpec.BooleanValue IMMERSIVE_MODE;
    public static final ModConfigSpec.BooleanValue DEV_MODE;

    // ── AI ────────────────────────────────────────────────────────────────────
    public static final ModConfigSpec.ConfigValue<String> API_KEY;
    public static final ModConfigSpec.ConfigValue<String> AI_ENDPOINT;
    public static final ModConfigSpec.ConfigValue<String> AI_MODEL;
    public static final ModConfigSpec.EnumValue<AiProvider> AI_PROVIDER;
    public static final ModConfigSpec.BooleanValue AI_THINK;

    // ── Voice (TTS) ───────────────────────────────────────────────────────────
    public static final ModConfigSpec.BooleanValue USE_TTS;
    public static final ModConfigSpec.EnumValue<TTSProvider> TTS_PROVIDER;
    public static final ModConfigSpec.ConfigValue<String> TTS_ENDPOINT;
    public static final ModConfigSpec.ConfigValue<String> VOICE;
    public static final ModConfigSpec.EnumValue<KokoroVoice> KOKORO_VOICE;
    public static final ModConfigSpec.ConfigValue<String> KOKORO_MODEL;

    // ── Speech (STT) ──────────────────────────────────────────────────────────
    public static final ModConfigSpec.EnumValue<STTProvider> STT_PROVIDER;
    public static final ModConfigSpec.ConfigValue<String> GROQ_KEY;
    public static final ModConfigSpec.ConfigValue<String> STT_ENDPOINT;
    public static final ModConfigSpec.ConfigValue<String> STT_MODEL;

    // ── Custom ────────────────────────────────────────────────────────────────
    public static final ModConfigSpec.ConfigValue<Integer> COLOR;
    public static final ModConfigSpec.ConfigValue<String> VERITY_CUSTOM_NAME;
    public static final ModConfigSpec.ConfigValue<String> PERSONALITY;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("GeneralSettings");
        DAY_COUNT = builder.comment("In-game days before Verity turns hostile.").define("dayCount", 5);
        CAN_CRASH = builder.comment("Allow Verity to kick you from the server.").define("canCrash", true);
        PLAY_VIDEO = builder.comment("Play the start up video.").define("playVideo", true);
        REQUIRE_VERITY = builder.comment("Require 'Verity' in every sentence to speak to him.").define("requireVerity", false);
        TRUE_DARKNESS = builder.comment("The extreme darkness toggle").define("trueDarkness", true);
        KILL_WILDLIFE = builder.comment("Toggles if Verity should randomly kill wildlife.").define("killWildlife", true);
        KILL_ENTITIES = builder.comment("Toggles if Verity can kill entities.").define("killEntities", true);
        KILL_VILLAGERS = builder.comment("Toggles if Verity should kill villagers.").define("killVillagers", true);
        CLEAR_PEACEFUL_MOBS = builder.comment("Toggles if Verity should clear peaceful mobs when turning hostile.").define("clearPeacefulMobs", false);
        SHOW_VERITYS_KARMA = builder.comment("Toggles the Karma bar above your hotbar.").define("showKarma", true);
        IMMERSIVE_MODE = builder.comment("Hide all Verity UI (and chat if the server host has this on).").define("immersiveMode", false);

        builder.push("AISettings");
        API_KEY = builder.comment("API Key for providers that requires one.").define("apiKey", "");
        AI_ENDPOINT = builder.comment("URL to use for the AI. Leave empty to use the provider default.").define("aiEndpoint", "");
        AI_MODEL = builder.comment("AI Model to use.").define("aiModel", "");
        AI_PROVIDER = builder.comment("The provider used to power the AI.").defineEnum("aiProvider", AiProvider.OPENAI);
        AI_THINK = builder.comment("Thinking for AI").define("aiThink", true);
        builder.pop();

        builder.push("VoiceSettings");
        USE_TTS = builder.comment("Use the text to speech at all").define("useTTS", true);
        TTS_PROVIDER = builder.comment("Provider to use for Text To Speech").defineEnum("ttsProvider", TTSProvider.NATIVE);
        TTS_ENDPOINT = builder.comment("URL of the TTS server").define("ttsEndpoint", "");
        VOICE = builder.comment("Choose the voice Verity has").define("voice", "Daniel");
        KOKORO_VOICE = builder.comment("The voice Kokoro uses.").defineEnum("kokoroVoice", KokoroVoice.am_fenrir);
        KOKORO_MODEL = builder.comment("Model for Kokoro").define("kokoroModel", "");
        builder.pop();

        builder.push("SpeechSettings");
        STT_PROVIDER = builder.comment("Provider to use for Speech To Text").defineEnum("sttProvider", STTProvider.NATIVE);
        GROQ_KEY = builder.comment("API Key for GROQ.").define("groqKey", "");
        STT_ENDPOINT = builder.comment("Custom endpoint for STT").define("sttEndpoint", "");
        STT_MODEL = builder.comment("Custom model for STT").define("sttModel", "");
        builder.pop();

        builder.push("Custom");
        COLOR = builder.comment("The Hue of Verity's texture color (0 to 360 degrees).").defineInRange("colorHue", 0, 0, 360);
        VERITY_CUSTOM_NAME = builder.comment("The custom name for Verity. Leave empty to use default 'Verity'.").define("customName", "Verity");
        PERSONALITY = builder.comment("The custom personality for Verity.").define("customPersonality", "normal");
        builder.pop();

        builder.push("AdvancedSettings");
        DEV_MODE = builder.comment("Enables dev mode (verbose AI logging).").define("devMode", false);
        builder.pop();

        builder.pop();
        SPEC = builder.build();
    }
}
