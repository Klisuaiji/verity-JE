package varmite.verity.types;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum STTProvider {
    GROQ,
    NATIVE,
    WHISPER;

    private static final Map<STTProvider, String> DEFAULT_URLS = Map.ofEntries(
            Map.entry(GROQ, ""),
            Map.entry(NATIVE, ""),
            Map.entry(WHISPER, "http://127.0.0.1:8080"));

    public String getDefaultUrl() {
        return DEFAULT_URLS.get(this);
    }

    public static List<STTProvider> requiresEndpoint() {
        return Arrays.stream(STTProvider.values())
                .filter(p -> Objects.equals(p.getDefaultUrl(), ""))
                .toList();
    }
}
