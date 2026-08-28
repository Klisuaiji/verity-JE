/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.storage.LevelResource
 */
package varmite.verity.entity.llm.store.chat;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import varmite.verity.entity.llm.store.chat.ChatMemoryStorage;
import varmite.verity.entity.llm.store.chat.MinecraftChatMemoryStore;

public class ChatMemoryManager {
    private static ChatMemoryStorage GLOBAL_STORAGE;

    public static void init(MinecraftServer server) {
        GLOBAL_STORAGE = new ChatMemoryStorage(server.m_129843_(LevelResource.f_78182_).toFile());
    }

    public static ChatMemoryStorage getGlobalStorage() {
        if (GLOBAL_STORAGE == null) {
            throw new IllegalStateException("ChatMemoryManager not initialized! Call init() on ServerStartingEvent");
        }
        return GLOBAL_STORAGE;
    }

    public static MinecraftChatMemoryStore getGlobalStore() {
        return new MinecraftChatMemoryStore(ChatMemoryManager.getGlobalStorage());
    }
}

