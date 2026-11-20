/*
 * Rewritten for NeoForge 1.21.1 GUI Layers API.
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Map;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import varmite.verity.client.render.BedrockPolyMesh;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import varmite.verity.client.AudioHudRenderer;
import varmite.verity.client.VerityPreviewTexture;
import varmite.verity.entity.llm.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.veritybox.rendering.BoxRenderer;
import varmite.verity.entity.utils.SphereEntityRenderer;
import varmite.verity.entity.demon.rendering.VerityDemonRenderer;
import varmite.verity.entity.verity.rendering.VerityEntityTexture;
import varmite.verity.client.gui.KarmaHudOverlay;
import varmite.verity.environment.items.UnshadedBakedModel;

@EventBusSubscriber(modid="verity", bus=EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class ModBusClientSetup {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType)ModEntities.VERITY_ENTITY.get(), SphereEntityRenderer::new);
        event.registerEntityRenderer((EntityType)ModEntities.BOX_ENTITY.get(), BoxRenderer::new);
        event.registerEntityRenderer((EntityType)ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath("verity", "karma_hud"), KarmaHudOverlay.HUD_KARMA);
        event.registerAbove(VanillaGuiLayers.HOTBAR, ResourceLocation.fromNamespaceAndPath("verity", "audio_hud"), AudioHudRenderer.LAYER);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ResourceLocation verityItemId = ResourceLocation.fromNamespaceAndPath("verity", "verity_item");
        for (Map.Entry<ModelResourceLocation, BakedModel> entry : event.getModels().entrySet()) {
            ModelResourceLocation key = entry.getKey();
            if (!key.id().getNamespace().equals(verityItemId.getNamespace()) || !key.id().getPath().equals(verityItemId.getPath())) continue;
            event.getModels().put(key, new UnshadedBakedModel((BakedModel)entry.getValue()));
        }
    }

    // 6.0.0-beta.8 — drop the baked poly_mesh cache on resource reload (F3+T,
    // resource pack changes), otherwise edited geometry would never be picked up.
    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((PreparableReloadListener)((ResourceManagerReloadListener)manager -> BedrockPolyMesh.clearCache()));
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(VerityPreviewTexture::init);
        event.enqueueWork(VerityEntityTexture::init);
        event.enqueueWork(() -> {
            System.out.println("[Verity Boot] FMLClientSetupEvent fired! Starting STT Init...");
            AiAPI.initLocalSTT();
        });
    }
}
