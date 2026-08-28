/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package varmite.verity.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import varmite.verity.client.audio.TTSHandler;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.network.PlayTtsPayload;

public class PlayTtsClientHandler {
    public static void handlePacket(PlayTtsPayload payload) {
        VerityEntity v;
        VerityEntity verityMob;
        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        if (localPlayer == null) {
            System.out.println("[VERITY DEBUG] Client received payload, but localPlayer is null!");
            return;
        }
        System.out.println("[VERITY DEBUG] Client successfully received TTS Payload: " + payload.text());
        Entity entity = localPlayer.m_9236_().m_6815_(payload.entityId());
        VerityEntity verityEntity = verityMob = entity instanceof VerityEntity ? (v = (VerityEntity)entity) : null;
        if (verityMob == null) {
            System.out.println("[VERITY DEBUG] Verity not found physically in world. Playing as 'Voice in Head'.");
        }
        TTSHandler.playTTS(payload.text(), (Player)localPlayer, verityMob);
    }
}

