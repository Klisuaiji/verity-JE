/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.constant.DataTickets
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.model.data.EntityModelData
 *  varmite.verity.entity.demon.rendering.VerityDemonModel
 *  varmite.verity.entity.demon.VerityDemonEntity
 */
package varmite.verity.entity.demon.rendering;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import varmite.verity.entity.demon.VerityDemonEntity;

public class VerityDemonModel
extends GeoModel<VerityDemonEntity> {
    // 6.0.0-beta.8 — the upstream geo.json now carries only the bone hierarchy
    // (no cubes); the actual surface lives in the Bedrock poly_mesh file below and
    // is drawn by VerityDemonRenderer#renderCubesOfBone.
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"geo/entity/verity_demon.geo.json");
    public static final ResourceLocation MESH = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"meshes/entity/verity_demon.json");

    public ResourceLocation getModelResource(VerityDemonEntity animatable) {
        return MODEL;
    }

    public ResourceLocation getTextureResource(VerityDemonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"textures/entity/verity_demon.png");
    }

    public ResourceLocation getAnimationResource(VerityDemonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"animations/entity/verity_demon.animation.json");
    }

    public void setCustomAnimations(VerityDemonEntity animatable, long instanceId, AnimationState<VerityDemonEntity> animationState) {
        EntityModelData entityData;
        super.setCustomAnimations(animatable, instanceId, animationState);
        // 6.0.0-beta.8 — the new geometry names this bone "Head" (capital H).
        GeoBone head = this.getAnimationProcessor().getBone("Head");
        if (head != null && (entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA)) != null) {
            head.setRotX(entityData.headPitch() * ((float)Math.PI / 180));
            head.setRotY(entityData.netHeadYaw() * ((float)Math.PI / 180));
        }
    }
}

