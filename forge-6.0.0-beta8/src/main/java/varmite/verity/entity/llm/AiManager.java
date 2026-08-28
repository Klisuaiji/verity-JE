/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.network.PacketDistributor
 */
package varmite.verity.entity.llm;

import java.util.concurrent.CompletableFuture;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import varmite.verity.Verity;
import varmite.verity.VerityConfig;
import varmite.verity.entity.llm.AiAPI;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.environment.items.ModItems;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;

public class AiManager {
    public static void queryAI(VerityEntity verity, String query, ServerPlayer Player2) {
        ServerLevel level;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object object = verity != null ? (ServerLevel)verity.m_9236_() : (level = Player2 != null ? Player2.m_284548_() : null);
        if (level == null) {
            return;
        }
        MinecraftServer server = level.m_7654_();
        WorldSpawnData worldData = WorldSpawnData.get(level);
        long currentDay = level.m_46468_() / 24000L;
        float currentKarma = worldData.verityKarma;
        boolean demonLoose = worldData.hasLiveDemon();
        ((CompletableFuture)CompletableFuture.supplyAsync(() -> {
            Thread.currentThread().setContextClassLoader(classLoader);
            return AiAPI.ask(verity, Player2, query, currentDay, currentKarma, demonLoose);
        }).handle((answer, throwable) -> {
            if (throwable == null) {
                return answer;
            }
            Verity.LOGGER.error("Verity's reply to \"{}\" failed", (Object)query, throwable);
            String detail = throwable.getMessage() != null ? throwable.getMessage() : throwable.getClass().getSimpleName();
            return "ERROR: " + detail;
        })).thenAccept(answer -> server.execute(() -> {
            if (verity != null && !verity.m_213877_()) {
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verity), (Object)new PlayTtsPayload(verity.m_19879_(), (String)answer));
            } else if (Player2 != null) {
                ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> Player2), (Object)new PlayTtsPayload(Player2.m_19879_(), (String)answer));
            }
            server.m_6846_().m_240416_((Component)Component.m_237113_((String)("<" + (String)VerityConfig.VERITY_CUSTOM_NAME.get() + "> " + answer)), false);
        }));
    }

    private static boolean isVerityInUpperInventory(ServerPlayer player) {
        ItemStack stack;
        int i;
        if (player == null) {
            return false;
        }
        for (i = 0; i <= 8 && i < player.m_150109_().f_35974_.size(); ++i) {
            stack = (ItemStack)player.m_150109_().f_35974_.get(i);
            if (stack.m_41619_() || !stack.m_150930_((Item)ModItems.VERITY_ITEM.get())) continue;
            return false;
        }
        if (!player.m_21206_().m_41619_() && player.m_21206_().m_150930_((Item)ModItems.VERITY_ITEM.get())) {
            return false;
        }
        for (i = 9; i < player.m_150109_().f_35974_.size(); ++i) {
            stack = (ItemStack)player.m_150109_().f_35974_.get(i);
            if (stack.m_41619_() || !stack.m_150930_((Item)ModItems.VERITY_ITEM.get())) continue;
            return true;
        }
        return false;
    }
}

