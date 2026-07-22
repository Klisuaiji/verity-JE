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
 *  net.neoforged.neoforge.registries.ForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 *  varmite.verity.item.ModItems
 *  varmite.verity.item.VerityItem
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.IForgeRegistry;
import net.neoforged.neoforge.registries.RegistryObject;
import varmite.verity.item.VerityItem;
import varmite.verity.sounds.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ITEMS, (String)"verity");
    public static final RegistryObject<Item> VERITY_ITEM = ITEMS.register("verity_item", () -> new VerityItem(new Item.Properties()));
    public static RegistryObject<Item> FLASHLIGHT = ITEMS.register("flashlight", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VERITY_DISC = ITEMS.register("verity_disc", () -> new RecordItem(15, () -> (SoundEvent)ModSounds.VERITY_DISC_SOUND.get(), new Item.Properties().food(1).food(Rarity.RARE), 3000));
    public static final RegistryObject<Item> VERITY_EDIT_DISC = ITEMS.register("verity_edit_disc", () -> new RecordItem(15, () -> (SoundEvent)ModSounds.VERITY_EDIT_DISC_SOUND.get(), new Item.Properties().food(1).food(Rarity.RARE), 400));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

