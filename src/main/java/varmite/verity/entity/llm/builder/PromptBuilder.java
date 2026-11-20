package varmite.verity.entity.llm.builder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Loads the XML system prompt shipped in the mod jar ({@code /prompts/verity.xml})
 * and substitutes {@code {PLACEHOLDER}} tokens. Introduced in 6.1 — before that the
 * system prompt was a giant inline string literal in AiAPI.
 */
public final class PromptBuilder {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");

    private PromptBuilder() {
    }

    public static String loadAndFillXml(Class<?> anchorClass, String resourcePath, Map<String, String> fillerValues) {
        String out = readResourceAsString(anchorClass, resourcePath);
        for (Map.Entry<String, String> e : fillerValues.entrySet()) {
            out = out.replace("{" + e.getKey() + "}", e.getValue() == null ? "" : e.getValue());
        }
        return out;
    }

    private static String readResourceAsString(Class<?> anchorClass, String resourcePath) {
        try (InputStream is = anchorClass.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load resource: " + resourcePath, ex);
        }
    }

    public static String formatCurrentDate() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }
}
