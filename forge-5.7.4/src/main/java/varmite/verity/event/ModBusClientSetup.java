/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.client.event.ModelEvent$ModifyBakingResult
 *  net.minecraftforge.client.event.RegisterGuiOverlaysEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 */
package varmite.verity.event;

import java.util.Map;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        event.registerEntityRenderer(ModEntities.VERITY_ENTITY.get(), SphereEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.BOX_ENTITY.get(), BoxRenderer::new);
        event.registerEntityRenderer(ModEntities.VERITY_DEMON_ENTITY.get(), VerityDemonRenderer::new);
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

