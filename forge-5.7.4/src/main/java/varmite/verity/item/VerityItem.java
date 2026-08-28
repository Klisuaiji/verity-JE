/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
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
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 *  net.minecraftforge.network.PacketDistributor
 */
package varmite.verity.item;

import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.ModEvents;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.item.ModItems;
import varmite.verity.item.client.VerityItemRenderer;
import varmite.verity.network.ModNetwork;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

public class VerityItem
extends Item {
    private Player karmaPlayer;
    public static WorldSpawnData data;

    public VerityItem(Item.Properties properties) {
        super(properties);
        properties.m_41487_(1);
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

    public Component m_7626_(ItemStack stack) {
        Object name = (String)VerityConfig.VERITY_CUSTOM_NAME.get();
        if (name == null || ((String)name).isBlank()) {
            name = "Verity";
        }
        if (!((String)name).endsWith("\u2122")) {
            name = (String)name + "\u2122";
        }
        return Component.m_237113_((String)name);
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.m_9236_().m_5776_()) {
            if (entity.m_20096_()) {
                CompoundTag data = entity.getPersistentData();
                int groundTicks = data.m_128451_("VerityGroundTicks");
                data.m_128405_("VerityGroundTicks", ++groundTicks);
                if (groundTicks >= 20) {
                    VerityEntity spawnedEntity;
                    ServerLevel level = (ServerLevel)entity.m_9236_();
                    BlockPos pos = entity.m_20183_();
                    String variantToSpawn = "happy";
                    if (stack.m_41782_() && stack.m_41783_().m_128441_("VerityVariant")) {
                        variantToSpawn = stack.m_41783_().m_128461_("VerityVariant");
                    }
                    if ((spawnedEntity = (VerityEntity)ModEntities.VERITY_ENTITY.get().m_20615_((Level)level)) != null) {
                        spawnedEntity.m_7678_((double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5, 0.0f, 0.0f);
                        spawnedEntity.setVariant(variantToSpawn);
                        if (stack.m_41782_() && stack.m_41782_()) {
                            spawnedEntity.m_7378_(stack.m_41783_());
                        }
                        level.m_7967_((Entity)spawnedEntity);
                    }
                    level.m_5594_(null, pos, SoundEvents.f_12019_, SoundSource.BLOCKS, 1.0f, 0.8f);
                    entity.m_146870_();
                    return true;
                }
            } else if (entity.getPersistentData().m_128441_("VerityGroundTicks")) {
                entity.getPersistentData().m_128405_("VerityGroundTicks", 0);
            }
            if (entity.m_20183_().m_123342_() <= -63) {
                entity.m_20334_(0.0, 1.0, 0.0);
                entity.f_19812_ = true;
            }
        }
        return false;
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions(){

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new VerityItemRenderer();
            }
        });
    }

    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        Level nearestPlayer2;
        if (damageSource.m_269533_(DamageTypeTags.f_268745_)) {
            Level level = itemEntity.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                BlockPos itemPos = itemEntity.m_20183_();
                BlockPos safePos = this.findClosestSafeSpawnLocation(serverLevel, itemPos);
                VerityEntity spawnedEntity = (VerityEntity)ModEntities.VERITY_ENTITY.get().m_20615_((Level)serverLevel);
                if (spawnedEntity != null) {
                    spawnedEntity.m_7678_((double)safePos.m_123341_() + 0.5, safePos.m_123342_(), (double)safePos.m_123343_() + 0.5, 0.0f, 0.0f);
                    serverLevel.m_7967_((Entity)spawnedEntity);
                    spawnedEntity.setVariant("serious_1");
                    spawnedEntity.m_9236_().m_5594_(null, safePos, (SoundEvent)ModSounds.BONE_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> spawnedEntity), (Object)new PlayTtsPayload(spawnedEntity.m_19879_(), "DO NOT DO THAT!"));
                    serverLevel.m_7654_().execute(() -> ModEvents.updateAndSyncKarma(serverLevel, -1.0f));
                    data = WorldSpawnData.get(serverLevel);
                    serverLevel.m_5594_(null, spawnedEntity.m_20183_(), SoundEvents.f_12031_, SoundSource.PLAYERS, 1.0f, 0.8f);
                    if (((Boolean)VerityConfig.IMMERSIVE_MODE.get()).booleanValue()) {
                        return;
                    }
                    spawnedEntity.m_20194_().m_6846_().m_240416_((Component)Component.m_237113_((String)"<%s> \u00a74DO NOT DO THAT.".formatted(VerityConfig.VERITY_CUSTOM_NAME.get())), false);
                }
            }
        } else if (damageSource.m_269533_(DamageTypeTags.f_268415_)) {
            ServerLevel serverLevel;
            Player nearestPlayer2;
            Level itemPos = itemEntity.m_9236_();
            if (itemPos instanceof ServerLevel && (nearestPlayer2 = (serverLevel = (ServerLevel)itemPos).m_45930_((Entity)itemEntity, 256.0)) instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer2;
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = stack.m_41784_();
                tag.m_128359_("VerityVariant", "serious_3");
                p.m_150109_().m_36054_(stack);
                p.m_213846_((Component)Component.m_237113_((String)"<Verity> Ayo chat why u let me explode"));
                ModNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), (Object)new PlayTtsPayload(p.m_19879_(), "Ayo chat why u let me explode"));
                serverLevel.m_7654_().execute(() -> ModEvents.updateAndSyncKarma(serverLevel, -1.0f));
                data = WorldSpawnData.get(serverLevel);
                serverLevel.m_5594_(null, p.m_20183_(), SoundEvents.f_11922_, SoundSource.PLAYERS, 1.0f, 1.3f);
            }
        } else if (damageSource.m_276093_(DamageTypes.f_268585_) && (nearestPlayer2 = itemEntity.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)nearestPlayer2;
            if ((nearestPlayer2 = serverLevel.m_45930_((Entity)itemEntity, 256.0)) instanceof ServerPlayer) {
                ServerPlayer p = (ServerPlayer)nearestPlayer2;
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CompoundTag tag = stack.m_41784_();
                tag.m_128359_("VerityVariant", "serious_3");
                p.m_150109_().m_36054_(stack);
                p.m_213846_((Component)Component.m_237113_((String)"<Verity> DON'T DO THAT."));
                ModNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), (Object)new PlayTtsPayload(p.m_19879_(), "DO NOT DO THAT!"));
                serverLevel.m_7654_().execute(() -> ModEvents.updateAndSyncKarma(serverLevel, -1.0f));
                data = WorldSpawnData.get(serverLevel);
                serverLevel.m_5594_(null, p.m_20183_(), (SoundEvent)ModSounds.BONE_0.get(), SoundSource.PLAYERS, 1.0f, 0.8f);
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
                    BlockPos checkPos = startPos.m_7918_(x, y, z);
                    boolean hasSolidFloor = level.m_8055_(checkPos.m_7495_()).m_60804_((BlockGetter)level, checkPos.m_7495_());
                    boolean isFeetEmpty = level.m_8055_(checkPos).m_60812_((BlockGetter)level, checkPos).m_83281_() && level.m_6425_(checkPos).m_76178_();
                    boolean bl = isHeadEmpty = level.m_8055_(checkPos.m_7494_()).m_60812_((BlockGetter)level, checkPos.m_7494_()).m_83281_() && level.m_6425_(checkPos.m_7494_()).m_76178_();
                    if (!hasSolidFloor || !isFeetEmpty || !isHeadEmpty || !((distanceSqr = startPos.m_123331_((Vec3i)checkPos)) < minDistanceSqr)) continue;
                    minDistanceSqr = distanceSqr;
                    closestSafePos = checkPos;
                }
            }
        }
        return closestSafePos != null ? closestSafePos : startPos;
    }
}

