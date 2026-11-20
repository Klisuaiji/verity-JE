/*
 * Ported to NeoForge 1.21.1 from Verity 6.0.0 (Forge 1.20.1).
 * /verityface <face> — changes the face variant of the nearest Verity entity.
 * SRG names remapped to Mojang names using ChangeKarmaCommand as the template.
 */
package varmite.verity.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import varmite.verity.entity.verity.VerityEntity;

public class VerityFaceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("verityface")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("face", StringArgumentType.word())
                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                VerityEntity.VALID_VARIANTS, (SuggestionsBuilder) builder))
                        .executes(context -> {
                            ServerPlayer player = ((CommandSourceStack) context.getSource()).getPlayerOrException();
                            ServerLevel level = player.serverLevel();
                            String face = StringArgumentType.getString(context, "face");
                            if (!VerityEntity.VALID_VARIANTS.contains(face)) {
                                ((CommandSourceStack) context.getSource()).sendFailure(Component.literal(
                                        "Unknown face '" + face + "'. Faces: " + String.join(", ", VerityEntity.VALID_VARIANTS)));
                                return 0;
                            }
                            VerityEntity nearest = null;
                            for (VerityEntity verity : level.getEntitiesOfClass(VerityEntity.class,
                                    player.getBoundingBox().inflate(128.0))) {
                                if (nearest != null && !(verity.distanceTo(player) < nearest.distanceTo(player))) {
                                    continue;
                                }
                                nearest = verity;
                            }
                            if (nearest == null) {
                                ((CommandSourceStack) context.getSource()).sendFailure(
                                        Component.literal("No Verity in this dimension."));
                                return 0;
                            }
                            nearest.setVariant(face);
                            ((CommandSourceStack) context.getSource()).sendSuccess(
                                    () -> Component.literal("Verity's face set to " + face + "."), false);
                            return 1;
                        })));
    }
}
