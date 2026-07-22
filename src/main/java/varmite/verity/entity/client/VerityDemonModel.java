/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.constant.DataTickets
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.model.data.EntityModelData
 *  varmite.verity.entity.client.VerityDemonModel
 *  varmite.verity.entity.custom.VerityDemonEntity
 */
package varmite.verity.entity.client;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import varmite.verity.entity.custom.VerityDemonEntity;

public class VerityDemonModel
extends GeoModel<VerityDemonEntity> {
    public ResourceLocation getModelResource(VerityDemonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"geo/entity/verity_demon.geo.json");
    }

    public ResourceLocation getTextureResource(VerityDemonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/entity/verity_demon.png");
    }

    public ResourceLocation getAnimationResource(VerityDemonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"animations/entity/verity_demon.animation.json");
    }

    public void setCustomAnimations(VerityDemonEntity animatable, long instanceId, AnimationState<VerityDemonEntity> animationState) {
        EntityModelData entityData;
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null && (entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA)) != null) {
            head.setRotX(entityData.headPitch() * ((float)Math.PI / 180));
            head.setRotY(entityData.netHeadYaw() * ((float)Math.PI / 180));
        }
    }
}

