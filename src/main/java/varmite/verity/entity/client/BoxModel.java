/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 *  varmite.verity.entity.client.BoxModel
 *  varmite.verity.entity.custom.BoxEntity
 */
package varmite.verity.entity.client;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import varmite.verity.entity.custom.BoxEntity;

public class BoxModel
extends GeoModel<BoxEntity> {
    public ResourceLocation getModelResource(BoxEntity entity) {
        return ResourceLocation.parse((String)"verity:geo/entity/box.geo.json");
    }

    public ResourceLocation getTextureResource(BoxEntity entity) {
        return ResourceLocation.parse((String)"verity:textures/entity/box.png");
    }

    public ResourceLocation getAnimationResource(BoxEntity entity) {
        return ResourceLocation.parse((String)"verity:animations/entity/box.animation.json");
    }
}

