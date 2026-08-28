/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.neoforged.neoforge.client.event.ModelEvent$ModifyBakingResult
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
 */
package varmite.verity.event;

import java.util.Map;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.client.BoxRenderer;
import varmite.verity.entity.client.SphereEntityRenderer;
import varmite.verity.entity.client.VerityDemonRenderer;
import varmite.verity.item.ModItems;
import varmite.verity.item.UnshadedBakedModel;
import varmite.verity.item.client.VerityItemRenderer;

public class ModBusClientSetup {
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.VERITY_ENTITY.get(), SphereEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.BOX_ENTITY.get(), BoxRenderer::new);
        event.registerEntityRenderer(ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonRenderer::new);
    }

    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions(){

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new VerityItemRenderer();
            }
        }, new Item[]{(Item)ModItems.VERITY_ITEM.get()});
    }

    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ResourceLocation verityItemId = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_item");
        for (Map.Entry<ModelResourceLocation, BakedModel> entry : event.getModels().entrySet()) {
            if (!entry.getKey().id().equals(verityItemId)) continue;
            event.getModels().put(entry.getKey(), new UnshadedBakedModel(entry.getValue()));
        }
    }
}

