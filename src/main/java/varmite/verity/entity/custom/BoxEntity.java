/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.core.animation.AnimationController
 *  software.bernie.geckolib.core.animation.RawAnimation
 *  software.bernie.geckolib.core.object.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 *  varmite.verity.entity.custom.BoxEntity
 *  varmite.verity.sounds.ModSounds
 *  varmite.verity.triggers.ModTriggers
 */
package varmite.verity.entity.custom;

import java.util.Collections;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import varmite.verity.sounds.ModSounds;
import varmite.verity.triggers.ModTriggers;

public class BoxEntity
extends LivingEntity
implements GeoEntity {
    private static final Logger log = LoggerFactory.getLogger(BoxEntity.class);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private int delayTicks = -1;
    private int soundEffectTimer = 0;
    private int randInt = 0;
    private Player p;
    private SoundEvent sEvent;
    private BlockPos bPos;
    private SoundSource sSource;
    private float v;
    private float pPitch;
    private boolean isOpened = false;
    public static final EntityDataAccessor<Boolean> HAS_CLICKED = SynchedEntityData.defineId(BoxEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);

    public BoxEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.defineId(HAS_CLICKED, (Object)false);
    }

    public void m_8119_() {
        if (!this.level().isClientSide) {
            if (this.soundEffectTimer < 60) {
                ++this.soundEffectTimer;
            } else if (!this.isOpened) {
                this.soundEffectTimer = 0;
                if (this.randInt == 0) {
                    this.hurt((SoundEvent)ModSounds.BOX_VERITY_0.get(), 4.0f, 1.0f);
                } else if (this.randInt == 1) {
                    this.hurt((SoundEvent)ModSounds.BOX_VERITY_1.get(), 4.0f, 1.0f);
                } else {
                    this.hurt((SoundEvent)ModSounds.BOX_VERITY_2.get(), 4.0f, 1.0f);
                }
                ++this.randInt;
                if (this.randInt > 2) {
                    this.randInt = 0;
                }
            }
        }
        super.m_8119_();
    }

    public boolean hasCustomName() {
        return false;
    }

    public boolean m_6052_() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public void push(@NotNull Entity entity) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.0);
    }

    public void playDelayedSound(Player player, SoundEvent soundEvent, BlockPos blockPos, SoundSource source, float volume, float pitch) {
        this.sEvent = soundEvent;
        this.sSource = source;
        this.bPos = blockPos;
        this.v = volume;
        this.pPitch = pitch;
        this.delayTicks = -1;
    }

    public void triggerOpen() {
        this.triggerAnim("action_controller", "open");
        MinecraftServer server = this.getServer();
        for (ServerPlayer player : server.m_6846_().m_11314_()) {
            ModTriggers.UNBOX_VERITY_TRIGGER.trigger(player);
        }
        this.isOpened = true;
    }

    public boolean m_6673_(DamageSource source) {
        return !source.getFoodExhaustion(DamageTypes.FELL_OUT_OF_WORLD) && !source.getFoodExhaustion(DamageTypes.GENERIC_KILL);
    }

    public Iterable<ItemStack> m_6168_() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    public ItemStack m_6844_(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    public void m_8061_(EquipmentSlot slot, ItemStack stack) {
    }

    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "idle_controller", 5, state -> {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("hover"));
            return PlayState.CONTINUE;
        })});
        AnimationController actionController = new AnimationController((GeoAnimatable)this, "action_controller", 0, state -> PlayState.STOP);
        actionController.triggerableAnim("open", RawAnimation.begin().thenPlayAndHold("open"));
        controllers.add(new AnimationController[]{actionController});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

