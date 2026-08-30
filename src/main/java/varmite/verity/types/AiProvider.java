package varmite.verity.types;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum AiProvider {
    GEMINI,
    GROQ,
    OPENROUTER,
    MISTRAL,
    OLLAMA,
    OPENAI;

    private static final Map<AiProvider, String> DEFAULT_URLS = Map.ofEntries(
            Map.entry(GEMINI, "https://api.gemini.com"),
            Map.entry(GROQ, "https://api.groq.com"),
            Map.entry(OPENROUTER, "https://openrouter.ai"),
            Map.entry(MISTRAL, "https://api.mistral.ai"),
            Map.entry(OLLAMA, "http://127.0.0.1:11434"),
            Map.entry(OPENAI, "https://api.openai.com"));

    public String getDefaultUrl() {
        return DEFAULT_URLS.get(this);
    }

    public static List<AiProvider> requiresEndpoint() {
        return Arrays.stream(AiProvider.values())
                .filter(p -> p.getDefaultUrl() == null)
                .toList();
    }
}
