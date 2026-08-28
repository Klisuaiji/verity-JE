/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.EntityAttributeCreationEvent
 */
package varmite.verity.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;

public class ModBusCommonSetup {
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.VERITY_ENTITY.get(), VerityEntity.createAttributes().m_22265_());
        event.put(ModEntities.BOX_ENTITY.get(), BoxEntity.createAttributes().m_22265_());
        event.put(ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonEntity.createAttributes().m_22265_());
    }
}

