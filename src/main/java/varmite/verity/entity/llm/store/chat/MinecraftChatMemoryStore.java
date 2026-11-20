/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 * langchain4j ChatMemoryStore backed by the world save directory.
 */
package varmite.verity.entity.llm.store.chat;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;

public class MinecraftChatMemoryStore implements ChatMemoryStore {
    private final ChatMemoryStorage storage;

    public MinecraftChatMemoryStore(ServerLevel level) {
        this.storage = new ChatMemoryStorage(
                level.getServer().getWorldPath(LevelResource.ROOT).resolve("data").toFile());
    }

    public MinecraftChatMemoryStore(ChatMemoryStorage serverStorage) {
        this.storage = serverStorage;
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String json = this.storage.getMessagesJson(memoryId.toString());
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String json = ChatMessageSerializer.messagesToJson(messages);
        this.storage.updateMessagesJson(memoryId.toString(), json);
    }

    public void clearMessages() {
        this.storage.updateMessagesJson("verity-chat-memory", "[]");
    }

    @Override
    public void deleteMessages(Object memoryId) {
        this.storage.deleteMessagesJson(memoryId.toString());
    }
}
