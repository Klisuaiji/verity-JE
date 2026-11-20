/*
 * Ported to NeoForge 1.21.1 — SimpleChannel replaced by the payload handler system.
 * Registers all network payloads via RegisterPayloadHandlersEvent (self-subscribing).
 */
package varmite.verity.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "verity")
public class ModNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("verity");
        registrar.playToClient(PlayTtsPayload.TYPE, PlayTtsPayload.STREAM_CODEC, PlayTtsPayload::handleData);
        registrar.playToClient(KarmaSyncS2CPacket.TYPE, KarmaSyncS2CPacket.STREAM_CODEC, KarmaSyncS2CPacket::handleData);
    }
}
