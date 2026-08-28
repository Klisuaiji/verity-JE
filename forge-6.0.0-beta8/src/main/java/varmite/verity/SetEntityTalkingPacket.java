/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 */
package varmite.verity;

import net.minecraft.network.FriendlyByteBuf;

public record SetEntityTalkingPacket(int entityId, int durationTicks) {
    public void encode(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.entityId);
        buffer.m_130130_(this.durationTicks);
    }

    public static SetEntityTalkingPacket decode(FriendlyByteBuf buffer) {
        return new SetEntityTalkingPacket(buffer.m_130242_(), buffer.m_130242_());
    }
}

