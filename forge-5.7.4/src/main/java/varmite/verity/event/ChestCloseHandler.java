/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.vehicle.ContainerEntity
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ChestMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.PlayerEnderChestContainer
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.event.entity.player.PlayerContainerEvent$Close
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package varmite.verity.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import varmite.verity.event.VeritySpawnScheduler;
import varmite.verity.item.ModItems;

@Mod.EventBusSubscriber(modid="verity")
public class ChestCloseHandler {
    @SubscribeEvent
    public static void onChestClose(PlayerContainerEvent.Close event) {
        if (event.getEntity().m_9236_().m_5776_()) {
            return;
        }
        AbstractContainerMenu abstractContainerMenu = event.getContainer();
        if (abstractContainerMenu instanceof ChestMenu) {
            ChestMenu chestMenu = (ChestMenu)abstractContainerMenu;
            boolean foundVerityItem = false;
            BlockPos chestPos = null;
            if (event.getContainer().m_6772_() != MenuType.f_39959_ && event.getContainer().m_6772_() != MenuType.f_39962_) {
                return;
            }
            if (chestMenu.m_39261_() instanceof PlayerEnderChestContainer) {
                return;
            }
            if (chestMenu.m_39261_() instanceof ContainerEntity) {
                return;
            }
            for (Slot slot : chestMenu.f_38839_) {
                Container container;
                if (slot.f_40218_ == event.getEntity().m_150109_() || !slot.m_7993_().m_150930_((Item)ModItems.VERITY_ITEM.get())) continue;
                foundVerityItem = true;
                slot.m_5852_(ItemStack.f_41583_);
                slot.m_6654_();
                if (chestPos != null || !((container = slot.f_40218_) instanceof BlockEntity)) continue;
                BlockEntity be = (BlockEntity)container;
                chestPos = be.m_58899_();
            }
            if (foundVerityItem && chestPos != null) {
                VeritySpawnScheduler.scheduleSpawn(event.getEntity().m_9236_(), chestPos, 0);
            }
        }
    }
}

