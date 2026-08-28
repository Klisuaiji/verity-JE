/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.resources.ResourceLocation
 */
package varmite.verity;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetEntityTalkingPacket(int entityId, int durationTicks) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<SetEntityTalkingPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.parse((String)"verity:set_entity_talking"));
    public static final StreamCodec<ByteBuf, SetEntityTalkingPacket> CODEC = StreamCodec.composite((StreamCodec)ByteBufCodecs.VAR_INT, SetEntityTalkingPacket::entityId, (StreamCodec)ByteBufCodecs.VAR_INT, SetEntityTalkingPacket::durationTicks, SetEntityTalkingPacket::new);

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

