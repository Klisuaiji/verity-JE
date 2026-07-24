/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.AABB
 *  net.neoforged.neoforge.event.ServerChatEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.common.EventBusSubscriber
 *  varmite.verity.entity.custom.VerityEntity
 *  varmite.verity.event.VerityPleadingHandler
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.entity.custom.VerityEntity;

@EventBusSubscriber(modid="verity", bus=EventBusSubscriber.Bus.GAME)
public class VerityPleadingHandler {
    private static final double PLEAD_DISTANCE = 10.0;

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().getString().toLowerCase();
        List<VerityEntity> verities = player.serverLevel().getEntities(EntityTypeTest.forClass(VerityEntity.class), new AABB(player.blockPosition()).inflate(60.0), e -> true);
        for (VerityEntity verity : verities) {
            if (!verity.isMonstrous()) continue;
            if ((double)player.distanceTo((Entity)verity) <= 10.0) {
                if (verity.hasPlayerLooked(player.getUUID())) {
                    boolean pleadedSuccessfully = false;
                    if (message.contains("i came back for you")) {
                        player.sendSystemMessage((Component)Component.translatable("verity.msg.plead_bond"));
                        pleadedSuccessfully = true;
                    } else if (message.contains("i'm sorry") || message.contains("i am sorry") || message.contains("please forgive me")) {
                        int currentApologies = verity.getApologyCount();
                        verity.setApologyCount(currentApologies + 1);
                        if (verity.getApologyCount() >= 3) {
                            player.sendSystemMessage((Component)Component.translatable("verity.msg.plead_apology"));
                            pleadedSuccessfully = true;
                        } else {
                            player.sendSystemMessage((Component)Component.translatable("verity.msg.plead_agitated", verity.getApologyCount()));
                        }
                    }
                    if (!pleadedSuccessfully) continue;
                    verity.setMonstrous(false);
                    verity.setApologyCount(0);
                    verity.clearPlayersWhoLooked();
                    verity.setVariant("happy");
                    verity.pleadingTimer = -1;
                    event.setCanceled(true);
                    return;
                }
                player.sendSystemMessage((Component)Component.translatable("verity.msg.plead_look"));
                continue;
            }
            player.sendSystemMessage((Component)Component.translatable("verity.msg.plead_closer"));
        }
    }
}

