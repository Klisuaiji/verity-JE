/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.SectionPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.ScreenEvent$Opening
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$RenderTickEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package varmite.verity.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import varmite.verity.VerityConfig;
import varmite.verity.client.DynamicLightManager;
import varmite.verity.client.IntroVideoScreen;
import varmite.verity.item.ModItems;

@Mod.EventBusSubscriber(modid="verity", value={Dist.CLIENT})
public class ModClientEvents {
    private static boolean hasPlayedIntro = false;
    private static Set<BlockPos> previousCenterBlocks = new HashSet<BlockPos>();
    private static Set<SectionPos> previousSections = new HashSet<SectionPos>();
    private static long lastChunkUpdateTime = 0L;
    private static final long UPDATE_COOLDOWN_MS = 50L;

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        if (!hasPlayedIntro && event.getScreen() instanceof TitleScreen) {
            hasPlayedIntro = true;
            if (((Boolean)VerityConfig.PLAY_VIDEO.get()).booleanValue()) {
                event.setNewScreen((Screen)new IntroVideoScreen(event.getScreen()));
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ != null && (Double)mc.f_91066_.m_231927_().m_231551_() > 0.0) {
            mc.f_91066_.m_231927_().m_231514_((Object)0.0);
            mc.f_91066_.m_92169_();
        }
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91073_ == null || mc.f_91074_ == null || mc.f_91060_ == null) {
            return;
        }
        ArrayList<DynamicLightManager.Beam> beams = new ArrayList<DynamicLightManager.Beam>();
        float pt = event.renderTickTime;
        for (Player player : mc.f_91073_.m_6907_()) {
            CompoundTag tag;
            ItemStack flashlight = ModClientEvents.getFlashlight(player);
            if (flashlight.m_41619_() || (tag = flashlight.m_41783_()) == null || !tag.m_128471_("FlashlightOn")) continue;
            Vec3 start = player.m_20299_(pt);
            Vec3 forward = player.m_20252_(pt);
            Vec3 end = start.m_82549_(forward.m_82490_(30.0));
            beams.add(new DynamicLightManager.Beam(start, end, player.m_20148_()));
        }
        DynamicLightManager.updateBeams(beams);
        long now = System.currentTimeMillis();
        if (now - lastChunkUpdateTime < 50L) {
            return;
        }
        HashSet<BlockPos> currentCenterBlocks = new HashSet<BlockPos>();
        HashSet<SectionPos> currentSections = new HashSet<SectionPos>();
        if (!beams.isEmpty()) {
            for (DynamicLightManager.Beam beam : beams) {
                double distance = beam.start.m_82554_(beam.end);
                Vec3 dir = beam.end.m_82546_(beam.start).m_82541_();
                for (double i = 0.0; i <= distance; i += 1.0) {
                    Vec3 point = beam.start.m_82549_(dir.m_82490_(i));
                    currentCenterBlocks.add(BlockPos.m_274561_((double)point.f_82479_, (double)point.f_82480_, (double)point.f_82481_));
                    int radius = (int)Math.ceil(2.0 + i / distance * 5.0);
                    int minSecX = SectionPos.m_123171_((int)((int)Math.floor(point.f_82479_ - (double)radius)));
                    int maxSecX = SectionPos.m_123171_((int)((int)Math.floor(point.f_82479_ + (double)radius)));
                    int minSecY = SectionPos.m_123171_((int)((int)Math.floor(point.f_82480_ - (double)radius)));
                    int maxSecY = SectionPos.m_123171_((int)((int)Math.floor(point.f_82480_ + (double)radius)));
                    int minSecZ = SectionPos.m_123171_((int)((int)Math.floor(point.f_82481_ - (double)radius)));
                    int maxSecZ = SectionPos.m_123171_((int)((int)Math.floor(point.f_82481_ + (double)radius)));
                    for (int x = minSecX; x <= maxSecX; ++x) {
                        for (int y = minSecY; y <= maxSecY; ++y) {
                            for (int z = minSecZ; z <= maxSecZ; ++z) {
                                currentSections.add(SectionPos.m_123173_((int)x, (int)y, (int)z));
                            }
                        }
                    }
                }
            }
        }
        if (currentCenterBlocks.equals(previousCenterBlocks)) {
            return;
        }
        HashSet<SectionPos> allToUpdate = new HashSet<SectionPos>();
        allToUpdate.addAll(previousSections);
        allToUpdate.addAll(currentSections);
        for (SectionPos section : allToUpdate) {
            mc.f_91060_.m_109770_(section.m_123170_(), section.m_123206_(), section.m_123222_());
        }
        previousCenterBlocks = currentCenterBlocks;
        previousSections = currentSections;
        lastChunkUpdateTime = now;
    }

    private static ItemStack getFlashlight(Player player) {
        ItemStack main = player.m_21205_();
        if (main.m_150930_((Item)ModItems.FLASHLIGHT.get())) {
            return main;
        }
        ItemStack off = player.m_21206_();
        if (off.m_150930_((Item)ModItems.FLASHLIGHT.get())) {
            return off;
        }
        return ItemStack.f_41583_;
    }
}

