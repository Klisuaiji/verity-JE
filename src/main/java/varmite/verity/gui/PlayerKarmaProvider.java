/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.neoforged.neoforge.common.capabilities.Capability
 *  net.neoforged.neoforge.common.capabilities.CapabilityManager
 *  net.neoforged.neoforge.common.capabilities.CapabilityToken
 *  net.neoforged.neoforge.common.capabilities.ICapabilityProvider
 *  net.neoforged.neoforge.common.util.INBTSerializable
 *  net.neoforged.neoforge.common.util.LazyOptional
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  varmite.verity.gui.PlayerKarma
 *  varmite.verity.gui.PlayerKarmaProvider
 */
package varmite.verity.gui;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.CapabilityManager;
import net.neoforged.neoforge.common.capabilities.CapabilityToken;
import net.neoforged.neoforge.common.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import varmite.verity.gui.PlayerKarma;

public class PlayerKarmaProvider
implements ICapabilityProvider,
INBTSerializable<CompoundTag> {
    public static Capability<PlayerKarma> PLAYER_KARMA = CapabilityManager.get((CapabilityToken)new /* Unavailable Anonymous Inner Class!! */);
    private PlayerKarma karma = null;
    private final LazyOptional<PlayerKarma> optional = LazyOptional.of(() -> this.createPlayerKarma());

    private PlayerKarma createPlayerKarma() {
        if (this.karma == null) {
            this.karma = new PlayerKarma();
        }
        return this.karma;
    }

    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_KARMA) {
            return this.optional.cast();
        }
        return LazyOptional.empty();
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        this.createPlayerKarma().saveNBTData(nbt);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.createPlayerKarma().loadNBTData(nbt);
    }
}

