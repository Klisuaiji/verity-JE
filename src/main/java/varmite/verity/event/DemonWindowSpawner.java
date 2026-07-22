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
        if (level.m_46468_() < 120000L) {
            return;
        }
        if (player.getTags().contains("verity_window_scare_done")) {
            return;
        }
        if (level.m_46467_() % 100L != 0L) {
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
                        String blockName = state.m_60734_().getDescriptionId().toLowerCase();
                        if (!blockName.contains("glass") && !blockName.contains("pane")) continue;
                        Vec3 directionToWindow = new Vec3((double)checkPos.m_123341_() - player.getX(), 0.0, (double)checkPos.m_123343_() - player.getZ()).m_82541_();
                        BlockPos spawnPos = checkPos.offset((int)Math.round(directionToWindow.x * 1.5), 0, (int)Math.round(directionToWindow.z * 1.5));
                        if (level.getBlockState(spawnPos).m_280555_() || !level.getBlockState(spawnPos.m_7495_()).m_280555_() || (demon = (VerityDemonEntity)((EntityType)ModEntities.VERITY_DEMON_ENTITY.get()).m_20615_(level)) == null) continue;
                        demon.variantArea((double)spawnPos.m_123341_() + 0.5, (double)spawnPos.m_123342_(), (double)spawnPos.m_123343_() + 0.5, 0.0f, 0.0f);
                        demon.getLookControl().setLookAt((Entity)player, 180.0f, 180.0f);
                        demon.setDemonState(0);
                        demon.setHuntPhase(0);
                        if (level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel)level;
                            serverLevel.m_8615_(18000L);
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
                if (level.getBlockState(checkPos).m_280555_() || !level.getBlockState(checkPos.m_7495_()).m_280555_()) continue;
                safeSpawnPos = checkPos;
                foundGround = true;
                break;
            }
            if (!foundGround) {
                safeSpawnPos = level.isStateAtPosition(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, targetPos);
            }
            if ((demon = (VerityDemonEntity)((EntityType)ModEntities.VERITY_DEMON_ENTITY.get()).m_20615_(level)) != null) {
                demon.variantArea((double)safeSpawnPos.m_123341_() + 0.5, (double)safeSpawnPos.m_123342_(), (double)safeSpawnPos.m_123343_() + 0.5, 0.0f, 0.0f);
                demon.setDemonState(0);
                demon.setHuntPhase(1);
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    serverLevel.m_8615_(18000L);
                }
                level.destroyBlock((Entity)demon);
                player.isColliding("verity_window_scare_done");
            }
        }
    }
}

