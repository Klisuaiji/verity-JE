/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.Util
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 *  net.neoforged.neoforge.client.event.RenderLevelStageEvent
 *  net.neoforged.neoforge.client.event.RenderLevelStageEvent$Stage
 *  net.neoforged.neoforge.client.event.ScreenEvent$Init$Post
 */
package varmite.verity.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import varmite.verity.VerityConfig;
import varmite.verity.client.DynamicLightManager;
import varmite.verity.client.IntroVideoScreen;
import varmite.verity.item.ModItems;

@EventBusSubscriber(modid="verity", value={Dist.CLIENT})
public class ModClientEvents {
    private static boolean hasPlayedIntro = false;
    private static Map<UUID, PlayerLightState> previousStates = new HashMap<UUID, PlayerLightState>();
    private static long lastChunkUpdate = 0L;

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!hasPlayedIntro && event.getScreen() instanceof TitleScreen) {
            hasPlayedIntro = true;
            if (((Boolean)VerityConfig.PLAY_VIDEO.get()).booleanValue()) {
                Minecraft.getInstance().setScreen((Screen)new IntroVideoScreen(event.getScreen()));
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        if ((Double)mc.options.gamma().get() > 0.0) {
            mc.options.gamma().set(0.0);
            mc.options.save();
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.levelRenderer == null) {
            return;
        }
        ArrayList<DynamicLightManager.Beam> allMixinBeams = new ArrayList<DynamicLightManager.Beam>();
        HashMap<UUID, PlayerLightState> currentStates = new HashMap<UUID, PlayerLightState>();
        float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
        for (Player player : mc.level.players()) {
            boolean isOn;
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            ItemStack object = mainHand.is((Item)ModItems.FLASHLIGHT.get()) ? mainHand : (offHand.is((Item)ModItems.FLASHLIGHT.get()) ? offHand : ItemStack.EMPTY);
            if (object.isEmpty() || !object.has(DataComponents.CUSTOM_DATA) || !(isOn = ((CustomData)object.get(DataComponents.CUSTOM_DATA)).copyTag().getBoolean("FlashlightOn"))) continue;
            Vec3 start = player.getEyePosition(partialTick);
            Vec3 forward = player.getViewVector(partialTick);
            Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
            if (right.lengthSqr() == 0.0) {
                right = forward.cross(new Vec3(1.0, 0.0, 0.0)).normalize();
            }
            Vec3 up = right.cross(forward).normalize();
            double spread = 0.15;
            double reach = 30.0;
            Vec3[] directions = new Vec3[]{forward, forward.add(up.scale(spread)).normalize(), forward.subtract(up.scale(spread)).normalize(), forward.add(right.scale(spread)).normalize(), forward.subtract(right.scale(spread)).normalize()};
            ArrayList<Vec3> ends = new ArrayList<Vec3>();
            for (Vec3 dir : directions) {
                Vec3 hitEnd = ModClientEvents.castPiercingRay(mc, player, start, dir, reach);
                ends.add(hitEnd);
                allMixinBeams.add(new DynamicLightManager.Beam(start, hitEnd, player.getUUID()));
            }
            currentStates.put(player.getUUID(), new PlayerLightState(start, (Vec3)ends.get(0), ends));
        }
        DynamicLightManager.updateBeams(allMixinBeams);
        long currentTime = Util.getMillis();
        boolean canUpdateChunks = currentTime - lastChunkUpdate > 30L;
        for (Map.Entry<UUID, PlayerLightState> entry : currentStates.entrySet()) {
            PlayerLightState current = entry.getValue();
            PlayerLightState prev = previousStates.get(entry.getKey());
            if (prev == null) {
                ModClientEvents.markConeDirty(mc.levelRenderer, current);
                previousStates.put(entry.getKey(), current);
                continue;
            }
            if (!canUpdateChunks || !(prev.start.distanceToSqr(current.start) > 0.01) && !(prev.centerEnd.distanceToSqr(current.centerEnd) > 0.5)) continue;
            ModClientEvents.markConeDirty(mc.levelRenderer, prev);
            ModClientEvents.markConeDirty(mc.levelRenderer, current);
            previousStates.put((UUID)entry.getKey(), current);
        }
        Iterator<Map.Entry<UUID, PlayerLightState>> iterator = previousStates.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, PlayerLightState> entry = iterator.next();
            if (currentStates.containsKey(entry.getKey())) continue;
            ModClientEvents.markConeDirty(mc.levelRenderer, entry.getValue());
            iterator.remove();
        }
        if (canUpdateChunks) {
            lastChunkUpdate = currentTime;
        }
    }

    private static Vec3 castPiercingRay(Minecraft mc, Player player, Vec3 start, Vec3 direction, double maxDistance) {
        ClipContext context;
        BlockHitResult hit;
        Vec3 maxEnd;
        Vec3 currentStart = start;
        Vec3 end = maxEnd = start.add(direction.scale(maxDistance));
        for (int i = 0; i < 20 && (hit = mc.level.clip(context = new ClipContext(currentStart, maxEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)player))).getType() != HitResult.Type.MISS; ++i) {
            BlockState state = mc.level.getBlockState(hit.getBlockPos());
            if (state.getLightBlock((BlockGetter)mc.level, hit.getBlockPos()) == 15 || state.isSolidRender((BlockGetter)mc.level, hit.getBlockPos())) {
                end = hit.getLocation();
                break;
            }
            currentStart = hit.getLocation().add(direction.scale(0.05));
        }
        return end;
    }

    private static void markConeDirty(LevelRenderer renderer, PlayerLightState state) {
        for (Vec3 end : state.allEnds) {
            Vec3 start;
            Vec3 dir = end.subtract(start = state.start);
            double length = dir.length();
            if (length == 0.0) continue;
            dir = dir.normalize();
            for (double d = 0.0; d <= length + 1.5; d += 3.0) {
                double actualDist = Math.min(d, length);
                Vec3 p = start.add(dir.scale(actualDist));
                double t = actualDist / length;
                double radius = 2.0 + t * 5.0;
                int r = Mth.ceil((double)radius);
                renderer.setBlocksDirty(Mth.floor((double)p.x) - r, Mth.floor((double)p.y) - r, Mth.floor((double)p.z) - r, Mth.ceil((double)p.x) + r, Mth.ceil((double)p.y) + r, Mth.ceil((double)p.z) + r);
            }
        }
    }

    private static class PlayerLightState {
        Vec3 start;
        Vec3 centerEnd;
        List<Vec3> allEnds;

        PlayerLightState(Vec3 start, Vec3 centerEnd, List<Vec3> allEnds) {
            this.start = start;
            this.centerEnd = centerEnd;
            this.allEnds = allEnds;
        }
    }
}

