/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.custom.BoxEntity
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.entity.custom.VerityEntity
 */
package varmite.verity.entity;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(NeoForgeRegistries.ENTITY_TYPES, (String)"verity");
    public static final Supplier<EntityType<VerityEntity>> VERITY_ENTITY = ENTITY_TYPES.register("verity", () -> EntityType.Builder.of(VerityEntity::new, (MobCategory)MobCategory.MISC).of(0.5f, 0.5f).of("verity"));
    public static final Supplier<EntityType<BoxEntity>> BOX_ENTITY = ENTITY_TYPES.register("box", () -> EntityType.Builder.of(BoxEntity::new, (MobCategory)MobCategory.MISC).of(1.0f, 1.0f).fireImmune().of(new Block[]{Blocks.POWDER_SNOW}).of("box"));
    public static final Supplier<EntityType<VerityDemonEntity>> VERITY_DEMON_ENTITY = ENTITY_TYPES.register("verity_demon", () -> EntityType.Builder.of(VerityDemonEntity::new, (MobCategory)MobCategory.MISC).of(1.2f, 3.0f).of("verity_demon"));

    public static void register(IEventBus modBus) {
        ENTITY_TYPES.register(modBus);
    }
}

