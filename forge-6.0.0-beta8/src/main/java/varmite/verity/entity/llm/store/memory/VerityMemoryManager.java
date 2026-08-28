/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.storage.LevelResource
 */
package varmite.verity.entity.llm.store.memory;

import java.io.File;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import varmite.verity.entity.llm.store.memory.VerityMemoryStore;

public class VerityMemoryManager {
    private static VerityMemoryStore INSTANCE;

    public static void init(MinecraftServer server) {
        File serverDir = server.m_129843_(LevelResource.f_78182_).toFile();
        INSTANCE = new VerityMemoryStore(serverDir);
    }

    public static VerityMemoryStore get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ServerMemoryManager not initialized!");
        }
        return INSTANCE;
    }
}

