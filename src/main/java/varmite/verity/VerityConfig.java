/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.neoforge.common.ModConfigSpec
 *  net.neoforged.neoforge.common.ModConfigSpec$BooleanValue
 *  net.neoforged.neoforge.common.ModConfigSpec$Builder
 *  net.neoforged.neoforge.common.ModConfigSpec$ConfigValue
 *  net.neoforged.neoforge.common.ModConfigSpec$EnumValue
 *  varmite.verity.AiModel
 *  varmite.verity.AiProvider
 *  varmite.verity.VerityConfig
 */
package varmite.verity;

import net.neoforged.fml.config.ModConfigSpec;
import varmite.verity.AiModel;
import varmite.verity.AiProvider;

public class VerityConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue CAN_CRASH;
    public static final ModConfigSpec.BooleanValue REQUIRE_VERITY;
    public static final ModConfigSpec.BooleanValue PLAY_VIDEO;
    public static final ModConfigSpec.BooleanValue TRUE_DARKNESS;
    public static final ModConfigSpec.BooleanValue KILL_VILLAGERS;
    public static final ModConfigSpec.BooleanValue SHOW_VERITYS_KARMA;
    public static final ModConfigSpec.BooleanValue USE_OLLAMA;
    public static final ModConfigSpec.BooleanValue USE_KOKORO;
    public static final ModConfigSpec.BooleanValue THINKING_MODE;
    public static final ModConfigSpec.BooleanValue USE_LOCAL_WHISPER;
    public static final ModConfigSpec.ConfigValue<String> API_KEY;
    public static final ModConfigSpec.ConfigValue<String> VOICE;
    public static final ModConfigSpec.ConfigValue<Integer> DAY_COUNT;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_URL;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_AI_MODEL;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_TTS_URL;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_TTS_MODEL;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_TTS_VOICE;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_STT_URL;
    public static final ModConfigSpec.ConfigValue<String> OLLAMA_STT_MODEL;
    public static final ModConfigSpec.EnumValue<AiModel> AI_MODEL;
    public static final ModConfigSpec.EnumValue<AiProvider> AI_PROVIDER;
    public static final ModConfigSpec.ConfigValue<Integer> COLOR;
    public static final ModConfigSpec.BooleanValue USE_NATIVE_TTS;
    public static final ModConfigSpec.ConfigValue<String> VERITY_CUSTOM_NAME;
    public static final ModConfigSpec.ConfigValue<String> PERSONALITY;
    public static final ModConfigSpec.BooleanValue USE_LOCAL_TTS;
    public static final ModConfigSpec.BooleanValue USE_LOCAL_STT;
    public static final ModConfigSpec.BooleanValue USE_TTS;
    public static final ModConfigSpec.BooleanValue IMMERSIVE_MODE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        USE_TTS = builder.comment("Use the text to speech at all").define("useTTS", true);
        IMMERSIVE_MODE = builder.comment("Hide all Verity UI (and chat if the server host has this on)").define("immersiveMode", false);
        DAY_COUNT = builder.comment("The amount of in-game days before Verity turns hostile/demon.").define("dayCount", (Object)5);
        CAN_CRASH = builder.comment("Allow Verity to kick you from the server.").define("canCrash", true);
        PLAY_VIDEO = builder.comment("Play the Verity Edit on the startup of the client.").define("playVideo", true);
        REQUIRE_VERITY = builder.comment("Require 'Verity' in every sentence to speak to him.").define("requireVerity", false);
        USE_NATIVE_TTS = builder.comment("Use the OS's built-in narrator (Windows, macOS, Linux) instead of Groq cloud TTS.").define("useNativeTts", false);
        TRUE_DARKNESS = builder.comment("The extreme darkness toggle").define("trueDarkness", true);
        KILL_VILLAGERS = builder.comment("Toggles if Verity should kill villagers.").define("killVillagers", true);
        SHOW_VERITYS_KARMA = builder.comment("Toggles the Karma bar above your hotbar.").define("showKarma", true);
        builder.push("AISettings");
        VOICE = builder.comment("Choose the voice Verity has").define("voice", (Object)"Daniel");
        USE_LOCAL_TTS = builder.comment("Use the offline Kitten AI Voice instead of Groq cloud TTS.").define("useLocalTts", true);
        USE_LOCAL_STT = builder.comment("Offline STT").define("useLocalStt", false);
        API_KEY = builder.comment("Put your Groq API Key here to give Verity a brain.").define("apiKey", (Object)"");
        AI_MODEL = builder.comment("Choose the AI intelligence level.").defineEnum("aiModel", (Enum)AiModel.FAST);
        AI_PROVIDER = builder.comment("Choose the AI Provider to use for Verity.").defineEnum("aiProvider", (Enum)AiProvider.GROQ);
        builder.push("Local LLM");
        builder.pop();
        USE_OLLAMA = builder.comment("If true the mod will always use Ollama instead of an AI Provider.").define("use_ollama", false);
        OLLAMA_URL = builder.comment("The URL of the LiteLLM Server.").define("ollama_url", (Object)"http://127.0.0.1:4000/v1/");
        OLLAMA_AI_MODEL = builder.comment("The AI model the mod uses.").define("ollama_ai_model", (Object)"ollama/qwen2.5:1.5b");
        THINKING_MODE = builder.comment("Toggels thinking mode for smarter or faster responsens. (Qwen3 Models only)").define("thinking_mode", false);
        USE_KOKORO = builder.comment("If true the mod will always use Kokoro TTS instead of an AI Provider or build in TTS.").define("use_kokoro", false);
        OLLAMA_TTS_URL = builder.comment("The URL of the FastKokoro Server.").define("ollama_tts_url", (Object)"http://127.0.0.1:8880/v1/");
        OLLAMA_TTS_MODEL = builder.comment("The TTS model the mod uses.").define("ollama_tts_model", (Object)"kokoro");
        OLLAMA_TTS_VOICE = builder.comment("The voice Kokoro uses.").define("ollama_tts_voice", (Object)"am_fenrir");
        USE_LOCAL_WHISPER = builder.comment("If true the mod will always use local STT instead of an AI Provider.").define("use_local_whisper", false);
        OLLAMA_STT_URL = builder.comment("The URL of the Whisper Server.").define("ollama_stt_url", (Object)"http://127.0.0.1:9000/v1/");
        OLLAMA_STT_MODEL = builder.comment("The STT model the mod uses.").define("ollama_stt_model", (Object)"models/ggml-large-v3-turbo.bin");
        builder.pop();
        builder.push("Custom");
        COLOR = builder.comment("The Hue of Verity's texture color (0 to 360 degrees).").defineInRange("colorHue", 0, 0, 360);
        VERITY_CUSTOM_NAME = builder.comment("The custom name for Verity. Leave empty to use default 'Verity'.").define("customName", (Object)"Verity");
        PERSONALITY = builder.comment("The custom personality for Verity.").define("customPersonality", (Object)"normal");
        builder.pop();
        SPEC = builder.build();
    }
}

