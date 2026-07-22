/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.event.TickEvent$Phase
 *  net.neoforged.neoforge.event.TickEvent$PlayerTickEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.custom.VerityDemonEntity
 *  varmite.verity.event.DemonWindowSpawner
 */
package varmite.verity.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.VerityDemonEntity;

@Mod.EventBusSubscriber(modid="verity")
public class DemonWindowSpawner {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        boolean isIndoors;
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }
        if (level.getDayTime() < 120000L) {
            return;
        }
        if (player.getTags().contains("verity_window_scare_done")) {
            return;
        }
        if (level.getGameTime() % 100L != 0L) {
            return;
        }
        boolean bl = isIndoors = !level.canSeeSky(player.blockPosition());
        if (isIndoors) {
            BlockPos playerPos = player.blockPosition();
            for (int x = -5; x <= 5; ++x) {
                for (int y = -1; y <= 2; ++y) {
                    for (int z = -5; z <= 5; ++z) {
                        VerityDemonEntity demon;
                        BlockPos checkPos = playerPos.offset(x, y, z);
                        BlockState state = level.getBlockState(checkPos);
                        String blockName = state.getBlock().getDescriptionId().toLowerCase();
                        if (!blockName.contains("glass") && !blockName.contains("pane")) continue;
                        Vec3 directionToWindow = new Vec3((double)checkPos.getX() - player.getX(), 0.0, (double)checkPos.getZ() - player.getZ()).normalize();
                        BlockPos spawnPos = checkPos.offset((int)Math.round(directionToWindow.x * 1.5), 0, (int)Math.round(directionToWindow.z * 1.5));
                        if (level.getBlockState(spawnPos).blocksMotion() || !level.getBlockState(spawnPos.below()).blocksMotion() || (demon = (VerityDemonEntity)((EntityType)ModEntities.VERITY_DEMON_ENTITY.get()).create(level)) == null) continue;
                        demon.variantArea((double)spawnPos.getX() + 0.5, (double)spawnPos.getY(), (double)spawnPos.getZ() + 0.5, 0.0f, 0.0f);
                        demon.getLookControl().setLookAt((Entity)player, 180.0f, 180.0f);
                        demon.setDemonState(0);
                        demon.setHuntPhase(0);
                        if (level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel)level;
                            serverLevel.setDayTime(18000L);
                        }
                        level.destroyBlock((Entity)demon);
                        player.isColliding("verity_window_scare_done");
                        return;
                    }
                }
            }
        } else {
            VerityDemonEntity demon;
            BlockPos targetPos;
            Vec3 lookVec = player.getLookAngle();
            BlockPos safeSpawnPos = targetPos = BlockPos.offset((double)(player.getX() - lookVec.x * 24.0), (double)player.getY(), (double)(player.getZ() - lookVec.z * 24.0));
            boolean foundGround = false;
            for (int yOffset = 5; yOffset >= -15; --yOffset) {
                BlockPos checkPos = targetPos.offset(0, yOffset, 0);
                if (level.getBlockState(checkPos).blocksMotion() || !level.getBlockState(checkPos.below()).blocksMotion()) continue;
                safeSpawnPos = checkPos;
                foundGround = true;
                break;
            }
            if (!foundGround) {
                safeSpawnPos = level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, targetPos);
            }
            if ((demon = (VerityDemonEntity)((EntityType)ModEntities.VERITY_DEMON_ENTITY.get()).create(level)) != null) {
                demon.variantArea((double)safeSpawnPos.getX() + 0.5, (double)safeSpawnPos.getY(), (double)safeSpawnPos.getZ() + 0.5, 0.0f, 0.0f);
                demon.setDemonState(0);
                demon.setHuntPhase(1);
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    serverLevel.setDayTime(18000L);
                }
                level.destroyBlock((Entity)demon);
                player.isColliding("verity_window_scare_done");
            }
        }
    }
}

