/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.client;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.minecraft.world.phys.Vec3;

public class DynamicLightManager {
    private static volatile List<Beam> currentFrameBeams = Collections.emptyList();

    public static List<Beam> getCurrentFrameBeams() {
        return currentFrameBeams;
    }

    public static void updateBeams(List<Beam> newBeams) {
        currentFrameBeams = List.copyOf(newBeams);
    }

    public static class Beam {
        public final Vec3 start;
        public final Vec3 end;
        public final UUID owner;

        public Beam(Vec3 start, Vec3 end, UUID owner) {
            this.start = start;
            this.end = end;
            this.owner = owner;
        }
    }
}

