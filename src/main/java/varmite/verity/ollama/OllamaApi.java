/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.ollama;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BooleanSupplier;

public final class OllamaApi {
    public static final String DEFAULT_URL = "http://127.0.0.1:11434";
    private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3L)).version(HttpClient.Version.HTTP_1_1).build();
    private static final Gson GSON = new Gson();

    private OllamaApi() {
    }

    public static String normalize(String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl.trim();
        url = url.replace("localhost", "127.0.0.1");
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        while (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    public static Optional<String> version(String baseUrl, Duration timeout) {
        try {
            HttpResponse<String> response = HTTP.send(OllamaApi.get(baseUrl, "/api/version", timeout), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return Optional.empty();
            }
            JsonObject json = JsonParser.parseString((String)response.body()).getAsJsonObject();
            return Optional.of(json.has("version") ? json.get("version").getAsString() : "unknown");
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    public static List<InstalledModel> listModels(String baseUrl, Duration timeout) throws IOException {
        JsonObject json = OllamaApi.sendJson(OllamaApi.get(baseUrl, "/api/tags", timeout));
        ArrayList<InstalledModel> models = new ArrayList<InstalledModel>();
        JsonArray array = json.has("models") ? json.getAsJsonArray("models") : new JsonArray();
        for (JsonElement element : array) {
            JsonObject model = element.getAsJsonObject();
            JsonObject details = model.has("details") && model.get("details").isJsonObject() ? model.getAsJsonObject("details") : new JsonObject();
            models.add(new InstalledModel(OllamaApi.string(model, "name"), model.has("size") ? model.get("size").getAsLong() : 0L, OllamaApi.string(details, "parameter_size"), OllamaApi.string(details, "family"), OllamaApi.string(details, "quantization_level")));
        }
        return models;
    }

    public static Set<String> capabilities(String baseUrl, String model, Duration timeout) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("model", model);
        JsonObject json = OllamaApi.sendJson(OllamaApi.post(baseUrl, "/api/show", body, timeout));
        LinkedHashSet<String> caps = new LinkedHashSet<String>();
        if (json.has("capabilities") && json.get("capabilities").isJsonArray()) {
            for (JsonElement cap : json.getAsJsonArray("capabilities")) {
                caps.add(cap.getAsString());
            }
        }
        return caps;
    }

    public static void pull(String baseUrl, String model, PullListener listener, BooleanSupplier cancelled) throws IOException {
        HttpResponse<InputStream> response;
        JsonObject body = new JsonObject();
        body.addProperty("model", model);
        body.addProperty("stream", Boolean.valueOf(true));
        HttpRequest request = OllamaApi.post(baseUrl, "/api/pull", body, null);
        try {
            response = HTTP.send(request, HttpResponse.BodyHandlers.ofInputStream());
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("Interrupted");
        }
        HashMap<String, long[]> layers = new HashMap<String, long[]>();
        try (InputStream stream = response.body();
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));){
            if (response.statusCode() != 200) {
                throw new IOException(OllamaApi.errorMessage(OllamaApi.readAll(reader), response.statusCode()));
            }
            while (true) {
                String line;
                if ((line = reader.readLine()) != null) {
                    if (cancelled.getAsBoolean()) {
                        throw new InterruptedIOException("Cancelled");
                    }
                    if (line.isBlank()) continue;
                    JsonObject event = JsonParser.parseString((String)line).getAsJsonObject();
                    if (event.has("error")) {
                        throw new IOException(event.get("error").getAsString());
                    }
                    String status = OllamaApi.string(event, "status");
                    if (event.has("digest") && event.has("total")) {
                        long total = event.get("total").getAsLong();
                        long completed = event.has("completed") ? event.get("completed").getAsLong() : 0L;
                        layers.put(event.get("digest").getAsString(), new long[]{completed, total});
                    }
                    long done = 0L;
                    long total = 0L;
                    for (long[] layer : layers.values()) {
                        done += layer[0];
                        total += layer[1];
                    }
                    listener.onProgress(status, done, total);
                    if ("success".equals(status)) {
                        return;
                    }
                }
                break;
            }
        }
        throw new IOException("Ollama closed the download before it finished.");
    }

    public static void warmUp(String baseUrl, String model, int numCtx, String keepAlive) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("model", model);
        body.addProperty("keep_alive", keepAlive);
        JsonObject options = new JsonObject();
        options.addProperty("num_ctx", (Number)numCtx);
        body.add("options", (JsonElement)options);
        OllamaApi.sendJson(OllamaApi.post(baseUrl, "/api/generate", body, Duration.ofMinutes(3L)));
    }

    public static void unload(String baseUrl, String model) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("model", model);
        body.addProperty("keep_alive", (Number)0);
        OllamaApi.sendJson(OllamaApi.post(baseUrl, "/api/generate", body, Duration.ofSeconds(30L)));
    }

    public static void delete(String baseUrl, String model) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("model", model);
        OllamaApi.sendJson(OllamaApi.request(baseUrl, "/api/delete", "DELETE", body, Duration.ofSeconds(60L)));
    }

    private static HttpRequest get(String baseUrl, String path, Duration timeout) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(OllamaApi.normalize(baseUrl) + path)).GET();
        if (timeout != null) {
            builder.timeout(timeout);
        }
        return builder.build();
    }

    private static HttpRequest post(String baseUrl, String path, JsonObject body, Duration timeout) {
        return OllamaApi.request(baseUrl, path, "POST", body, timeout);
    }

    private static HttpRequest request(String baseUrl, String path, String method, JsonObject body, Duration timeout) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(OllamaApi.normalize(baseUrl) + path)).header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString(GSON.toJson((JsonElement)body), StandardCharsets.UTF_8));
        if (timeout != null) {
            builder.timeout(timeout);
        }
        return builder.build();
    }

    private static JsonObject sendJson(HttpRequest request) throws IOException {
        try {
            HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(OllamaApi.errorMessage(response.body(), response.statusCode()));
            }
            String text = response.body();
            if (text == null || text.isBlank()) {
                return new JsonObject();
            }
            JsonElement parsed = JsonParser.parseString((String)text);
            return parsed.isJsonObject() ? parsed.getAsJsonObject() : new JsonObject();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("Interrupted");
        }
        catch (JsonParseException | IllegalStateException e) {
            throw new IOException("Ollama sent a response Verity could not read: " + e.getMessage(), e);
        }
    }

    private static String errorMessage(String body, int status) {
        try {
            JsonObject json = JsonParser.parseString((String)body).getAsJsonObject();
            if (json.has("error")) {
                return json.get("error").getAsString();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "Ollama answered HTTP " + status;
    }

    private static String readAll(BufferedReader reader) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static String string(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : "";
    }

    public record InstalledModel(String name, long sizeBytes, String parameterSize, String family, String quantization) {
    }

    public static interface PullListener {
        public void onProgress(String var1, long var2, long var4);
    }
}

