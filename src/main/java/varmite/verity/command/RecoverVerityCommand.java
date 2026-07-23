/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  varmite.verity.command.RecoverVerityCommand
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.custom.VerityEntity
 */
package varmite.verity.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.VerityEntity;

public class RecoverVerityCommand {
    private static long lastUseTime = 0L;
    private static final long COOLDOWN = 3600000L;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("recoververity").executes(context -> {
            ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
            ServerLevel level = player.serverLevel();
            boolean verityExists = false;
            for (Entity entity : level.getAllEntities()) {
                if (!(entity instanceof VerityEntity)) continue;
                verityExists = true;
                break;
            }
            if (verityExists) {
                player.sendSystemMessage((Component)Component.literal("\u00a7cVerity is already recovered."));
                return 0;
            }
            long now = System.currentTimeMillis();
            long remaining = 3600000L - (now - lastUseTime);
            if (remaining > 0L) {
                long hours = remaining / 3600000L;
                long minutes = remaining % 3600000L / 60000L;
                long seconds = remaining % 60000L / 1000L;
                player.sendSystemMessage((Component)Component.literal(("\u00a7cRecovery is on cooldown. Time remaining: " + hours + "h " + minutes + "m " + seconds + "s")));
                return 0;
            }
            lastUseTime = now;
            VerityEntity verity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create((Level)level);
            if (verity == null) {
                player.sendSystemMessage((Component)Component.literal("\u00a7cFailed to recover Verity."));
                return 0;
            }
            verity.variantArea(player.getX() + 1.5, player.getY(), player.getZ() + 1.5, player.getYRot(), 0.0f);
            level.addFreshEntity((Entity)verity);
            player.sendSystemMessage((Component)Component.literal("\u00a7aVerity has been recovered!"));
            return 1;
        }));
    }
}

