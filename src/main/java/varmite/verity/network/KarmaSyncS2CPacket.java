/*
 * Ported to NeoForge 1.21.1 — SimpleChannel message replaced by a CustomPacketPayload.
 */
package varmite.verity.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import varmite.verity.network.ClientKarmaData;

public record KarmaSyncS2CPacket(int karma) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KarmaSyncS2CPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("verity", "karma_sync"));

    public static final StreamCodec<FriendlyByteBuf, KarmaSyncS2CPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    KarmaSyncS2CPacket::karma,
                    KarmaSyncS2CPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleData(KarmaSyncS2CPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientKarmaData.set(payload.karma()));
    }
}
