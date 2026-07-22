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
import java.util.concurrent.atomic.AtomicReference;
import varmite.verity.client.DynamicLightManager;

public class DynamicLightManager {
    private static final AtomicReference<List<Beam>> CURRENT = new AtomicReference(Collections.emptyList());

    public static void updateBeams(List<Beam> newBeams) {
        CURRENT.set(newBeams == null ? Collections.emptyList() : List.copyOf(newBeams));
    }
}

