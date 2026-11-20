/*
 * Ported to NeoForge 1.21.1 from Verity 6.0.0 (Forge 1.20.1).
 * Tracks how long a player keeps Verity alone and lowers karma / plays dialogue
 * when the player returns. Forge TickEvent.PlayerTickEvent -> PlayerTickEvent.Post;
 * all SRG names remapped to Mojang names.
 */
package varmite.verity.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "verity", bus = EventBusSubscriber.Bus.GAME)
public class VerityAbandonmentTracker {
    private static final Map<UUID, Integer> ALONE_TICKS = new HashMap<>();
    private static final Map<UUID, Boolean> WAS_ABANDONED = new HashMap<>();
    private static final double ABANDON_DISTANCE = 24.0;
    private static final double RETURN_DISTANCE = 8.0;
    private static final int FIVE_MINUTES_TICKS = 6000;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ServerLevel level = (ServerLevel) player.level();
        if (level.isClientSide()) {
            return;
        }
        if (player.tickCount % 20 != 0) {
            return;
        }
        Entity nearestVerity = findNearestVerity(level, player);
        if (nearestVerity == null) {
            return;
        }
        UUID verityId = nearestVerity.getUUID();
        double distance = player.distanceTo(nearestVerity);
        if (distance > ABANDON_DISTANCE) {
            int currentTicks = ALONE_TICKS.getOrDefault(verityId, 0) + 20;
            ALONE_TICKS.put(verityId, currentTicks);
            if (currentTicks >= FIVE_MINUTES_TICKS && currentTicks % FIVE_MINUTES_TICKS == 0) {
                lowerKarma(player, 1);
                WAS_ABANDONED.put(verityId, true);
                triggerAbandonmentSound(level, nearestVerity);
            }
        } else if (distance <= RETURN_DISTANCE) {
            if (WAS_ABANDONED.getOrDefault(verityId, false)) {
                int totalTicks = ALONE_TICKS.getOrDefault(verityId, 0);
                int minutesAlone = Math.max(1, totalTicks / 1200);
                triggerReturnDialogue(player, nearestVerity, minutesAlone);
                ALONE_TICKS.put(verityId, 0);
                WAS_ABANDONED.put(verityId, false);
            } else {
                ALONE_TICKS.put(verityId, 0);
            }
        }
    }

    private static Entity findNearestVerity(ServerLevel level, ServerPlayer player) {
        AABB searchBox = player.getBoundingBox().inflate(96.0);
        List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, searchBox, entity -> {
            String name = entity.getClass().getSimpleName().toLowerCase();
            return name.contains("sphere") || name.contains("verity");
        });
        Entity nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Entity entity : nearbyEntities) {
            double dist = player.distanceTo(entity);
            if (dist < minDistance) {
                minDistance = dist;
                nearest = entity;
            }
        }
        return nearest;
    }

    private static void lowerKarma(ServerPlayer player, int amount) {
        CompoundTag tag = player.getPersistentData();
        int currentKarma = tag.getInt("VerityKarma");
        tag.putInt("VerityKarma", Math.max(-100, currentKarma - amount));
    }

    private static int getKarma(ServerPlayer player) {
        return player.getPersistentData().getInt("VerityKarma");
    }

    private static void triggerAbandonmentSound(ServerLevel level, Entity verity) {
        level.playSound(null, verity.getX(), verity.getY(), verity.getZ(),
                SoundEvents.GHAST_SCREAM, SoundSource.NEUTRAL, 0.4f, 0.5f);
    }

    private static void triggerReturnDialogue(ServerPlayer player, Entity verity, int minutesAlone) {
        int karma = getKarma(player);
        ServerLevel level = (ServerLevel) verity.level();
        level.playSound(null, verity.getX(), verity.getY(), verity.getZ(),
                SoundEvents.VILLAGER_NO, SoundSource.NEUTRAL, 0.8f, 0.7f);
        String speech = karma >= 10
                ? "\u00a7e<Verity> \u00a7f...Where have you been?! You left me alone for " + minutesAlone + " whole minutes! I thought you abandoned me!"
                : (karma >= 0
                    ? "\u00a76<Verity> \u00a7fWhere were you...? You walked away and didn't come back. I was counting the seconds... please don't leave me alone like that."
                    : "\u00a74<Verity> \u00a7c...You left me. Again. For " + minutesAlone + " minutes I sat here in the dark. Don't ever turn your back on me again.");
        player.sendSystemMessage(Component.literal(speech));
    }
}
