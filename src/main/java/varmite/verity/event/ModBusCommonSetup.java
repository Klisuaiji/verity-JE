/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.custom.BoxEntity
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.entity.custom.VerityEntity
 *  varmite.verity.event.ModBusCommonSetup
 */
package varmite.verity.event;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;

public class ModBusCommonSetup {
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)ModEntities.VERITY_ENTITY.get(), VerityEntity.createAttributes().build());
        event.put((EntityType)ModEntities.BOX_ENTITY.get(), BoxEntity.createAttributes().build());
        event.put((EntityType)ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonEntity.createAttributes().build());
    }
}

