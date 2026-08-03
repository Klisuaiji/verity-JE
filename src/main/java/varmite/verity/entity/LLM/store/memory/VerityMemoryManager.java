/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 */
package varmite.verity.entity.LLM.store.memory;

import java.io.File;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

public class VerityMemoryManager {
    private static VerityMemoryStore INSTANCE;

    public static void init(MinecraftServer server) {
        File serverDir = server.getWorldPath(LevelResource.ROOT).toFile();
        INSTANCE = new VerityMemoryStore(serverDir);
    }

    public static VerityMemoryStore get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ServerMemoryManager not initialized!");
        }
        return INSTANCE;
    }
}
