/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.ForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 *  varmite.verity.block.FlashlightLightBlock
 *  varmite.verity.block.ModBlocks
 */
package varmite.verity.block;

import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.IForgeRegistry;
import net.neoforged.neoforge.registries.RegistryObject;
import varmite.verity.block.FlashlightLightBlock;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.BLOCKS, (String)"verity");
    public static final RegistryObject<Block> FLASHLIGHT_LIGHT = BLOCKS.register("flashlight_light", FlashlightLightBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

