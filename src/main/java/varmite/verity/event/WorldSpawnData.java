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
        messageTag.m_128359_("role", role);
        messageTag.m_128359_("content", content);
        this.chatHistory.add((Object)messageTag);
        while (this.chatHistory.size() > 10) {
            this.chatHistory.remove(0);
        }
        this.setDirty();
    }

    public CompoundTag loadTeams(CompoundTag tag) {
        tag.m_128379_("hasSpawnedEntity", this.hasSpawnedEntity);
        tag.m_128350_("verityKarma", this.verityKarma);
        tag.m_128379_("hasSpawnedDemon", this.hasSpawnedDemon);
        tag.m_128379_("hasSpawnedDemonAngered", this.hasSpawnedDemonAngered);
        tag.m_128365_("chatHistory", (Tag)this.chatHistory);
        return tag;
    }

    public static WorldSpawnData get(ServerLevel level) {
        return (WorldSpawnData)level.m_8895_().m_164861_(WorldSpawnData::load, WorldSpawnData::new, "verity_world_data");
    }

    public static WorldSpawnData load(CompoundTag tag) {
        WorldSpawnData data = new WorldSpawnData();
        data.hasSpawnedEntity = tag.m_128471_("hasSpawnedEntity");
        data.hasSpawnedDemon = tag.m_128471_("hasSpawnedDemon");
        data.hasSpawnedDemonAngered = tag.m_128471_("hasSpawnedDemonAngered");
        if (tag.m_128441_("verityKarma")) {
            data.verityKarma = tag.m_128457_("verityKarma");
        }
        if (tag.m_128425_("chatHistory", 9)) {
            data.chatHistory = tag.m_128437_("chatHistory", 10).copy();
        }
        return data;
    }
}

