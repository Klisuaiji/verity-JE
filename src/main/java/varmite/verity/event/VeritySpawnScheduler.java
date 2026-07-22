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
 *  net.neoforged.neoforge.event.TickEvent$ServerTickEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.neoforge.network.PacketDistributor
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.event.VeritySpawnScheduler
 *  varmite.verity.event.VeritySpawnScheduler$ScheduledSpawn
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.network.PlayTtsPayload
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.entity.ModEntities;
import varmite.verity.event.VeritySpawnScheduler;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;

/*
 * Exception performing whole class analysis ignored.
 */
@Mod.EventBusSubscriber(modid="verity")
public class VeritySpawnScheduler {
    private static final List<ScheduledSpawn> SCHEDULED_SPAWNS = new ArrayList();

    public static void scheduleSpawn(Level level, BlockPos pos, int delayTicks) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            long executeAt = serverLevel.getServer().m_129921_() + delayTicks;
            SCHEDULED_SPAWNS.add(new ScheduledSpawn(serverLevel, pos, executeAt));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        long currentTick = event.getServer().m_129921_();
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
        BlockPos abovePos = chestPos.m_7494_();
        if (!level.getBlockState(abovePos).m_60795_()) {
            level.destroyBlock(abovePos, true);
        }
        if ((verity = ((EntityType)ModEntities.VERITY_ENTITY.get()).m_20615_((Level)level)) != null) {
            verity.variantArea((double)chestPos.m_123341_() + 0.5, (double)chestPos.m_123342_() + 1.0, (double)chestPos.m_123343_() + 0.5, 0.0f, 0.0f);
            level.destroyBlock(verity);
            ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> verity), (Object)new PlayTtsPayload(verity.getId(), "You can't trap me lil bro."));
            level.createTick(null, verity.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
}

