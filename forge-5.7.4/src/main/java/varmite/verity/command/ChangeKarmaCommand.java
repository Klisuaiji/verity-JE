/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 */
package varmite.verity.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import varmite.verity.event.ModEvents;

public class ChangeKarmaCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"changekarma").requires(source -> source.m_6761_(2))).then(Commands.m_82129_((String)"karma", (ArgumentType)IntegerArgumentType.integer((int)0, (int)20)).executes(context -> {
            ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
            ServerLevel serverLevel = player.m_284548_();
            serverLevel.m_7654_().execute(() -> ModEvents.setAndSyncKarma(serverLevel, IntegerArgumentType.getInteger((CommandContext)context, (String)"karma")));
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Karma updated to " + IntegerArgumentType.getInteger((CommandContext)context, (String)"karma") + ".")), false);
            return 1;
        })));
    }
}

