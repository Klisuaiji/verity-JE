/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.saveddata.SavedData
 */
package varmite.verity.event;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class WorldSpawnData
extends SavedData {
    public float verityKarma = 0.0f;
    public boolean hasSpawnedEntity = false;
    public boolean hasProvokedSpawn = false;
    public boolean hasHostileDaySpawn = false;
    public UUID activeDemon = null;
    public final ListTag chatHistory = new ListTag();

    public boolean hasLiveDemon() {
        return this.activeDemon != null;
    }

    public void setActiveDemon(UUID demon) {
        this.activeDemon = demon;
        this.m_77762_();
    }

    public void clearActiveDemon(UUID demon) {
        if (demon.equals(this.activeDemon)) {
            this.activeDemon = null;
            this.m_77762_();
        }
    }

    public CompoundTag m_7176_(CompoundTag tag) {
        tag.m_128379_("hasSpawnedEntity", this.hasSpawnedEntity);
        tag.m_128350_("verityKarma", this.verityKarma);
        tag.m_128379_("hasProvokedSpawn", this.hasProvokedSpawn);
        tag.m_128379_("hasHostileDaySpawn", this.hasHostileDaySpawn);
        if (this.activeDemon != null) {
            tag.m_128362_("activeDemon", this.activeDemon);
        }
        return tag;
    }

    public static WorldSpawnData get(ServerLevel level) {
        return (WorldSpawnData)level.m_7654_().m_129783_().m_8895_().m_164861_(WorldSpawnData::load, WorldSpawnData::new, "verity_world_data");
    }

    public static WorldSpawnData load(CompoundTag tag) {
        WorldSpawnData data = new WorldSpawnData();
        data.hasSpawnedEntity = tag.m_128471_("hasSpawnedEntity");
        data.hasProvokedSpawn = tag.m_128471_("hasProvokedSpawn");
        data.hasHostileDaySpawn = tag.m_128471_("hasHostileDaySpawn");
        if (tag.m_128403_("activeDemon")) {
            data.activeDemon = tag.m_128342_("activeDemon");
        }
        if (tag.m_128441_("verityKarma")) {
            data.verityKarma = tag.m_128457_("verityKarma");
        }
        return data;
    }
}

