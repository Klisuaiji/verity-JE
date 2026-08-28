/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package varmite.verity.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.custom.VerityEntity;

public record PlayTtsPayload(int entityId, String text) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PlayTtsPayload> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"play_tts"));
    public static final StreamCodec<FriendlyByteBuf, PlayTtsPayload> STREAM_CODEC = StreamCodec.composite((StreamCodec)ByteBufCodecs.INT, PlayTtsPayload::entityId, (StreamCodec)ByteBufCodecs.STRING_UTF8, PlayTtsPayload::text, PlayTtsPayload::new);

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleData(PlayTtsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            VerityEntity v;
            VerityEntity verityMob;
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) {
                System.out.println("[VERITY DEBUG] Client received payload, but localPlayer is null!");
                return;
            }
            System.out.println("[VERITY DEBUG] Client successfully received TTS Payload: " + payload.text());
            Entity entity = localPlayer.level().getEntity(payload.entityId());
            VerityEntity verityEntity = verityMob = entity instanceof VerityEntity ? (v = (VerityEntity)entity) : null;
            if (verityMob == null) {
                System.out.println("[VERITY DEBUG] Verity not found physically in world. Playing as 'Voice in Head'.");
            }
            AiAPI.playTTS(payload.text(), (Player)localPlayer, verityMob);
        });
    }
}

