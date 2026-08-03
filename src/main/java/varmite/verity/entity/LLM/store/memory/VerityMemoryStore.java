/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 * Long-term key/value "facts" Verity remembers about the world and the player.
 */
package varmite.verity.entity.LLM.store.memory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VerityMemoryStore {
    private static final String FILE_NAME = "verity_memory.json";

    private final File serverDataDir;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, String> memories = new ConcurrentHashMap<>();

    public VerityMemoryStore(File serverDir) {
        this.serverDataDir = serverDir;
        if (!this.serverDataDir.exists()) {
            this.serverDataDir.mkdirs();
        }
        this.load();
    }

    public void save() {
        try {
            File file = new File(this.serverDataDir, FILE_NAME);
            JsonObject root = new JsonObject();
            this.memories.forEach(root::addProperty);
            Files.write(file.toPath(), this.gson.toJson(root).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Failed to save VerityMemoryStore: " + e.getMessage());
        }
    }

    public void load() {
        try {
            File file = new File(this.serverDataDir, FILE_NAME);
            if (!file.exists()) {
                return;
            }
            String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            root.entrySet().forEach(entry -> this.memories.put(entry.getKey(), entry.getValue().getAsString()));
        } catch (IOException e) {
            System.err.println("Failed to load VerityMemoryStore: " + e.getMessage());
        }
    }

    public void addMemory(String id, String text) {
        this.memories.put(id, text);
        this.save();
    }

    public String getMemory(String id) {
        return this.memories.get(id);
    }

    public void removeMemory(String id) {
        this.memories.remove(id);
        this.save();
    }

    public Set<String> getAllKeys() {
        return new HashSet<>(this.memories.keySet());
    }

    public Map<String, String> getAllMemories() {
        return new ConcurrentHashMap<>(this.memories);
    }
}
