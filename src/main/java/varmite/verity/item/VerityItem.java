/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.network.PacketDistributor
 *  varmite.verity.VerityConfig
 *  varmite.verity.entity.ModEntities
 *  varmite.verity.entity.custom.VerityEntity
 *  varmite.verity.event.ModEvents
 *  varmite.verity.event.WorldSpawnData
 *  varmite.verity.item.ModItems
 *  varmite.verity.item.VerityItem
 *  varmite.verity.network.ModNetwork
 *  varmite.verity.network.PlayTtsPayload
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.item;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.item.ModItems;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

public class VerityItem
extends Item {
    private Player karmaPlayer;
    public static WorldSpawnData data;

    public VerityItem(Item.Properties properties) {
        super(properties);
        properties.food(1);
    }

    public boolean isDamageable(ItemStack stack) {
        return super.isDamageable(stack);
    }

    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        this.karmaPlayer = player;
        return super.onDroppedByPlayer(item, player);
    }

    public Component getName(ItemStack stack) {
        Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
        if (name == null || ((String)name).isBlank()) {
            name = "Verity";
        }
        if (!((String)name).endsWith("\u2122")) {
            name = (String)name + "\u2122";
        }
        return Component.literal(name);
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide()) {
            if (entity.onGround()) {
                CompoundTag data = entity.getPersistentData();
                int groundTicks = data.getInt("VerityGroundTicks");
                data.putInt("VerityGroundTicks", ++groundTicks);
                if (groundTicks >= 20) {
                    VerityEntity spawnedEntity;
                    ServerLevel level = (ServerLevel)entity.level();
                    BlockPos pos = entity.blockPosition();
                    String variantToSpawn = "happy";
                    if (stack.hasTag() && stack.getTag().contains("VerityVariant")) {
                        variantToSpawn = stack.getTag().getString("VerityVariant");
                    }
                    if ((spawnedEntity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create((Level)level)) != null) {
                        spawnedEntity.variantArea((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
                        spawnedEntity.setVariant(variantToSpawn);
                        if (stack.hasTag() && stack.hasTag()) {
                            spawnedEntity.hurt(stack.getTag());
                        }
                        level.destroyBlock((Entity)spawnedEntity);
                    }
                    level.createTick(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);
                    entity.discard();
                    return true;
                }
            } else if (entity.getPersistentData().contains("VerityGroundTicks")) {
                entity.getPersistentData().putInt("VerityGroundTicks", 0);
            }
            if (entity.blockPosition().getY() <= -63) {
                entity.setDeltaMovement(0.0, 1.0, 0.0);
                entity.hasImpulse = true;
            }
        }
        return false;
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions)new IClientItemExtensions() {});
    }

    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        Level nearestPlayer2;
        if (damageSource.getFoodExhaustion(DamageTypeTags.IS_FIRE)) {
            Level level = itemEntity.level();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                BlockPos itemPos = itemEntity.blockPosition();
                BlockPos safePos = this.findClosestSafeSpawnLocation(serverLevel, itemPos);
                VerityEntity spawnedEntity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create((Level)serverLevel);
                if (spawnedEntity != null) {
                    spawnedEntity.variantArea((double)safePos.getX() + 0.5, (double)safePos.getY(), (double)safePos.getZ() + 0.5, 0.0f, 0.0f);
                    serverLevel.destroyBlock((Entity)spawnedEntity);
                    spawnedEntity.setVariant("serious_1");
                    spawnedEntity.level().createTick(null, safePos, (SoundEvent)ModSounds.BONE_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(spawnedEntity, new PlayTtsPayload(spawnedEntity.getId(), "DO NOT DO THAT!"));
                    serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)-1.0f));
                    data = WorldSpawnData.get((ServerLevel)serverLevel);
                    serverLevel.createTick(null, spawnedEntity.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0f, 0.8f);
                    if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                        return;
                    }
                    spawnedEntity.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal("<%s> \u00a74DO NOT DO THAT.".formatted(VerityConfig.VERITY_CUSTOM_NAME.get())), false);
                }
            }
        } else if (damageSource.getFoodExhaustion(DamageTypeTags.IS_EXPLOSION)) {
            ServerLevel serverLevel;
            Player nearestPlayer2;
            Level itemPos = itemEntity.level();
            if (itemPos instanceof ServerLevel && (nearestPlayer2 = (serverLevel = (ServerLevel)itemPos).getEntities((Entity)itemEntity, 256.0)) instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer2;
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = stack.getOrCreateTag();
                tag.putString("VerityVariant", "serious_3");
                p.getInventory().add(stack);
                p.sendSystemMessage((Component)Component.literal("<Verity> Ayo chat why u let me explode"));
                PacketDistributor.sendToPlayer(p, new PlayTtsPayload(p.getId(), "Ayo chat why u let me explode"));
                serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)-1.0f));
                data = WorldSpawnData.get((ServerLevel)serverLevel);
                serverLevel.createTick(null, p.blockPosition(), SoundEvents.GHAST_SCREAM, SoundSource.PLAYERS, 1.0f, 1.3f);
            }
        } else if (damageSource.getFoodExhaustion(DamageTypes.CACTUS) && (nearestPlayer2 = itemEntity.level()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)nearestPlayer2;
            if ((nearestPlayer2 = serverLevel.getEntities((Entity)itemEntity, 256.0)) instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer2;
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = stack.getOrCreateTag();
                tag.putString("VerityVariant", "serious_3");
                p.getInventory().add(stack);
                p.sendSystemMessage((Component)Component.literal("<Verity> DON'T DO THAT."));
                PacketDistributor.sendToPlayer(p, new PlayTtsPayload(p.getId(), "DO NOT DO THAT!"));
                serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)-1.0f));
                data = WorldSpawnData.get((ServerLevel)serverLevel);
                serverLevel.createTick(null, p.blockPosition(), (SoundEvent)ModSounds.BONE_0.get(), SoundSource.PLAYERS, 1.0f, 0.8f);
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
                    boolean isFeetEmpty = level.getBlockState(checkPos).getCollisionShape((BlockGetter)level, checkPos).min() && level.getFluidState(checkPos).isEmpty();
                    boolean bl = isHeadEmpty = level.getBlockState(checkPos.above()).getCollisionShape((BlockGetter)level, checkPos.above()).min() && level.getFluidState(checkPos.above()).isEmpty();
                    if (!hasSolidFloor || !isFeetEmpty || !isHeadEmpty || !((distanceSqr = startPos.distSqr((Vec3i)checkPos)) < minDistanceSqr)) continue;
                    minDistanceSqr = distanceSqr;
                    closestSafePos = checkPos;
                }
            }
        }
        return closestSafePos != null ? closestSafePos : startPos;
    }
}

