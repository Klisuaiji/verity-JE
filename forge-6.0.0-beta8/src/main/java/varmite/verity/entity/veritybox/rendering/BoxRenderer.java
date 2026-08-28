/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package varmite.verity.entity.veritybox.rendering;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import varmite.verity.entity.veritybox.BoxEntity;
import varmite.verity.entity.veritybox.rendering.BoxModel;

public class BoxRenderer
extends GeoEntityRenderer<BoxEntity> {
    public BoxRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, (GeoModel)new BoxModel());
    }

    public boolean shouldShowName(BoxEntity animatable) {
        return false;
    }
}

