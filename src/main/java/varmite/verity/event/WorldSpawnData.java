/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.saveddata.SavedData
 *  varmite.verity.event.WorldSpawnData
 */
package varmite.verity.event;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class WorldSpawnData
extends SavedData {
    public float verityKarma = 0.0f;
    public boolean hasSpawnedEntity = false;
    public boolean hasSpawnedDemon = false;
    public boolean hasSpawnedDemonAngered = false;
    public ListTag chatHistory = new ListTag();

    public void addMessageToHistory(String role, String content) {
        CompoundTag messageTag = new CompoundTag();
        messageTag.putString("role", role);
        messageTag.putString("content", content);
        this.chatHistory.add(messageTag);
        while (this.chatHistory.size() > 10) {
            this.chatHistory.remove(0);
        }
        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putBoolean("hasSpawnedEntity", this.hasSpawnedEntity);
        tag.putFloat("verityKarma", this.verityKarma);
        tag.putBoolean("hasSpawnedDemon", this.hasSpawnedDemon);
        tag.putBoolean("hasSpawnedDemonAngered", this.hasSpawnedDemonAngered);
        tag.put("chatHistory", this.chatHistory);
        return tag;
    }

    public static WorldSpawnData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(WorldSpawnData::new, WorldSpawnData::load), "verity_world_data");
    }

    public static WorldSpawnData load(CompoundTag tag, HolderLookup.Provider provider) {
        WorldSpawnData data = new WorldSpawnData();
        data.hasSpawnedEntity = tag.getBoolean("hasSpawnedEntity");
        data.hasSpawnedDemon = tag.getBoolean("hasSpawnedDemon");
        data.hasSpawnedDemonAngered = tag.getBoolean("hasSpawnedDemonAngered");
        if (tag.contains("verityKarma")) {
            data.verityKarma = tag.getFloat("verityKarma");
        }
        if (tag.contains("chatHistory", 9)) {
            data.chatHistory = tag.getList("chatHistory", 10).copy();
        }
        return data;
    }
}

