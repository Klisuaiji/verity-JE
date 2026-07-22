/*
 * Ported to NeoForge 1.21.1 — implements INBTSerializable so it can back an AttachmentType.
 */
package varmite.verity.gui;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class PlayerKarma implements INBTSerializable<CompoundTag> {
    private int karma;
    private final int MIN_KARMA = 0;
    private final int MAX_KARMA = 20;

    public int getKarma() {
        return this.karma;
    }

    public void addKarma(int amount) {
        this.karma = Math.min(this.karma + amount, 20);
    }

    public void subKarma(int amount) {
        this.karma = Math.max(this.karma - amount, 0);
    }

    public void setKarma(int amount) {
        this.karma = Math.max(0, Math.min(amount, 20));
    }

    public void copyFrom(PlayerKarma source) {
        this.karma = source.karma;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("player_karma", this.karma);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.karma = nbt.getInt("player_karma");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        this.saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.loadNBTData(nbt);
    }
}
