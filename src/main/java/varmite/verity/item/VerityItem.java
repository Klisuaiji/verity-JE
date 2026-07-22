/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package varmite.verity.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.item.ModItems;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

public class VerityItem
extends Item {
    public VerityItem(Item.Properties properties) {
        super(properties);
        properties.stacksTo(1);
    }

    public boolean isDamageable(ItemStack stack) {
        return super.isDamageable(stack);
    }

    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public int getDefaultMaxStackSize() {
        return 1;
    }

    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_FIRE)) {
            Level level = itemEntity.level();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                BlockPos itemPos = itemEntity.blockPosition();
                BlockPos safePos = this.findClosestSafeSpawnLocation(serverLevel, itemPos);
                VerityEntity spawnedEntity = (VerityEntity)ModEntities.VERITY_ENTITY.get().create(serverLevel);
                if (spawnedEntity != null) {
                    spawnedEntity.moveTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5, 0.0f, 0.0f);
                    serverLevel.addFreshEntity(spawnedEntity);
                    spawnedEntity.setVariant("serious_1");
                    spawnedEntity.level().playSound(null, safePos, ModSounds.BONE_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    spawnedEntity.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Verity> \u00a74DO NOT DO THAT."), false);
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(spawnedEntity, new PlayTtsPayload(spawnedEntity.getId(), "DO NOT DO THAT!"));
                }
            }
        } else if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
            Level itemPos = itemEntity.level();
            if (itemPos instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)itemPos;
                Player nearestPlayer = serverLevel.getNearestPlayer(itemEntity, 256.0);
                if (nearestPlayer instanceof ServerPlayer) {
                    ServerPlayer p = (ServerPlayer)nearestPlayer;
                    ItemStack stack = new ItemStack(ModItems.VERITY_ITEM.get());
                    CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("VerityVariant", "serious_3"));
                    p.getInventory().add(stack);
                    p.sendSystemMessage(Component.literal("<Verity> Ayo chat why u let me explode"));
                    PacketDistributor.sendToPlayer(p, new PlayTtsPayload(p.getId(), "Ayo chat why u let me explode"));
                    serverLevel.playSound(null, p.blockPosition(), SoundEvents.GHAST_SCREAM, SoundSource.PLAYERS, 1.0f, 1.3f);
                }
            }
        } else if (damageSource.is(DamageTypes.CACTUS) && itemEntity.level() instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)itemEntity.level();
            Player nearestPlayer = serverLevel.getNearestPlayer(itemEntity, 256.0);
            if (nearestPlayer instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer;
                ItemStack stack = new ItemStack(ModItems.VERITY_ITEM.get());
                CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("VerityVariant", "serious_3"));
                p.getInventory().add(stack);
                p.sendSystemMessage(Component.literal("<Verity> DON'T DO THAT."));
                PacketDistributor.sendToPlayer(p, new PlayTtsPayload(p.getId(), "DO NOT DO THAT!"));
                serverLevel.playSound(null, p.blockPosition(), ModSounds.BONE_0.get(), SoundSource.PLAYERS, 1.0f, 0.8f);
            } else {
                System.out.println("[VERITY DEBUG] Cactus destruction fired, but couldn't find a valid ServerPlayer nearby.");
            }
        }
        super.onDestroyed(itemEntity, damageSource);
    }

    private BlockPos findClosestSafeSpawnLocation(ServerLevel level, BlockPos startPos) {
        BlockPos closestSafePos = null;
        double minDistanceSqr = Double.MAX_VALUE;
        for (int x = -3; x <= 3; ++x) {
            for (int y = -3; y <= 3; ++y) {
                for (int z = -3; z <= 3; ++z) {
                    double distanceSqr;
                    boolean isHeadEmpty;
                    BlockPos checkPos = startPos.offset(x, y, z);
                    boolean hasSolidFloor = level.getBlockState(checkPos.below()).isSolidRender((BlockGetter)level, checkPos.below());
                    boolean isFeetEmpty = level.getBlockState(checkPos).getCollisionShape((BlockGetter)level, checkPos).isEmpty() && level.getFluidState(checkPos).isEmpty();
                    boolean bl = isHeadEmpty = level.getBlockState(checkPos.above()).getCollisionShape((BlockGetter)level, checkPos.above()).isEmpty() && level.getFluidState(checkPos.above()).isEmpty();
                    if (!hasSolidFloor || !isFeetEmpty || !isHeadEmpty || !((distanceSqr = startPos.distSqr((Vec3i)checkPos)) < minDistanceSqr)) continue;
                    minDistanceSqr = distanceSqr;
                    closestSafePos = checkPos;
                }
            }
        }
        return closestSafePos != null ? closestSafePos : startPos;
    }
}

