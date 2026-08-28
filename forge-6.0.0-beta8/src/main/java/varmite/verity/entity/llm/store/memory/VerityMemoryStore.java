/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package varmite.verity.entity.llm.store.memory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VerityMemoryStore {
    private static final String FILE_NAME = "verity_memory.json";
    private final File serverDataDir;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, String> memories = new ConcurrentHashMap<String, String>();

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
            this.memories.forEach((arg_0, arg_1) -> ((JsonObject)root).addProperty(arg_0, arg_1));
            Files.writeString(file.toPath(), (CharSequence)this.gson.toJson((JsonElement)root), new OpenOption[0]);
        }
        catch (IOException e) {
            System.err.println("Failed to save VerityMemoryStore: " + e.getMessage());
        }
    }

    public void load() {
        try {
            File file = new File(this.serverDataDir, FILE_NAME);
            if (!file.exists()) {
                return;
            }
            String json = Files.readString(file.toPath());
            JsonObject root = JsonParser.parseString((String)json).getAsJsonObject();
            root.entrySet().forEach(entry -> this.memories.put((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString()));
        }
        catch (IOException e) {
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
        return new HashSet<String>(this.memories.keySet());
    }

    public Map<String, String> getAllMemories() {
        return new ConcurrentHashMap<String, String>(this.memories);
    }
}

