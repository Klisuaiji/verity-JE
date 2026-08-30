/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.RecordItem
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 *  varmite.verity.item.ModItems
 *  varmite.verity.item.VerityItem
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;
import varmite.verity.item.VerityItem;
import varmite.verity.sounds.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, (String)"verity");
    public static final DeferredHolder<Item, Item> VERITY_ITEM = ITEMS.register("verity_item", () -> new VerityItem(new Item.Properties()));
    public static DeferredHolder<Item, Item> FLASHLIGHT = ITEMS.register("flashlight", () -> new Item(new Item.Properties()));
    // 1.20.1 的 RecordItem 在 1.21.1 已被移除：唱片改由 JukeboxSong 数据注册表（data/verity/jukebox_song/*.json）
    public static final ResourceKey<JukeboxSong> VERITY_DISC_SONG = ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_disc"));
    public static final ResourceKey<JukeboxSong> VERITY_EDIT_DISC_SONG = ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_edit_disc"));
    public static final DeferredHolder<Item, Item> VERITY_DISC = ITEMS.register("verity_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(VERITY_DISC_SONG)));
    public static final DeferredHolder<Item, Item> VERITY_EDIT_DISC = ITEMS.register("verity_edit_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(VERITY_EDIT_DISC_SONG)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

