/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  varmite.verity.client.DynamicLightManager
 *  varmite.verity.client.DynamicLightManager$Beam
 */
package varmite.verity.client;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.world.phys.Vec3;
import varmite.verity.client.DynamicLightManager;

public class DynamicLightManager {
    private static final AtomicReference<List<Beam>> CURRENT = new AtomicReference(Collections.emptyList());

    public static void updateBeams(List<Beam> newBeams) {
        CURRENT.set(newBeams == null ? Collections.emptyList() : List.copyOf(newBeams));
    }

    public static List<Beam> getCurrentFrameBeams() {
        return CURRENT.get();
    }

    public static class Beam {
        public final Vec3 start;
        public final Vec3 end;
        public final UUID uuid;

        public Beam(Vec3 start, Vec3 end, UUID uuid) {
            this.start = start;
            this.end = end;
            this.uuid = uuid;
        }
    }
}

