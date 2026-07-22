/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.neoforged.neoforge.network.NetworkRegistry
 *  net.neoforged.neoforge.network.simple.SimpleChannel
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.network.PlayTtsPayload
 */
package varmite.verity.network;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import varmite.verity.network.PlayTtsPayload;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel((ResourceLocation)new ResourceLocation("verity", "main"), () -> "1", "1"::equals, "1"::equals);
    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(packetId++, PlayTtsPayload.class, PlayTtsPayload::encode, PlayTtsPayload::decode, PlayTtsPayload::handle);
    }
}

