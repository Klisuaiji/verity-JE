/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.network.PacketDistributor
 */
package varmite.verity.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import varmite.verity.entity.ModEntities;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;

@Mod.EventBusSubscriber(modid="verity")
public class VeritySpawnScheduler {
    private static final List<ScheduledSpawn> SCHEDULED_SPAWNS = new ArrayList<ScheduledSpawn>();

    public static void scheduleSpawn(Level level, BlockPos pos, int delayTicks) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            long executeAt = serverLevel.m_7654_().m_129921_() + delayTicks;
            SCHEDULED_SPAWNS.add(new ScheduledSpawn(serverLevel, pos, executeAt));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        long currentTick = event.getServer().m_129921_();
        Iterator<ScheduledSpawn> iterator = SCHEDULED_SPAWNS.iterator();
        while (iterator.hasNext()) {
            ScheduledSpawn task = iterator.next();
            if (currentTick < task.executeTick) continue;
            VeritySpawnScheduler.executeVerityEvent(task.level, task.pos);
            iterator.remove();
        }
    }

    private static void executeVerityEvent(ServerLevel level, BlockPos chestPos) {
        Entity verity;
        BlockPos abovePos = chestPos.m_7494_();
        if (!level.m_8055_(abovePos).m_60795_()) {
            level.m_46961_(abovePos, true);
        }
        if ((verity = ModEntities.VERITY_ENTITY.get().m_20615_((Level)level)) != null) {
            verity.m_7678_((double)chestPos.m_123341_() + 0.5, (double)chestPos.m_123342_() + 1.0, (double)chestPos.m_123343_() + 0.5, 0.0f, 0.0f);
            level.m_7967_(verity);
            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verity), (Object)new PlayTtsPayload(verity.m_19879_(), "You can't trap me lil bro."));
            level.m_5594_(null, verity.m_20183_(), SoundEvents.f_11749_, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    private record ScheduledSpawn(ServerLevel level, BlockPos pos, long executeTick) {
    }
}

