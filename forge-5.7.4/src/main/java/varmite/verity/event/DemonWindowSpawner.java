/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package varmite.verity.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
        Level level = player.m_9236_();
        if (level.m_5776_()) {
            return;
        }
        if (level.m_46468_() < 120000L) {
            return;
        }
        if (player.m_19880_().contains("verity_window_scare_done")) {
            return;
        }
        if (level.m_46467_() % 100L != 0L) {
            return;
        }
        boolean bl = isIndoors = !level.m_45527_(player.m_20183_());
        if (isIndoors) {
            BlockPos playerPos = player.m_20183_();
            for (int x = -5; x <= 5; ++x) {
                for (int y = -1; y <= 2; ++y) {
                    for (int z = -5; z <= 5; ++z) {
                        VerityDemonEntity demon;
                        BlockPos checkPos = playerPos.m_7918_(x, y, z);
                        BlockState state = level.m_8055_(checkPos);
                        String blockName = state.m_60734_().m_7705_().toLowerCase();
                        if (!blockName.contains("glass") && !blockName.contains("pane")) continue;
                        Vec3 directionToWindow = new Vec3((double)checkPos.m_123341_() - player.m_20185_(), 0.0, (double)checkPos.m_123343_() - player.m_20189_()).m_82541_();
                        BlockPos spawnPos = checkPos.m_7918_((int)Math.round(directionToWindow.f_82479_ * 1.5), 0, (int)Math.round(directionToWindow.f_82481_ * 1.5));
                        if (level.m_8055_(spawnPos).m_280555_() || !level.m_8055_(spawnPos.m_7495_()).m_280555_() || (demon = (VerityDemonEntity)ModEntities.VERITY_DEMON_ENTITY.get().m_20615_(level)) == null) continue;
                        demon.m_7678_((double)spawnPos.m_123341_() + 0.5, spawnPos.m_123342_(), (double)spawnPos.m_123343_() + 0.5, 0.0f, 0.0f);
                        demon.m_21563_().m_24960_((Entity)player, 180.0f, 180.0f);
                        demon.setDemonState(0);
                        demon.setHuntPhase(0);
                        if (level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel)level;
                            serverLevel.m_8615_(18000L);
                        }
                        level.m_7967_((Entity)demon);
                        player.m_20049_("verity_window_scare_done");
                        return;
                    }
                }
            }
        } else {
            VerityDemonEntity demon;
            BlockPos targetPos;
            Vec3 lookVec = player.m_20154_();
            BlockPos safeSpawnPos = targetPos = BlockPos.m_274561_((double)(player.m_20185_() - lookVec.f_82479_ * 24.0), (double)player.m_20186_(), (double)(player.m_20189_() - lookVec.f_82481_ * 24.0));
            boolean foundGround = false;
            for (int yOffset = 5; yOffset >= -15; --yOffset) {
                BlockPos checkPos = targetPos.m_7918_(0, yOffset, 0);
                if (level.m_8055_(checkPos).m_280555_() || !level.m_8055_(checkPos.m_7495_()).m_280555_()) continue;
                safeSpawnPos = checkPos;
                foundGround = true;
                break;
            }
            if (!foundGround) {
                safeSpawnPos = level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, targetPos);
            }
            if ((demon = (VerityDemonEntity)ModEntities.VERITY_DEMON_ENTITY.get().m_20615_(level)) != null) {
                demon.m_7678_((double)safeSpawnPos.m_123341_() + 0.5, safeSpawnPos.m_123342_(), (double)safeSpawnPos.m_123343_() + 0.5, 0.0f, 0.0f);
                demon.setDemonState(0);
                demon.setHuntPhase(1);
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    serverLevel.m_8615_(18000L);
                }
                level.m_7967_((Entity)demon);
                player.m_20049_("verity_window_scare_done");
            }
        }
    }
}

