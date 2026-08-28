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
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.ScreenEvent$Opening
 *  net.neoforged.neoforge.client.event.ClientTickEvent
 *  net.neoforged.neoforge.event.TickEvent$Phase
 *  net.neoforged.neoforge.client.event.ClientTickEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  varmite.verity.VerityConfig
 *  varmite.verity.client.DynamicLightManager
 *  varmite.verity.client.DynamicLightManager$Beam
 *  varmite.verity.client.IntroVideoScreen
 *  varmite.verity.event.ModClientEvents
 *  varmite.verity.item.ModItems
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ScreenEvent;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.VerityConfig;
import varmite.verity.client.DynamicLightManager;
import varmite.verity.client.IntroVideoScreen;
import varmite.verity.item.ModItems;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
/*
 * Exception performing whole class analysis ignored.
 */
@EventBusSubscriber(modid="verity", bus=EventBusSubscriber.Bus.GAME, value={Dist.CLIENT})
public class ModClientEvents {
    private static boolean hasPlayedIntro = false;
    private static Set<BlockPos> previousCenterBlocks = new HashSet();
    private static Set<SectionPos> previousSections = new HashSet();
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
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && (Double)mc.options.gamma().get() > 0.0) {
            mc.options.gamma().set(0.0);
            mc.options.save();
        }
    }

    @SubscribeEvent
    public static void onRenderFrame(RenderFrameEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || mc.levelRenderer == null) {
            return;
        }
        ArrayList<DynamicLightManager.Beam> beams = new ArrayList<DynamicLightManager.Beam>();
        float pt = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        for (Player player : mc.level.players()) {
            ItemStack flashlight = ModClientEvents.getFlashlight((Player)player);
            if (flashlight.isEmpty()) continue;
            CompoundTag tag = flashlight.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            if (tag == null || !tag.getBoolean("FlashlightOn")) continue;
            Vec3 start = player.getEyePosition(pt);
            Vec3 forward = player.getViewVector(pt);
            Vec3 end = start.add(forward.scale(30.0));
            beams.add(new DynamicLightManager.Beam(start, end, player.getUUID()));
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
                double distance = beam.start.distanceTo(beam.end);
                Vec3 dir = beam.end.subtract(beam.start).normalize();
                for (double i = 0.0; i <= distance; i += 1.0) {
                    Vec3 point = beam.start.add(dir.scale(i));
                    currentCenterBlocks.add(BlockPos.containing(point.x, point.y, point.z));
                    int radius = (int)Math.ceil(2.0 + i / distance * 5.0);
                    int minSecX = SectionPos.blockToSectionCoord((int)Math.floor(point.x - (double)radius));
                    int maxSecX = SectionPos.blockToSectionCoord((int)Math.floor(point.x + (double)radius));
                    int minSecY = SectionPos.blockToSectionCoord((int)Math.floor(point.y - (double)radius));
                    int maxSecY = SectionPos.blockToSectionCoord((int)Math.floor(point.y + (double)radius));
                    int minSecZ = SectionPos.blockToSectionCoord((int)Math.floor(point.z - (double)radius));
                    int maxSecZ = SectionPos.blockToSectionCoord((int)Math.floor(point.z + (double)radius));
                    for (int x = minSecX; x <= maxSecX; ++x) {
                        for (int y = minSecY; y <= maxSecY; ++y) {
                            for (int z = minSecZ; z <= maxSecZ; ++z) {
                                currentSections.add(SectionPos.of((int)x, (int)y, (int)z));
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
            mc.levelRenderer.setSectionDirtyWithNeighbors(section.x(), section.y(), section.z());
        }
        previousCenterBlocks = currentCenterBlocks;
        previousSections = currentSections;
        lastChunkUpdateTime = now;
    }

    private static ItemStack getFlashlight(Player player) {
        ItemStack main = player.getMainHandItem();
        if (main.is((Item)ModItems.FLASHLIGHT.get())) {
            return main;
        }
        ItemStack off = player.getOffhandItem();
        if (off.is((Item)ModItems.FLASHLIGHT.get())) {
            return off;
        }
        return ItemStack.EMPTY;
    }
}

