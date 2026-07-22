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
import varmite.verity.network.ModNetwork;
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
        return Component.getContents((String)name);
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide()) {
            if (entity.onGround()) {
                CompoundTag data = entity.getPersistentData();
                int groundTicks = data.m_128451_("VerityGroundTicks");
                data.m_128405_("VerityGroundTicks", ++groundTicks);
                if (groundTicks >= 20) {
                    VerityEntity spawnedEntity;
                    ServerLevel level = (ServerLevel)entity.level();
                    BlockPos pos = entity.blockPosition();
                    String variantToSpawn = "happy";
                    if (stack.m_41782_() && stack.m_41783_().m_128441_("VerityVariant")) {
                        variantToSpawn = stack.m_41783_().m_128461_("VerityVariant");
                    }
                    if ((spawnedEntity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).m_20615_((Level)level)) != null) {
                        spawnedEntity.variantArea((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, 0.0f, 0.0f);
                        spawnedEntity.setVariant(variantToSpawn);
                        if (stack.m_41782_() && stack.m_41782_()) {
                            spawnedEntity.hurt(stack.m_41783_());
                        }
                        level.destroyBlock((Entity)spawnedEntity);
                    }
                    level.createTick(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);
                    entity.discard();
                    return true;
                }
            } else if (entity.getPersistentData().m_128441_("VerityGroundTicks")) {
                entity.getPersistentData().m_128405_("VerityGroundTicks", 0);
            }
            if (entity.blockPosition().m_123342_() <= -63) {
                entity.m_20334_(0.0, 1.0, 0.0);
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
                VerityEntity spawnedEntity = (VerityEntity)((EntityType)ModEntities.VERITY_ENTITY.get()).m_20615_((Level)serverLevel);
                if (spawnedEntity != null) {
                    spawnedEntity.variantArea((double)safePos.m_123341_() + 0.5, (double)safePos.m_123342_(), (double)safePos.m_123343_() + 0.5, 0.0f, 0.0f);
                    serverLevel.destroyBlock((Entity)spawnedEntity);
                    spawnedEntity.setVariant("serious_1");
                    spawnedEntity.level().createTick(null, safePos, (SoundEvent)ModSounds.BONE_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> spawnedEntity), (Object)new PlayTtsPayload(spawnedEntity.getId(), "DO NOT DO THAT!"));
                    serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)-1.0f));
                    data = WorldSpawnData.get((ServerLevel)serverLevel);
                    serverLevel.createTick(null, spawnedEntity.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0f, 0.8f);
                    if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                        return;
                    }
                    spawnedEntity.getServer().m_6846_().m_240416_((Component)Component.getContents((String)"<%s> \u00a74DO NOT DO THAT.".formatted(VerityConfig.VERITY_CUSTOM_NAME.get())), false);
                }
            }
        } else if (damageSource.getFoodExhaustion(DamageTypeTags.IS_EXPLOSION)) {
            ServerLevel serverLevel;
            Player nearestPlayer2;
            Level itemPos = itemEntity.level();
            if (itemPos instanceof ServerLevel && (nearestPlayer2 = (serverLevel = (ServerLevel)itemPos).getEntities((Entity)itemEntity, 256.0)) instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer2;
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = stack.m_41784_();
                tag.m_128359_("VerityVariant", "serious_3");
                p.m_150109_().add(stack);
                p.m_213846_((Component)Component.getContents((String)"<Verity> Ayo chat why u let me explode"));
                ModNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), (Object)new PlayTtsPayload(p.getId(), "Ayo chat why u let me explode"));
                serverLevel.getServer().execute(() -> ModEvents.updateAndSyncKarma((ServerLevel)serverLevel, (float)-1.0f));
                data = WorldSpawnData.get((ServerLevel)serverLevel);
                serverLevel.createTick(null, p.blockPosition(), SoundEvents.GHAST_SCREAM, SoundSource.PLAYERS, 1.0f, 1.3f);
            }
        } else if (damageSource.getFoodExhaustion(DamageTypes.CACTUS) && (nearestPlayer2 = itemEntity.level()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)nearestPlayer2;
            if ((nearestPlayer2 = serverLevel.getEntities((Entity)itemEntity, 256.0)) instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer2;
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = stack.m_41784_();
                tag.m_128359_("VerityVariant", "serious_3");
                p.m_150109_().add(stack);
                p.m_213846_((Component)Component.getContents((String)"<Verity> DON'T DO THAT."));
                ModNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), (Object)new PlayTtsPayload(p.getId(), "DO NOT DO THAT!"));
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
                    boolean hasSolidFloor = level.getBlockState(checkPos.m_7495_()).m_60804_((BlockGetter)level, checkPos.m_7495_());
                    boolean isFeetEmpty = level.getBlockState(checkPos).m_60812_((BlockGetter)level, checkPos).min() && level.getFluidState(checkPos).m_76178_();
                    boolean bl = isHeadEmpty = level.getBlockState(checkPos.m_7494_()).m_60812_((BlockGetter)level, checkPos.m_7494_()).min() && level.getFluidState(checkPos.m_7494_()).m_76178_();
                    if (!hasSolidFloor || !isFeetEmpty || !isHeadEmpty || !((distanceSqr = startPos.m_123331_((Vec3i)checkPos)) < minDistanceSqr)) continue;
                    minDistanceSqr = distanceSqr;
                    closestSafePos = checkPos;
                }
            }
        }
        return closestSafePos != null ? closestSafePos : startPos;
    }
}

