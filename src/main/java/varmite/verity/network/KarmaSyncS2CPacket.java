/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.neoforged.neoforge.network.NetworkEvent$Context
 *  varmite.verity.network.ClientKarmaData
 *  varmite.verity.network.KarmaSyncS2CPacket
 */
package varmite.verity.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import varmite.verity.network.ClientKarmaData;

public class KarmaSyncS2CPacket {
    private final int karma;

    public KarmaSyncS2CPacket(int karma) {
        this.karma = karma;
    }

    public KarmaSyncS2CPacket(FriendlyByteBuf buf) {
        this.karma = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.karma);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientKarmaData.set((int)this.karma));
        return true;
    }
}

