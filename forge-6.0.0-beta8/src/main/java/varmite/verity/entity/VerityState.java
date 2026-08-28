/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import varmite.verity.entity.verity.VerityEntity;

public class VerityState {
    public static boolean hasSpawned = false;
    public static long timeWillSpawn;
    public static VerityEntity verityEntity;
    public static final Map<UUID, Long> HURT_COOLDOWN;
    public static boolean transformFollowingDay;
    public static boolean followPlayer;
    public static int idleChatTimer;
    public static int lonelinessTimer;
    public static boolean isClientTalking;

    static {
        verityEntity = null;
        HURT_COOLDOWN = new HashMap<UUID, Long>();
        transformFollowingDay = false;
        followPlayer = false;
        idleChatTimer = 3600;
        lonelinessTimer = 3000;
    }
}

