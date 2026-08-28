/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package varmite.verity.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.SOUND_EVENTS, (String)"verity");
    public static final RegistryObject<SoundEvent> BOX_OPEN = ModSounds.registerSoundEvent("box_open");
    public static final RegistryObject<SoundEvent> BOX_CLICK = ModSounds.registerSoundEvent("box_click");
    public static final RegistryObject<SoundEvent> BOX_VERITY_0 = ModSounds.registerSoundEvent("box_verity_0");
    public static final RegistryObject<SoundEvent> BOX_VERITY_1 = ModSounds.registerSoundEvent("box_verity_1");
    public static final RegistryObject<SoundEvent> BOX_VERITY_2 = ModSounds.registerSoundEvent("box_verity_2");
    public static final RegistryObject<SoundEvent> INTRODUCTION = ModSounds.registerSoundEvent("intro");
    public static final RegistryObject<SoundEvent> IMPACT_0 = ModSounds.registerSoundEvent("impact_0");
    public static final RegistryObject<SoundEvent> IMPACT_1 = ModSounds.registerSoundEvent("impact_1");
    public static final RegistryObject<SoundEvent> IMPACT_2 = ModSounds.registerSoundEvent("impact_2");
    public static final RegistryObject<SoundEvent> INTRO_VIDEO_AUDIO = ModSounds.registerSoundEvent("intro_video_audio");
    public static final RegistryObject<SoundEvent> CHASE = ModSounds.registerSoundEvent("chase");
    public static final RegistryObject<SoundEvent> BONE_0 = ModSounds.registerSoundEvent("bone_snap");
    public static final RegistryObject<SoundEvent> BONE_1 = ModSounds.registerSoundEvent("bone_break");
    public static final RegistryObject<SoundEvent> JUMPSCARE = ModSounds.registerSoundEvent("jumpscare");
    public static final RegistryObject<SoundEvent> VERITY_DISC_SOUND = SOUND_EVENTS.register("verity_disc", () -> SoundEvent.m_262824_((ResourceLocation)new ResourceLocation("verity", "verity_disc")));
    public static final RegistryObject<SoundEvent> VERITY_EDIT_DISC_SOUND = SOUND_EVENTS.register("verity_edit_disc", () -> SoundEvent.m_262824_((ResourceLocation)new ResourceLocation("verity", "verity_edit_disc")));

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.m_262824_((ResourceLocation)new ResourceLocation("verity", name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

