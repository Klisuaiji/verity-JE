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
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  varmite.verity.entity.custom.VerityEntity
 *  varmite.verity.event.VerityPleadingHandler
 */
package varmite.verity.event;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.entity.custom.VerityEntity;

@Mod.EventBusSubscriber(modid="verity", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class VerityPleadingHandler {
    private static final double PLEAD_DISTANCE = 10.0;

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().getString().toLowerCase();
        List verities = player.serverLevel().getEntities(VerityEntity.class, new AABB(player.blockPosition()).inflate(60.0));
        for (VerityEntity verity : verities) {
            if (!verity.isMonstrous()) continue;
            if ((double)player.setId((Entity)verity) <= 10.0) {
                if (verity.hasPlayerLooked(player.getUUID())) {
                    boolean pleadedSuccessfully = false;
                    if (message.contains("i came back for you")) {
                        player.sendSystemMessage((Component)Component.getContents((String)"\u00a7aYou remind Verity of your bond. He calms down."));
                        pleadedSuccessfully = true;
                    } else if (message.contains("i'm sorry") || message.contains("i am sorry") || message.contains("please forgive me")) {
                        int currentApologies = verity.getApologyCount();
                        verity.setApologyCount(currentApologies + 1);
                        if (verity.getApologyCount() >= 3) {
                            player.sendSystemMessage((Component)Component.getContents((String)"\u00a7aYour repeated apologies finally reach Verity. He calms down."));
                            pleadedSuccessfully = true;
                        } else {
                            player.sendSystemMessage((Component)Component.getContents((String)("\u00a7eVerity seems to hear you, but remains agitated. (" + verity.getApologyCount() + "/3 apologies)")));
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
                player.sendSystemMessage((Component)Component.getContents((String)"\u00a7cYou must look at Verity to get his attention!"));
                continue;
            }
            player.sendSystemMessage((Component)Component.getContents((String)"\u00a7cYou need to be closer to Verity to plead with him."));
        }
    }
}

