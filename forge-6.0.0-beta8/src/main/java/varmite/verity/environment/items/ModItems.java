/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.RecordItem
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package varmite.verity.environment.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import varmite.verity.environment.items.VerityItem;
import varmite.verity.environment.sounds.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ITEMS, (String)"verity");
    public static final RegistryObject<Item> VERITY_ITEM = ITEMS.register("verity_item", () -> new VerityItem(new Item.Properties()));
    public static final RegistryObject<Item> FLASHLIGHT = ITEMS.register("flashlight", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VERITY_DISC = ITEMS.register("verity_disc", () -> new RecordItem(15, ModSounds.VERITY_DISC_SOUND, new Item.Properties().m_41487_(1).m_41497_(Rarity.RARE), 3000));
    public static final RegistryObject<Item> VERITY_EDIT_DISC = ITEMS.register("verity_edit_disc", () -> new RecordItem(15, ModSounds.VERITY_EDIT_DISC_SOUND, new Item.Properties().m_41487_(1).m_41497_(Rarity.RARE), 400));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

