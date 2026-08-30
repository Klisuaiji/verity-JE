package varmite.verity.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import varmite.verity.entity.verity.VerityEntity;

/**
 * Centralised mutable runtime state for Verity (6.1).
 * Replaces the assorted static fields that used to live across ModEvents.
 */
public class VerityState {
    public static boolean hasSpawned = false;
    public static long timeWillSpawn;
    public static VerityEntity verityEntity = null;
    public static final Map<UUID, Long> HURT_COOLDOWN = new HashMap<>();
    public static boolean transformFollowingDay = false;
    public static boolean followPlayer = false;
    public static int idleChatTimer = 3600;
    public static int lonelinessTimer = 3000;
    public static boolean isMonstrous = false;
}
