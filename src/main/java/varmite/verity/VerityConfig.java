/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.neoforge.common.ModConfigSpec
 *  net.neoforged.neoforge.common.ModConfigSpec$BooleanValue
 *  net.neoforged.neoforge.common.ModConfigSpec$Builder
 *  net.neoforged.neoforge.common.ModConfigSpec$ConfigValue
 */
package varmite.verity;

import java.util.Arrays;
import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;

public class VerityConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue CAN_CRASH;
    public static final ModConfigSpec.BooleanValue REQUIRE_VERITY;
    public static final ModConfigSpec.BooleanValue PLAY_VIDEO;
    public static final ModConfigSpec.ConfigValue<String> API_KEY;
    public static final ModConfigSpec.ConfigValue<String> AI_MODEL;
    public static final ModConfigSpec.BooleanValue USE_NATIVE_TTS;
    private static final List<String> MODEL_OPTIONS;

    static {
        MODEL_OPTIONS = Arrays.asList("Fast-lite", "Fast", "Intelligent");
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        CAN_CRASH = builder.comment("Allow Verity to kick you from the server.").define("canCrash", true);
        PLAY_VIDEO = builder.comment("Play the Verity Edit on the startup of the client.").define("playVideo", true);
        REQUIRE_VERITY = builder.comment("Require 'Verity' in every sentence to speak to him.").define("requireVerity", false);
        USE_NATIVE_TTS = builder.comment("Use the OS's built-in narrator (Windows, macOS, Linux) instead of Groq cloud TTS.").define("useNativeTts", false);
        builder.push("AISettings");
        API_KEY = builder.comment("Put your Groq API Key here to give Verity a brain.").define("apiKey", "");
        AI_MODEL = builder.comment("Choose the AI intelligence level: Fast-lite, Fast, or Intelligent.").defineInList("aiModel", "Fast", MODEL_OPTIONS);
        builder.pop();
        SPEC = builder.build();
    }
}

