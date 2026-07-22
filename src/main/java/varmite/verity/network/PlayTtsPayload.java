/*
 * Ported to NeoForge 1.21.1 — SimpleChannel message replaced by a CustomPacketPayload.
 * Client handling is delegated to PlayTtsClientHandler (preserves the 5.7.3 client logic).
 */
package varmite.verity.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import varmite.verity.network.PlayTtsClientHandler;

public record PlayTtsPayload(int entityId, String text) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlayTtsPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("verity", "play_tts"));

    public static final StreamCodec<FriendlyByteBuf, PlayTtsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, PlayTtsPayload::entityId,
                    ByteBufCodecs.STRING_UTF8, PlayTtsPayload::text,
                    PlayTtsPayload::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleData(PlayTtsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> PlayTtsClientHandler.handlePacket(payload));
    }
}
