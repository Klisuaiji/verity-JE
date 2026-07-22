/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.fml.DistExecutor
 *  net.neoforged.neoforge.network.NetworkEvent$Context
 *  varmite.verity.network.PlayTtsClientHandler
 *  varmite.verity.network.PlayTtsPayload
 */
package varmite.verity.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent;
import varmite.verity.network.PlayTtsClientHandler;

public class PlayTtsPayload {
    private final int entityId;
    private final String text;

    public PlayTtsPayload(int entityId, String text) {
        this.entityId = entityId;
        this.text = text;
    }

    public int entityId() {
        return this.entityId;
    }

    public String text() {
        return this.text;
    }

    public static void encode(PlayTtsPayload payload, FriendlyByteBuf buf) {
        buf.writeInt(payload.entityId);
        buf.m_130070_(payload.text);
    }

    public static PlayTtsPayload decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String text = buf.m_130136_(Short.MAX_VALUE);
        return new PlayTtsPayload(entityId, text);
    }

    public static void handle(PlayTtsPayload payload, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn((Dist)Dist.CLIENT, () -> () -> PlayTtsClientHandler.handlePacket((PlayTtsPayload)payload)));
        ctx.setPacketHandled(true);
    }
}

