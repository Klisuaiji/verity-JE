/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.ForgeConfigSpec
 *  net.minecraftforge.common.ForgeConfigSpec$BooleanValue
 *  net.minecraftforge.common.ForgeConfigSpec$Builder
 *  net.minecraftforge.common.ForgeConfigSpec$ConfigValue
 *  net.minecraftforge.common.ForgeConfigSpec$EnumValue
 */
package varmite.verity;

import net.minecraftforge.common.ForgeConfigSpec;
import varmite.verity.types.AiProvider;
import varmite.verity.types.KokoroVoice;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;

public class VerityConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue CAN_CRASH;
    public static final ForgeConfigSpec.BooleanValue REQUIRE_VERITY;
    public static final ForgeConfigSpec.BooleanValue PLAY_VIDEO;
    public static final ForgeConfigSpec.BooleanValue TRUE_DARKNESS;
    public static final ForgeConfigSpec.BooleanValue KILL_WILDLIFE;
    public static final ForgeConfigSpec.BooleanValue KILL_VILLAGERS;
    public static final ForgeConfigSpec.BooleanValue KILL_ENTITIES;
    public static final ForgeConfigSpec.BooleanValue IMMERSIVE_MODE;
    public static final ForgeConfigSpec.BooleanValue AI_THINK;
    public static final ForgeConfigSpec.BooleanValue DEV_MODE;
    public static final ForgeConfigSpec.ConfigValue<String> API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> VOICE;
    public static final ForgeConfigSpec.ConfigValue<Integer> DAY_COUNT;
    public static final ForgeConfigSpec.ConfigValue<String> AI_MODEL;
    public static final ForgeConfigSpec.ConfigValue<String> AI_ENDPOINT;
    public static final ForgeConfigSpec.EnumValue<AiProvider> AI_PROVIDER;
    public static final ForgeConfigSpec.EnumValue<TTSProvider> TTS_PROVIDER;
    public static final ForgeConfigSpec.ConfigValue<String> TTS_ENDPOINT;
    public static final ForgeConfigSpec.EnumValue<KokoroVoice> KOKORO_VOICE;
    public static final ForgeConfigSpec.ConfigValue<String> KOKORO_MODEL;
    public static final ForgeConfigSpec.EnumValue<STTProvider> STT_PROVIDER;
    public static final ForgeConfigSpec.ConfigValue<String> GROQ_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> STT_ENDPOINT;
    public static final ForgeConfigSpec.ConfigValue<String> STT_MODEL;
    public static final ForgeConfigSpec.ConfigValue<Integer> COLOR;
    public static final ForgeConfigSpec.ConfigValue<String> VERITY_CUSTOM_NAME;
    public static final ForgeConfigSpec.ConfigValue<String> PERSONALITY;
    public static final ForgeConfigSpec.BooleanValue USE_TTS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("GeneralSettings");
        DAY_COUNT = builder.comment("In-game days before Verity turns hostile.").define("dayCount", (Object)5);
        CAN_CRASH = builder.comment("Allow Verity to kick you from the server.").define("canCrash", true);
        PLAY_VIDEO = builder.comment("Play the start up video.").define("playVideo", true);
        REQUIRE_VERITY = builder.comment("Require 'Verity' in every sentence to speak to him.").define("requireVerity", false);
        TRUE_DARKNESS = builder.comment("The extreme darkness toggle").define("trueDarkness", true);
        KILL_WILDLIFE = builder.comment("Toggles if Verity should randomly kill wildlife.").define("killWildlife", true);
        KILL_ENTITIES = builder.comment("Toggles if Verity can kill entities.").define("killEntities", true);
        KILL_VILLAGERS = builder.comment("Toggles if Verity should kill villagers.").define("killVillagers", true);
        IMMERSIVE_MODE = builder.comment("Toggles the Karma bar above your hotbar.").define("showKarma", false);
        builder.push("AISettings");
        API_KEY = builder.comment("API Key for providers that requires one.").define("apiKey", (Object)"");
        AI_ENDPOINT = builder.comment("URL to use for the AI.").define("aiEndpoint", (Object)"");
        AI_MODEL = builder.comment("AI Model to use.").define("aiModel", (Object)"");
        AI_PROVIDER = builder.comment("The provider used to power the AI.").defineEnum("aiProvider", (Enum)AiProvider.OPENAI);
        AI_THINK = builder.comment("Thinking for AI").define("aiThink", true);
        builder.pop();
        builder.push("VoiceSettings");
        USE_TTS = builder.comment("Use the text to speech at all").define("useTTS", true);
        TTS_PROVIDER = builder.comment("Provider to use for Text To Speech").defineEnum("ttsProvider", (Enum)TTSProvider.LOCAL);
        TTS_ENDPOINT = builder.comment("URL of the TTS server").define("ttsEndpoint", (Object)"");
        VOICE = builder.comment("Choose the voice Verity has").define("voice", (Object)"Daniel");
        KOKORO_VOICE = builder.comment("The voice Kokoro uses.").defineEnum("kokoroVoice", (Enum)KokoroVoice.am_fenrir);
        KOKORO_MODEL = builder.comment("Model for Kokoro").define("kokoroModel", (Object)"");
        builder.pop();
        builder.push("SpeechSettings");
        STT_PROVIDER = builder.comment("Provider to use for Text To Speech").defineEnum("sttProvider", (Enum)STTProvider.NATIVE);
        GROQ_KEY = builder.comment("API Key for GROQ.").define("groqKey", (Object)"");
        STT_ENDPOINT = builder.comment("Custom endpoint for STT").define("sttEndpoint", (Object)"");
        STT_MODEL = builder.comment("Custom endpoint for STT").define("sttModel", (Object)"");
        builder.pop();
        builder.push("Custom");
        COLOR = builder.comment("The Hue of Verity's texture color (0 to 360 degrees).").defineInRange("colorHue", 0, 0, 360);
        VERITY_CUSTOM_NAME = builder.comment("The custom name for Verity. Leave empty to use default 'Verity'.").define("customName", (Object)"Verity");
        PERSONALITY = builder.comment("The custom personality for Verity.").define("customPersonality", (Object)"");
        DEV_MODE = builder.comment("Enables dev mode").define("devMode", false);
        builder.pop();
        SPEC = builder.build();
    }
}

