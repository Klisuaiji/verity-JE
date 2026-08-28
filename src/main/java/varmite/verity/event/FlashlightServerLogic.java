/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.event.TickEvent$Phase
 *  net.neoforged.neoforge.event.tick.ServerTickEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  varmite.verity.block.ModBlocks
 *  varmite.verity.event.FlashlightServerLogic
 *  varmite.verity.item.ModItems
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.block.ModBlocks;
import varmite.verity.item.ModItems;

import net.neoforged.neoforge.event.tick.ServerTickEvent;
/*
 * Exception performing whole class analysis ignored.
 */
@EventBusSubscriber(modid="verity", bus=EventBusSubscriber.Bus.GAME)
public class FlashlightServerLogic {
    private static final Map<UUID, List<BlockPos>> activeLights = new HashMap();

    private static boolean isFlashlightActive(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data != null && data.copyTag().getBoolean("FlashlightOn");
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        if (event.getServer() == null) {
            return;
        }
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            ServerLevel level = player.serverLevel();
            UUID uuid = player.getUUID();
            boolean isFlashlightOn = false;
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            if (main.is((Item)ModItems.FLASHLIGHT.get()) && isFlashlightActive(main) || off.is((Item)ModItems.FLASHLIGHT.get()) && isFlashlightActive(off)) {
                isFlashlightOn = true;
            }
            if (isFlashlightOn) {
                List<BlockPos> currentPositions;
                Vec3 eyePos = player.getEyePosition();
                Vec3 lookVec = player.getViewVector(1.0f);
                double maxDist = 25.0;
                Vec3 endPos = eyePos.add(lookVec.scale(maxDist));
                BlockHitResult hitResult = level.clip(new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)player));
                double hitDistance = maxDist;
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    hitDistance = hitResult.getLocation().distanceTo(eyePos);
                }
                ArrayList<BlockPos> desiredPositions = new ArrayList<BlockPos>();
                for (double i = 2.0; i <= hitDistance - 1.5; i += 2.5) {
                    desiredPositions.add(BlockPos.containing(eyePos.add(lookVec.scale(i))));
                }
                double finalHitDist = Math.max(0.5, hitDistance - 0.5);
                Vec3 hitVec = eyePos.add(lookVec.scale(finalHitDist));
                BlockPos hitCenter = BlockPos.containing(hitVec);
                if (!desiredPositions.contains(hitCenter)) {
                    desiredPositions.add(hitCenter);
                }
                if (hitDistance > 4.0) {
                    Vec3 up = player.getUpVector(1.0f);
                    Vec3 right = lookVec.cross(up).normalize();
                    if (right.lengthSqr() < 0.001) {
                        float yawRad = player.getYRot() * ((float)Math.PI / 180);
                        right = new Vec3(-Math.cos(yawRad), 0.0, -Math.sin(yawRad)).normalize();
                    }
                    Vec3 trueUp = right.cross(lookVec).normalize();
                    desiredPositions.add(BlockPos.containing(hitVec.add(right.scale(1.5))));
                    desiredPositions.add(BlockPos.containing(hitVec.subtract(right.scale(1.5))));
                    desiredPositions.add(BlockPos.containing(hitVec.add(trueUp.scale(1.5))));
                    desiredPositions.add(BlockPos.containing(hitVec.subtract(trueUp.scale(1.5))));
                }
                if (desiredPositions.equals(currentPositions = activeLights.getOrDefault(uuid, new ArrayList<>()))) continue;
                for (BlockPos pos : currentPositions) {
                    BlockState state;
                    if (desiredPositions.contains(pos) || !(state = level.getBlockState(pos)).is((Block)ModBlocks.FLASHLIGHT_LIGHT.get())) continue;
                    boolean wasWater = (Boolean)state.getValue((Property)BlockStateProperties.WATERLOGGED);
                    level.setBlock(pos, wasWater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
                }
                for (BlockPos pos : desiredPositions) {
                    boolean isWaterSource;
                    if (currentPositions.contains(pos)) continue;
                    BlockState targetState = level.getBlockState(pos);
                    boolean isAir = targetState.isAir();
                    boolean bl = isWaterSource = targetState.is(Blocks.WATER) && targetState.getFluidState().isSource();
                    if (!isAir && !isWaterSource) continue;
                    level.setBlock(pos, (BlockState)((Block)ModBlocks.FLASHLIGHT_LIGHT.get()).defaultBlockState().setValue((Property)BlockStateProperties.WATERLOGGED, (Comparable)Boolean.valueOf(isWaterSource)), 3);
                }
                activeLights.put(uuid, desiredPositions);
                continue;
            }
            if (!activeLights.containsKey(uuid)) continue;
            FlashlightServerLogic.cleanupLight((ServerPlayer)player, (UUID)uuid);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        FlashlightServerLogic.cleanupLight((ServerPlayer)((ServerPlayer)event.getEntity()), (UUID)event.getEntity().getUUID());
    }

    private static void cleanupLight(ServerPlayer player, UUID uuid) {
        List<BlockPos> positions = activeLights.get(uuid);
        if (positions != null) {
            ServerLevel level = player.serverLevel();
            for (BlockPos pos : positions) {
                BlockState state = level.getBlockState(pos);
                if (!state.is((Block)ModBlocks.FLASHLIGHT_LIGHT.get())) continue;
                boolean wasWater = (Boolean)state.getValue((Property)BlockStateProperties.WATERLOGGED);
                level.setBlock(pos, wasWater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
            }
            activeLights.remove(uuid);
        }
    }
}

