/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package varmite.verity.network;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.network.PlayTtsPayload;

public class PlayTtsHandler {
    public static void handle(PlayTtsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null || player.level() == null) {
                return;
            }
            Entity entity = player.level().getEntity(payload.entityId());
            if (entity instanceof VerityEntity) {
                VerityEntity verity = (VerityEntity)entity;
                AiAPI.playTTS(payload.text(), player, verity);
            }
        });
    }
}

