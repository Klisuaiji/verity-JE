/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.neoforged.neoforge.client.event.ModelEvent$ModifyBakingResult
 *  net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  varmite.verity.client.VerityPreviewTexture
 *  varmite.verity.entity.AI.AiAPI
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.client.BoxRenderer
 *  varmite.verity.entity.client.SphereEntityRenderer
 *  varmite.verity.entity.client.VerityDemonRenderer
 *  varmite.verity.entity.client.VerityEntityTexture
 *  varmite.verity.event.ModBusClientSetup
 *  varmite.verity.gui.KarmaHudOverlay
 *  varmite.verity.item.UnshadedBakedModel
 */
package varmite.verity.event;

import java.util.Map;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import varmite.verity.client.VerityPreviewTexture;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.client.BoxRenderer;
import varmite.verity.entity.client.SphereEntityRenderer;
import varmite.verity.entity.client.VerityDemonRenderer;
import varmite.verity.entity.client.VerityEntityTexture;
import varmite.verity.gui.KarmaHudOverlay;
import varmite.verity.item.UnshadedBakedModel;

@Mod.EventBusSubscriber(modid="verity", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class ModBusClientSetup {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType)ModEntities.VERITY_ENTITY.get(), SphereEntityRenderer::new);
        event.registerEntityRenderer((EntityType)ModEntities.BOX_ENTITY.get(), BoxRenderer::new);
        event.registerEntityRenderer((EntityType)ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("karma_hud", KarmaHudOverlay.HUD_KARMA);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ResourceLocation verityItemId = new ResourceLocation("verity", "verity_item");
        for (Map.Entry entry : event.getModels().entrySet()) {
            ResourceLocation key = (ResourceLocation)entry.getKey();
            if (!key.m_135827_().equals(verityItemId.m_135827_()) || !key.m_135815_().equals(verityItemId.m_135815_())) continue;
            event.getModels().put(key, new UnshadedBakedModel((BakedModel)entry.getValue()));
        }
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

