/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.ollama;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import varmite.verity.ollama.OllamaApi;

public final class OllamaModels {
    public static final List<Recommended> RECOMMENDED = List.of(new Recommended("qwen3.5:4b", "4B", 3320000000L, true, "Best all-rounder. Smart and quick on most gaming PCs (6 GB+ VRAM)."), new Recommended("qwen3:4b-instruct", "4B", 2500000000L, false, "Fastest replies - no thinking step. Good tool use. 4 GB+ VRAM."), new Recommended("llama3.2:3b", "3B", 2020000000L, false, "Very light. For laptops and older GPUs, or CPU only."), new Recommended("granite4:micro", "3B", 2100000000L, false, "Tiny model built for tool calling. Runs almost anywhere."), new Recommended("qwen3:8b", "8B", 5230000000L, true, "Accurate tool use with Thinking on. Needs 8 GB VRAM."), new Recommended("ministral-3:8b", "8B", 6020000000L, false, "Mistral's edge model. Strong chat, no thinking step. 8 GB+ VRAM."), new Recommended("qwen3.5:9b", "9B", 6550000000L, true, "Smartest under 12B. Wants 10 GB+ VRAM next to Minecraft."));
    private static final long LIST_TTL_MS = 15000L;
    private static final Map<String, CachedList> LISTS = new ConcurrentHashMap<String, CachedList>();
    private static final Map<String, Set<String>> CAPABILITIES = new ConcurrentHashMap<String, Set<String>>();

    private OllamaModels() {
    }

    public static List<OllamaApi.InstalledModel> installed(String baseUrl, boolean forceRefresh) {
        String url = OllamaApi.normalize(baseUrl);
        CachedList cached = LISTS.get(url);
        if (!forceRefresh && cached != null && System.currentTimeMillis() - cached.at() < 15000L) {
            return cached.models();
        }
        try {
            List<OllamaApi.InstalledModel> models = List.copyOf(OllamaApi.listModels(url, Duration.ofSeconds(4L)));
            LISTS.put(url, new CachedList(System.currentTimeMillis(), models));
            return models;
        }
        catch (IOException e) {
            LISTS.remove(url);
            return List.of();
        }
    }

    public static List<OllamaApi.InstalledModel> lastKnown(String baseUrl) {
        CachedList cached = LISTS.get(OllamaApi.normalize(baseUrl));
        return cached == null ? List.of() : cached.models();
    }

    public static CompletableFuture<List<OllamaApi.InstalledModel>> refreshAsync(String baseUrl) {
        return CompletableFuture.supplyAsync(() -> OllamaModels.installed(baseUrl, true));
    }

    public static boolean isInstalled(String baseUrl, String tag) {
        String wanted = OllamaModels.withTag(tag);
        return OllamaModels.lastKnown(baseUrl).stream().anyMatch(m -> OllamaModels.withTag(m.name()).equalsIgnoreCase(wanted));
    }

    public static Set<String> capabilities(String baseUrl, String model) {
        String key = OllamaApi.normalize(baseUrl) + "|" + OllamaModels.withTag(model);
        Set<String> cached = CAPABILITIES.get(key);
        if (cached != null) {
            return cached;
        }
        try {
            Set<String> caps = Set.copyOf(OllamaApi.capabilities(baseUrl, model, Duration.ofSeconds(5L)));
            CAPABILITIES.put(key, caps);
            return caps;
        }
        catch (IOException e) {
            return Set.of();
        }
    }

    public static Optional<String> pickDefault(String baseUrl) {
        List<OllamaApi.InstalledModel> models = OllamaModels.installed(baseUrl, false);
        if (models.isEmpty()) {
            return Optional.empty();
        }
        for (Recommended rec : RECOMMENDED) {
            for (OllamaApi.InstalledModel m2 : models) {
                if (!OllamaModels.withTag(m2.name()).equalsIgnoreCase(OllamaModels.withTag(rec.tag()))) continue;
                return Optional.of(m2.name());
            }
        }
        ArrayList<String> toolCapable = new ArrayList<String>();
        for (OllamaApi.InstalledModel m3 : models) {
            if (OllamaModels.isEmbeddingOnly(m3) || !OllamaModels.capabilities(baseUrl, m3.name()).contains("tools")) continue;
            toolCapable.add(m3.name());
        }
        if (!toolCapable.isEmpty()) {
            return Optional.of((String)toolCapable.get(0));
        }
        return models.stream().filter(m -> !OllamaModels.isEmbeddingOnly(m)).map(OllamaApi.InstalledModel::name).findFirst();
    }

    public static void invalidate(String baseUrl) {
        String url = OllamaApi.normalize(baseUrl);
        LISTS.remove(url);
        CAPABILITIES.keySet().removeIf(key -> key.startsWith(url + "|"));
    }

    public static String withTag(String model) {
        String m = model == null ? "" : model.trim();
        return m.contains(":") ? m : m + ":latest";
    }

    private static boolean isEmbeddingOnly(OllamaApi.InstalledModel model) {
        String name = model.name().toLowerCase(Locale.ROOT);
        return name.contains("embed") || model.family().toLowerCase(Locale.ROOT).contains("bert");
    }

    private record CachedList(long at, List<OllamaApi.InstalledModel> models) {
    }

    public record Recommended(String tag, String params, long downloadBytes, boolean thinking, String blurb) {
    }
}

