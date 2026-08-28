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
 *  net.minecraft.world.level.Level
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
import net.minecraft.world.level.Level;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.llm.AiManager;
import varmite.verity.entity.llm.store.chat.ChatMemoryManager;
import varmite.verity.entity.verity.VerityEntity;

public class RecoverVerityCommand {
    private static long lastUseTime = 0L;
    private static final long COOLDOWN = 3600000L;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_((String)"recoververity").executes(context -> {
            ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
            ServerLevel level = player.m_284548_();
            boolean verityExists = false;
            for (Entity entity : level.m_8583_()) {
                if (!(entity instanceof VerityEntity)) continue;
                verityExists = true;
                break;
            }
            if (verityExists) {
                player.m_213846_((Component)Component.m_237113_((String)"\u00a7cVerity is already recovered."));
                return 0;
            }
            long now = System.currentTimeMillis();
            long remaining = 3600000L - (now - lastUseTime);
            if (remaining > 0L) {
                long hours = remaining / 3600000L;
                long minutes = remaining % 3600000L / 60000L;
                long seconds = remaining % 60000L / 1000L;
                player.m_213846_((Component)Component.m_237113_((String)("\u00a7cRecovery is on cooldown. Time remaining: " + hours + "h " + minutes + "m " + seconds + "s")));
                return 0;
            }
            lastUseTime = now;
            VerityEntity verity = (VerityEntity)ModEntities.VERITY_ENTITY.get().m_20615_((Level)level);
            if (verity == null) {
                player.m_213846_((Component)Component.m_237113_((String)"\u00a7cFailed to recover Verity."));
                return 0;
            }
            verity.m_7678_(player.m_20185_() + 1.5, player.m_20186_(), player.m_20189_() + 1.5, player.m_146908_(), 0.0f);
            level.m_7967_((Entity)verity);
            player.m_213846_((Component)Component.m_237113_((String)"\u00a7aVerity has been recovered!"));
            return 1;
        }));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_((String)"clear_history").executes(context -> {
            ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
            ChatMemoryManager.getGlobalStore().clearMessages();
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Chat history cleared."), false);
            return 1;
        }));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_((String)"trigger_random").executes(context -> {
            ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
            VerityState.verityEntity.startTalking(80);
            String idlePrompt = "<SYSTEM> You decide to start a random conversation with the user. Ask them a question, comment the environment or even give random facts.\n";
            AiManager.queryAI(VerityState.verityEntity, idlePrompt, player);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Command ran."), false);
            return 1;
        }));
    }
}

