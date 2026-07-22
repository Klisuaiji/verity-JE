/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.ForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 *  varmite.verity.sounds.ModSounds
 */
package varmite.verity.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.IForgeRegistry;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.SOUND_EVENTS, (String)"verity");
    public static final RegistryObject<SoundEvent> BOX_OPEN = ModSounds.registerSoundEvent((String)"box_open");
    public static final RegistryObject<SoundEvent> BOX_CLICK = ModSounds.registerSoundEvent((String)"box_click");
    public static final RegistryObject<SoundEvent> BOX_VERITY_0 = ModSounds.registerSoundEvent((String)"box_verity_0");
    public static final RegistryObject<SoundEvent> BOX_VERITY_1 = ModSounds.registerSoundEvent((String)"box_verity_1");
    public static final RegistryObject<SoundEvent> BOX_VERITY_2 = ModSounds.registerSoundEvent((String)"box_verity_2");
    public static final RegistryObject<SoundEvent> INTRODUCTION = ModSounds.registerSoundEvent((String)"intro");
    public static final RegistryObject<SoundEvent> IMPACT_0 = ModSounds.registerSoundEvent((String)"impact_0");
    public static final RegistryObject<SoundEvent> IMPACT_1 = ModSounds.registerSoundEvent((String)"impact_1");
    public static final RegistryObject<SoundEvent> IMPACT_2 = ModSounds.registerSoundEvent((String)"impact_2");
    public static final RegistryObject<SoundEvent> INTRO_VIDEO_AUDIO = ModSounds.registerSoundEvent((String)"intro_video_audio");
    public static final RegistryObject<SoundEvent> CHASE = ModSounds.registerSoundEvent((String)"chase");
    public static final RegistryObject<SoundEvent> BONE_0 = ModSounds.registerSoundEvent((String)"bone_snap");
    public static final RegistryObject<SoundEvent> BONE_1 = ModSounds.registerSoundEvent((String)"bone_break");
    public static final RegistryObject<SoundEvent> JUMPSCARE = ModSounds.registerSoundEvent((String)"jumpscare");
    public static final RegistryObject<SoundEvent> VERITY_DISC_SOUND = SOUND_EVENTS.register("verity_disc", () -> SoundEvent.create((ResourceLocation)new ResourceLocation("verity", "verity_disc")));
    public static final RegistryObject<SoundEvent> VERITY_EDIT_DISC_SOUND = SOUND_EVENTS.register("verity_edit_disc", () -> SoundEvent.create((ResourceLocation)new ResourceLocation("verity", "verity_edit_disc")));

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.create((ResourceLocation)new ResourceLocation("verity", name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

