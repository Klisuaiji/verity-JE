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
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.registries.DeferredRegister
 */
package varmite.verity.item;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import varmite.verity.item.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create((ResourceKey)Registries.f_279569_, (String)"verity");
    public static final Supplier<CreativeModeTab> VERITY_TAB = CREATIVE_MODE_TABS.register("verity_tab", () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237115_((String)"creativetab.verity.verity_tab")).m_257737_(() -> new ItemStack((ItemLike)ModItems.VERITY_ITEM.get())).m_257501_((parameters, output) -> {
        output.m_246326_((ItemLike)ModItems.VERITY_ITEM.get());
        output.m_246326_((ItemLike)ModItems.VERITY_DISC.get());
        output.m_246326_((ItemLike)ModItems.VERITY_EDIT_DISC.get());
        output.m_246326_((ItemLike)ModItems.FLASHLIGHT.get());
    }).m_257652_());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

