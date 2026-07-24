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
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.item.ModItems;
import varmite.verity.item.client.VerityItemRenderer;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

public class VerityItem
extends Item {
    private Player karmaPlayer;
    public static WorldSpawnData data;

    public VerityItem(Item.Properties properties) {
        super(properties.food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.6f).build()));
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
        String name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
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
                    if (stack.has(DataComponents.CUSTOM_DATA)) {
                        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                        if (tag != null && tag.contains("VerityVariant")) {
                            variantToSpawn = tag.getString("VerityVariant");
                        }
                    }
                    if ((spawnedEntity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create((Level)level)) != null) {
                        spawnedEntity.variantArea((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
                        spawnedEntity.setVariant(variantToSpawn);
                        if (stack.has(DataComponents.CUSTOM_DATA)) {
                            CompoundTag stackTag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                            if (stackTag != null) {
                                spawnedEntity.load(stackTag);
                            }
                        }
                        level.destroyBlock(spawnedEntity.blockPosition(), false);
                    }
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);
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
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new VerityItemRenderer();
            }
        });
    }

    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_FIRE)) {
            Level level = itemEntity.level();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                BlockPos itemPos = itemEntity.blockPosition();
                BlockPos safePos = this.findClosestSafeSpawnLocation(serverLevel, itemPos);
                VerityEntity spawnedEntity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).create((Level)serverLevel);
                if (spawnedEntity != null) {
                    spawnedEntity.variantArea((double)safePos.getX() + 0.5, (double)safePos.getY(), (double)safePos.getZ() + 0.5, 0.0f, 0.0f);
                    serverLevel.destroyBlock(spawnedEntity.blockPosition(), false);
                    spawnedEntity.setVariant("serious_1");
                    spawnedEntity.level().playSound(null, safePos, (SoundEvent)ModSounds.BONE_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(spawnedEntity, new PlayTtsPayload(spawnedEntity.getId(), "DO NOT DO THAT!"));
                    serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)-1.0f));
                    data = WorldSpawnData.get((ServerLevel)serverLevel);
                    serverLevel.playSound(null, spawnedEntity.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0f, 0.8f);
                    if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                        return;
                    }
                    spawnedEntity.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("verity.msg.do_not_do_that", VerityConfig.VERITY_CUSTOM_NAME.get()), false);
                }
            }
        } else if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
            if (itemEntity.level() instanceof ServerLevel serverLevel) {
                Player nearestPlayer = serverLevel.getNearestPlayer(itemEntity, 256.0);
                if (nearestPlayer instanceof ServerPlayer p) {
                    ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                    CompoundTag tag = new CompoundTag();
                    tag.putString("VerityVariant", "serious_3");
                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                    p.getInventory().add(stack);
                    p.sendSystemMessage((Component)Component.translatable("verity.msg.explode_chat", VerityConfig.VERITY_CUSTOM_NAME.get()));
                    PacketDistributor.sendToPlayer(p, new PlayTtsPayload(p.getId(), "Ayo chat why u let me explode"));
                    serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma(serverLevel, -1.0f));
                    data = WorldSpawnData.get(serverLevel);
                    serverLevel.playSound(null, p.blockPosition(), SoundEvents.GHAST_SCREAM, SoundSource.PLAYERS, 1.0f, 1.3f);
                }
            }
        } else if (damageSource.is(DamageTypes.CACTUS) && itemEntity.level() instanceof ServerLevel serverLevel) {
            Player nearestPlayer = serverLevel.getNearestPlayer(itemEntity, 256.0);
            if (nearestPlayer instanceof ServerPlayer p) {
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = new CompoundTag();
                tag.putString("VerityVariant", "serious_3");
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                p.getInventory().add(stack);
                p.sendSystemMessage((Component)Component.translatable("verity.msg.dont_do_that", VerityConfig.VERITY_CUSTOM_NAME.get()));
                PacketDistributor.sendToPlayer(p, new PlayTtsPayload(p.getId(), "DO NOT DO THAT!"));
                serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma(serverLevel, -1.0f));
                data = WorldSpawnData.get(serverLevel);
                serverLevel.playSound(null, p.blockPosition(), (SoundEvent)ModSounds.BONE_0.get(), SoundSource.PLAYERS, 1.0f, 0.8f);
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
                    isHeadEmpty = level.getBlockState(checkPos.above()).getCollisionShape((BlockGetter)level, checkPos.above()).isEmpty() && level.getFluidState(checkPos.above()).isEmpty();
                    if (!hasSolidFloor || !isFeetEmpty || !isHeadEmpty || !((distanceSqr = startPos.distSqr((Vec3i)checkPos)) < minDistanceSqr)) continue;
                    minDistanceSqr = distanceSqr;
                    closestSafePos = checkPos;
                }
            }
        }
        return closestSafePos != null ? closestSafePos : startPos;
    }
}

