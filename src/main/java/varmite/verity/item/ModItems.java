package varmite.verity.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;
import varmite.verity.item.VerityItem;
import varmite.verity.sounds.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "verity");
    public static final DeferredHolder<Item, Item> VERITY_ITEM = ITEMS.register("verity_item", () -> new VerityItem(new Item.Properties()));
    public static DeferredHolder<Item, Item> FLASHLIGHT = ITEMS.register("flashlight", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> VERITY_DISC = ITEMS.register("verity_disc", () -> new Item(new Item.Properties().rarity(Rarity.RARE)
            .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath("verity", "verity_disc")))));
    public static final DeferredHolder<Item, Item> VERITY_EDIT_DISC = ITEMS.register("verity_edit_disc", () -> new Item(new Item.Properties().rarity(Rarity.RARE)
            .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath("verity", "verity_edit_disc")))));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
