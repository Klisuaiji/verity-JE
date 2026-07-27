/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.sounds;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;
public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, "verity");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOX_OPEN = ModSounds.registerSoundEvent("box_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOX_CLICK = ModSounds.registerSoundEvent("box_click");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOX_VERITY_0 = ModSounds.registerSoundEvent("box_verity_0");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOX_VERITY_1 = ModSounds.registerSoundEvent("box_verity_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOX_VERITY_2 = ModSounds.registerSoundEvent("box_verity_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> INTRODUCTION = ModSounds.registerSoundEvent("intro");
    public static final DeferredHolder<SoundEvent, SoundEvent> IMPACT_0 = ModSounds.registerSoundEvent("impact_0");
    public static final DeferredHolder<SoundEvent, SoundEvent> IMPACT_1 = ModSounds.registerSoundEvent("impact_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> IMPACT_2 = ModSounds.registerSoundEvent("impact_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> INTRO_VIDEO_AUDIO = ModSounds.registerSoundEvent("intro_video_audio");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHASE = ModSounds.registerSoundEvent("chase");
    public static final DeferredHolder<SoundEvent, SoundEvent> BONE_0 = ModSounds.registerSoundEvent("bone_snap");
    public static final DeferredHolder<SoundEvent, SoundEvent> BONE_1 = ModSounds.registerSoundEvent("bone_break");
    public static final DeferredHolder<SoundEvent, SoundEvent> JUMPSCARE = ModSounds.registerSoundEvent("jumpscare");
    public static final DeferredHolder<SoundEvent, SoundEvent> VERITY_DISC_SOUND = SOUND_EVENTS.register("verity_disc", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("verity", "verity_disc")));
    public static final DeferredHolder<SoundEvent, SoundEvent> VERITY_EDIT_DISC_SOUND = SOUND_EVENTS.register("verity_edit_disc", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("verity", "verity_edit_disc")));

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("verity", name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

