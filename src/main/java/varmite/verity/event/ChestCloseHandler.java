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
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.neoforged.neoforge.event.entity.player.PlayerContainerEvent$Close
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  varmite.verity.event.ChestCloseHandler
 *  varmite.verity.event.VeritySpawnScheduler
 *  varmite.verity.item.ModItems
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.event.VeritySpawnScheduler;
import varmite.verity.item.ModItems;

@Mod.EventBusSubscriber(modid="verity")
public class ChestCloseHandler {
    @SubscribeEvent
    public static void onChestClose(PlayerContainerEvent.Close event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        AbstractContainerMenu abstractContainerMenu = event.getContainer();
        if (abstractContainerMenu instanceof ChestMenu) {
            ChestMenu chestMenu = (ChestMenu)abstractContainerMenu;
            boolean foundVerityItem = false;
            BlockPos chestPos = null;
            if (event.getContainer().stillValid() != MenuType.GENERIC_9x3 && event.getContainer().stillValid() != MenuType.GENERIC_9x6) {
                return;
            }
            if (chestMenu.getContainer() instanceof PlayerEnderChestContainer) {
                return;
            }
            if (chestMenu.getContainer() instanceof ContainerEntity) {
                return;
            }
            for (Slot slot : chestMenu.slots) {
                Container container;
                if (slot.container == event.getEntity().m_150109_() || !slot.getItem().m_150930_((Item)ModItems.VERITY_ITEM.get())) continue;
                foundVerityItem = true;
                slot.getItem(ItemStack.EMPTY);
                slot.setByPlayer();
                if (chestPos != null || !((container = slot.container) instanceof BlockEntity)) continue;
                BlockEntity be = (BlockEntity)container;
                chestPos = be.getBlockPos();
            }
            if (foundVerityItem && chestPos != null) {
                VeritySpawnScheduler.scheduleSpawn((Level)event.getEntity().level(), chestPos, (int)0);
            }
        }
    }
}

