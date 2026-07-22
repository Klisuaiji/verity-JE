/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 */
package varmite.verity.event;

import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;

public class ModBusCommonSetup {
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.VERITY_ENTITY.get(), VerityEntity.createAttributes().build());
        event.put(ModEntities.BOX_ENTITY.get(), BoxEntity.createAttributes().build());
        event.put(ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonEntity.createAttributes().build());
    }
}

