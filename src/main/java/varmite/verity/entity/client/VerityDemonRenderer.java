/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.world.entity.Entity
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  varmite.verity.entity.client.VerityDemonModel
 *  varmite.verity.entity.client.VerityDemonRenderer
 *  varmite.verity.entity.custom.VerityDemonEntity
 */
package varmite.verity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import varmite.verity.entity.client.VerityDemonModel;
import varmite.verity.entity.custom.VerityDemonEntity;

public class VerityDemonRenderer
extends GeoEntityRenderer<VerityDemonEntity> {
    public VerityDemonRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new VerityDemonModel());
    }

    public void render(VerityDemonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        int actualDeathTime = entity.deathTime;
        entity.deathTime = 0;
        super.render((Entity)entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        entity.deathTime = actualDeathTime;
    }
}

