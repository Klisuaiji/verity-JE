/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.network.NetworkDirection
 *  net.minecraftforge.network.NetworkRegistry$ChannelBuilder
 *  net.minecraftforge.network.PacketDistributor
 *  net.minecraftforge.network.simple.SimpleChannel
 */
package varmite.verity.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import varmite.verity.network.KarmaSyncS2CPacket;

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

