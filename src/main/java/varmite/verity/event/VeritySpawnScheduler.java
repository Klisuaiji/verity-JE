/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.event.TickEvent$Phase
 *  net.neoforged.neoforge.event.tick.ServerTickEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.network.PacketDistributor
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.event.VeritySpawnScheduler
 *  varmite.verity.event.VeritySpawnScheduler$ScheduledSpawn
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.network.PlayTtsPayload
 */
package varmite.verity.event;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.entity.ModEntities;
import varmite.verity.event.VeritySpawnScheduler;
import varmite.verity.network.PlayTtsPayload;

import net.neoforged.neoforge.event.tick.ServerTickEvent;
/*
 * Exception performing whole class analysis ignored.
 */
@EventBusSubscriber(modid="verity")
public class VeritySpawnScheduler {
    private static final List<ScheduledSpawn> SCHEDULED_SPAWNS = new ArrayList();

    public static void scheduleSpawn(Level level, BlockPos pos, int delayTicks) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            long executeAt = serverLevel.getServer().getTickCount() + delayTicks;
            SCHEDULED_SPAWNS.add(new ScheduledSpawn(serverLevel, pos, executeAt));
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event) {
        long currentTick = event.getServer().getTickCount();
        Iterator iterator = SCHEDULED_SPAWNS.iterator();
        while (iterator.hasNext()) {
            ScheduledSpawn task = (ScheduledSpawn)iterator.next();
            if (currentTick < task.executeTick) continue;
            VeritySpawnScheduler.executeVerityEvent((ServerLevel)task.level, (BlockPos)task.pos);
            iterator.remove();
        }
    }

    private static void executeVerityEvent(ServerLevel level, BlockPos chestPos) {
        Entity verity;
        BlockPos abovePos = chestPos.above();
        if (!level.getBlockState(abovePos).isAir()) {
            level.destroyBlock(abovePos, true);
        }
        if ((verity = ((EntityType)ModEntities.VERITY_ENTITY.get()).create((Level)level)) != null) {
            verity.variantArea((double)chestPos.getX() + 0.5, (double)chestPos.getY() + 1.0, (double)chestPos.getZ() + 0.5, 0.0f, 0.0f);
            level.destroyBlock(verity);
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(verity, new PlayTtsPayload(verity.getId(), "You can't trap me lil bro."));
            level.createTick(null, verity.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }


    private static class ScheduledSpawn {
        final ServerLevel level;
        final BlockPos pos;
        final long executeTick;

        ScheduledSpawn(ServerLevel level, BlockPos pos, long executeTick) {
            this.level = level;
            this.pos = pos;
            this.executeTick = executeTick;
        }
    }

}
