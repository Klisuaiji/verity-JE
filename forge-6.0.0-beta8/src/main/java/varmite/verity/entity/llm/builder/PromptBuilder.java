/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.entity.llm.builder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public final class PromptBuilder {
    public static String loadAndFillXml(Class<?> anchorClass, String resourcePath, Map<String, String> fillerValues) {
        String out = PromptBuilder.readResourceAsString(anchorClass, resourcePath);
        for (Map.Entry<String, String> e : fillerValues.entrySet()) {
            out = out.replace("{" + e.getKey() + "}", e.getValue());
        }
        return out;
    }

    private static String readResourceAsString(Class<?> anchorClass, String resourcePath) {
        String string;
        block9: {
            InputStream is = anchorClass.getResourceAsStream(resourcePath);
            try {
                if (is == null) {
                    throw new IllegalArgumentException("Resource not found: " + resourcePath);
                }
                byte[] bytes = is.readAllBytes();
                string = new String(bytes, StandardCharsets.UTF_8);
                if (is == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (is != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception ex) {
                    throw new RuntimeException("Failed to load resource: " + resourcePath, ex);
                }
            }
            is.close();
        }
        return string;
    }

    public static String formatCurrentDate() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        return LocalDateTime.now().format(fmt);
    }
}

