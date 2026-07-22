/*
 * Ported to NeoForge 1.21.1 — capability system replaced by the Attachments API.
 * The per-player karma is now an AttachmentType that auto-attaches to every IAttachmentHolder
 * (players included) and is copied across death via copyOnDeath().
 */
package varmite.verity.gui;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class PlayerKarmaProvider {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "verity");

    public static final Supplier<AttachmentType<PlayerKarma>> PLAYER_KARMA =
            ATTACHMENT_TYPES.register("player_karma",
                    () -> AttachmentType.serializable(PlayerKarma::new).copyOnDeath().build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
