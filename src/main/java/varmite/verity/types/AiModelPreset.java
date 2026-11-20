/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.types;

import varmite.verity.types.AiProvider;

public enum AiModelPreset {
    SMART,
    FAST;


    public String getModel(AiProvider provider) {
        return switch (this) {
            default -> throw new IncompatibleClassChangeError();
            case SMART -> {
                switch (provider) {
                    default: {
                        throw new IncompatibleClassChangeError();
                    }
                    case OPENAI: {
                        yield "gpt-5.4-mini";
                    }
                    case GEMINI: {
                        yield "gemini-3.5-flash";
                    }
                    case GROQ: 
                    case OPENROUTER: {
                        yield "openai/gpt-oss-120b";
                    }
                    case MISTRAL: {
                        yield "mistral-small-2603";
                    }
                    case OLLAMA: 
                }
                throw new IllegalArgumentException("Ollama requires a custom model name.");
            }
            case FAST -> {
                switch (provider) {
                    default: {
                        throw new IncompatibleClassChangeError();
                    }
                    case OPENAI: {
                        yield "gpt-5.4-nano";
                    }
                    case GEMINI: {
                        yield "gemini-3.5-flash-lite";
                    }
                    case GROQ: 
                    case OPENROUTER: {
                        yield "openai/gpt-oss-20b";
                    }
                    case MISTRAL: {
                        yield "ministral-3b-2512";
                    }
                    case OLLAMA: 
                }
                throw new IllegalArgumentException("Ollama requires a custom model name.");
            }
        };
    }

    public static String resolve(AiProvider provider, AiModelPreset preset, boolean useCustomModel, String customModel) {
        if (provider == AiProvider.OLLAMA || useCustomModel) {
            if (customModel == null || customModel.isBlank()) {
                throw new IllegalStateException("Enter a custom AI model for " + String.valueOf((Object)provider) + ".");
            }
            return customModel.trim();
        }
        return preset.getModel(provider);
    }
}

