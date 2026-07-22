/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.neoforged.neoforge.network.NetworkDirection
 *  net.neoforged.neoforge.network.NetworkRegistry$ChannelBuilder
 *  net.neoforged.neoforge.network.PacketDistributor
 *  net.neoforged.neoforge.network.simple.SimpleChannel
 *  varmite.verity.network.KarmaSyncS2CPacket
 *  varmite.verity.network.ModMessages
 */
package varmite.verity.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkDirection;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import varmite.verity.network.KarmaSyncS2CPacket;

/*
 * Exception performing whole class analysis ignored.
 */
public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net;
        INSTANCE = net = NetworkRegistry.ChannelBuilder.named((ResourceLocation)new ResourceLocation("verity", "messages")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
        net.messageBuilder(KarmaSyncS2CPacket.class, ModMessages.id(), NetworkDirection.PLAY_TO_CLIENT).decoder(KarmaSyncS2CPacket::new).encoder(KarmaSyncS2CPacket::toBytes).consumerMainThread(KarmaSyncS2CPacket::handle).add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    static {
        packetId = 0;
    }
}

