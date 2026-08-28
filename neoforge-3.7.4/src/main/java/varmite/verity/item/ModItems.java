/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.JukeboxSong
 *  net.minecraft.world.item.Rarity
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredItem
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.DeferredRegister$Items
 */
package varmite.verity.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import varmite.verity.item.VerityItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems((String)"verity");
    public static final DeferredItem<Item> VERITY_ITEM = ITEMS.register("verity_item", () -> new VerityItem(new Item.Properties()));
    public static final ResourceKey<JukeboxSong> MY_SONG_KEY = ResourceKey.create((ResourceKey)Registries.JUKEBOX_SONG, (ResourceLocation)ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_disc"));
    public static DeferredItem<Item> FLASHLIGHT = ITEMS.register("flashlight", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> VERITY_DISC = ITEMS.register("verity_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MY_SONG_KEY)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

