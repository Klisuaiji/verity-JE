/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 * Async entry point that drives a single Verity conversation turn.
 */
package varmite.verity.entity.LLM;

import java.util.concurrent.CompletableFuture;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.network.PlayTtsPayload;

public class AiManager {

    public static void queryAI(VerityEntity verity, String query, ServerPlayer player) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServerLevel level = (ServerLevel) verity.level();
        long currentDay = level.getDayTime() / 24000L;
        float currentKarma = WorldSpawnData.get(level).verityKarma;

        CompletableFuture.supplyAsync(() -> {
                    // langchain4j resolves SPI providers from the TCCL; Minecraft's worker
                    // threads do not inherit the mod class loader, so restore it here.
                    Thread.currentThread().setContextClassLoader(classLoader);
                    return AiAPI.ask(verity, player, query, currentDay, currentKarma);
                })
                .whenComplete((answer, throwable) -> {
                    if (throwable != null) {
                        System.err.println("Error in AiAPI.ask: " + throwable.getMessage());
                        throwable.printStackTrace();
                    } else {
                        System.out.println("AiAPI response: " + answer);
                    }
                })
                .thenAccept(answer -> {
                    MinecraftServer server = verity.getServer();
                    if (server == null) {
                        return;
                    }
                    server.execute(() -> {
                        if (answer == null || answer.isBlank()) {
                            if (!verity.isRemoved()) {
                                verity.stopTalking();
                            }
                            return;
                        }
                        String reply = answer.length() > 1500 ? answer.substring(0, 1500) + "..." : answer;
                        if (!verity.isRemoved()) {
                            PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                                    verity, new PlayTtsPayload(verity.getId(), reply));
                        }
                        // 6.1 dropped the talk advancement trigger; keep it so the
                        // ported advancement tree still works.
                        if (player != null) {
                            ModEvents.send(player, reply);
                        } else if (!VerityConfig.IMMERSIVE_MODE.get()) {
                            server.getPlayerList().broadcastSystemMessage(
                                    Component.literal("<" + VerityConfig.VERITY_CUSTOM_NAME.get() + "> " + reply),
                                    false);
                        }
                    });
                });
    }
}
