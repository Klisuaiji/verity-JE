/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 */
package varmite.verity.entity;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import varmite.verity.entity.demon.VerityDemonEntity;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.entity.veritybox.BoxEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ENTITY_TYPES, (String)"verity");
    public static final Supplier<EntityType<VerityEntity>> VERITY_ENTITY = ENTITY_TYPES.register("verity", () -> EntityType.Builder.m_20704_(VerityEntity::new, (MobCategory)MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20712_("verity"));
    public static final Supplier<EntityType<BoxEntity>> BOX_ENTITY = ENTITY_TYPES.register("box", () -> EntityType.Builder.m_20704_(BoxEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.0f, 1.0f).m_20719_().m_20714_(new Block[]{Blocks.f_152499_}).m_20712_("box"));
    public static final Supplier<EntityType<VerityDemonEntity>> VERITY_DEMON_ENTITY = ENTITY_TYPES.register("verity_demon", () -> EntityType.Builder.m_20704_(VerityDemonEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.2f, 3.0f).m_20712_("verity_demon"));

    public static void register(IEventBus modBus) {
        ENTITY_TYPES.register(modBus);
    }
}

