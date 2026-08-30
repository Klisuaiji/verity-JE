/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.veritybox.BoxEntity
 *  varmite.verity.entity.demon.VerityDemonEntity
 *  varmite.verity.entity.verity.VerityEntity
 *  varmite.verity.event.ModBusCommonSetup
 */
package varmite.verity.event;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.veritybox.BoxEntity;
import varmite.verity.entity.demon.VerityDemonEntity;
import varmite.verity.entity.verity.VerityEntity;

public class ModBusCommonSetup {
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)ModEntities.VERITY_ENTITY.get(), VerityEntity.createAttributes().build());
        event.put((EntityType)ModEntities.BOX_ENTITY.get(), BoxEntity.createAttributes().build());
        event.put((EntityType)ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonEntity.createAttributes().build());
    }
}

