/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  varmite.verity.entity.client.BoxModel
 *  varmite.verity.entity.client.BoxRenderer
 *  varmite.verity.entity.custom.BoxEntity
 */
package varmite.verity.entity.client;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import varmite.verity.entity.client.BoxModel;
import varmite.verity.entity.custom.BoxEntity;

public class BoxRenderer
extends GeoEntityRenderer<BoxEntity> {
    public BoxRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, (GeoModel)new BoxModel());
        this.shadowRadius = 0.7f;
    }

    @Override
    public RenderType getRenderType(BoxEntity animatable, ResourceLocation texture,
                                    MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }

    @Override
    public boolean shouldShowName(BoxEntity animatable) {
        return false;
    }
}

