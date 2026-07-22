/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  varmite.verity.SetEntityTalkingPacket
 */
package varmite.verity;

import net.minecraft.network.FriendlyByteBuf;

public class SetEntityTalkingPacket {
    private final int entityId;
    private final int durationTicks;

    public SetEntityTalkingPacket(int entityId, int durationTicks) {
        this.entityId = entityId;
        this.durationTicks = durationTicks;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.entityId);
        buffer.writeVarInt(this.durationTicks);
    }

    public static SetEntityTalkingPacket decode(FriendlyByteBuf buffer) {
        return new SetEntityTalkingPacket(buffer.readVarInt(), buffer.readVarInt());
    }

    public int getEntityId() {
        return this.entityId;
    }

    public int getDurationTicks() {
        return this.durationTicks;
    }
}

