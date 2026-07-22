/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
 *  net.neoforged.neoforge.network.registration.PayloadRegistrar
 */
package varmite.verity.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import varmite.verity.network.PlayTtsPayload;

@EventBusSubscriber(modid="verity")
public class ModNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("verity");
        registrar.playToClient(PlayTtsPayload.TYPE, PlayTtsPayload.STREAM_CODEC, PlayTtsPayload::handleData);
    }
}

