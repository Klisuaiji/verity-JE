/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  varmite.verity.item.ModCreativeModeTabs
 *  varmite.verity.item.ModItems
 */
package varmite.verity.item;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import varmite.verity.item.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create((ResourceKey)Registries.CREATIVE_MODE_TAB, (String)"verity");
    public static final Supplier<CreativeModeTab> VERITY_TAB = CREATIVE_MODE_TABS.register("verity_tab", () -> CreativeModeTab.builder().title((Component)Component.getSiblings((String)"creativetab.verity.verity_tab")).title(() -> new ItemStack((ItemLike)ModItems.VERITY_ITEM.get())).title((parameters, output) -> {
        output.accept((ItemLike)ModItems.VERITY_ITEM.get());
        output.accept((ItemLike)ModItems.VERITY_DISC.get());
        output.accept((ItemLike)ModItems.VERITY_EDIT_DISC.get());
        output.accept((ItemLike)ModItems.FLASHLIGHT.get());
    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

