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
 *  net.neoforged.neoforge.event.TickEvent$ServerTickEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  varmite.verity.block.ModBlocks
 *  varmite.verity.event.FlashlightServerLogic
 *  varmite.verity.item.ModItems
 */
package varmite.verity.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.block.ModBlocks;
import varmite.verity.item.ModItems;

/*
 * Exception performing whole class analysis ignored.
 */
@Mod.EventBusSubscriber(modid="verity")
public class FlashlightServerLogic {
    private static final Map<UUID, List<BlockPos>> activeLights = new HashMap();

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (event.getServer() == null) {
            return;
        }
        for (ServerPlayer player : event.getServer().m_6846_().m_11314_()) {
            ServerLevel level = player.m_284548_();
            UUID uuid = player.getUUID();
            boolean isFlashlightOn = false;
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            if (main.m_150930_((Item)ModItems.FLASHLIGHT.get()) && main.m_41784_().m_128471_("FlashlightOn") || off.m_150930_((Item)ModItems.FLASHLIGHT.get()) && off.m_41784_().m_128471_("FlashlightOn")) {
                isFlashlightOn = true;
            }
            if (isFlashlightOn) {
                List currentPositions;
                Vec3 eyePos = player.getEyePosition();
                Vec3 lookVec = player.m_20252_(1.0f);
                double maxDist = 25.0;
                Vec3 endPos = eyePos.m_82549_(lookVec.m_82490_(maxDist));
                BlockHitResult hitResult = level.getBlockEntity(new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)player));
                double hitDistance = maxDist;
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    hitDistance = hitResult.getLocation().m_82554_(eyePos);
                }
                ArrayList<BlockPos> desiredPositions = new ArrayList<BlockPos>();
                for (double i = 2.0; i <= hitDistance - 1.5; i += 2.5) {
                    desiredPositions.add(BlockPos.offset((Position)eyePos.m_82549_(lookVec.m_82490_(i))));
                }
                double finalHitDist = Math.max(0.5, hitDistance - 0.5);
                Vec3 hitVec = eyePos.m_82549_(lookVec.m_82490_(finalHitDist));
                BlockPos hitCenter = BlockPos.offset((Position)hitVec);
                if (!desiredPositions.contains(hitCenter)) {
                    desiredPositions.add(hitCenter);
                }
                if (hitDistance > 4.0) {
                    Vec3 up = player.m_20289_(1.0f);
                    Vec3 right = lookVec.m_82537_(up).m_82541_();
                    if (right.m_82556_() < 0.001) {
                        float yawRad = player.getYRot() * ((float)Math.PI / 180);
                        right = new Vec3(-Math.cos(yawRad), 0.0, -Math.sin(yawRad)).m_82541_();
                    }
                    Vec3 trueUp = right.m_82537_(lookVec).m_82541_();
                    desiredPositions.add(BlockPos.offset((Position)hitVec.m_82549_(right.m_82490_(1.5))));
                    desiredPositions.add(BlockPos.offset((Position)hitVec.m_82546_(right.m_82490_(1.5))));
                    desiredPositions.add(BlockPos.offset((Position)hitVec.m_82549_(trueUp.m_82490_(1.5))));
                    desiredPositions.add(BlockPos.offset((Position)hitVec.m_82546_(trueUp.m_82490_(1.5))));
                }
                if (desiredPositions.equals(currentPositions = (List)activeLights.getOrDefault(uuid, new ArrayList()))) continue;
                for (BlockPos pos : currentPositions) {
                    BlockState state;
                    if (desiredPositions.contains(pos) || !(state = level.getBlockState(pos)).m_60713_((Block)ModBlocks.FLASHLIGHT_LIGHT.get())) continue;
                    boolean wasWater = (Boolean)state.m_61143_((Property)BlockStateProperties.WATERLOGGED);
                    level.setBlock(pos, wasWater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
                }
                for (BlockPos pos : desiredPositions) {
                    boolean isWaterSource;
                    if (currentPositions.contains(pos)) continue;
                    BlockState targetState = level.getBlockState(pos);
                    boolean isAir = targetState.m_60795_();
                    boolean bl = isWaterSource = targetState.m_60713_(Blocks.WATER) && targetState.m_60819_().m_76170_();
                    if (!isAir && !isWaterSource) continue;
                    level.setBlock(pos, (BlockState)((Block)ModBlocks.FLASHLIGHT_LIGHT.get()).defaultBlockState().m_61124_((Property)BlockStateProperties.WATERLOGGED, (Comparable)Boolean.valueOf(isWaterSource)), 3);
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
        List positions = (List)activeLights.get(uuid);
        if (positions != null) {
            ServerLevel level = player.m_284548_();
            for (BlockPos pos : positions) {
                BlockState state = level.getBlockState(pos);
                if (!state.m_60713_((Block)ModBlocks.FLASHLIGHT_LIGHT.get())) continue;
                boolean wasWater = (Boolean)state.m_61143_((Property)BlockStateProperties.WATERLOGGED);
                level.setBlock(pos, wasWater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
            }
            activeLights.remove(uuid);
        }
    }
}

