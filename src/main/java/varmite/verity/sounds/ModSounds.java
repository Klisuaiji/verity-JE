/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package varmite.verity.sounds;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create((Registry)BuiltInRegistries.SOUND_EVENT, (String)"verity");
    public static final Supplier<SoundEvent> BOX_OPEN = ModSounds.registerSoundEvent("box_open");
    public static final Supplier<SoundEvent> BOX_CLICK = ModSounds.registerSoundEvent("box_click");
    public static final Supplier<SoundEvent> BOX_VERITY_0 = ModSounds.registerSoundEvent("box_verity_0");
    public static final Supplier<SoundEvent> BOX_VERITY_1 = ModSounds.registerSoundEvent("box_verity_1");
    public static final Supplier<SoundEvent> BOX_VERITY_2 = ModSounds.registerSoundEvent("box_verity_2");
    public static final Supplier<SoundEvent> INTRODUCTION = ModSounds.registerSoundEvent("intro");
    public static final Supplier<SoundEvent> IMPACT_0 = ModSounds.registerSoundEvent("impact_0");
    public static final Supplier<SoundEvent> IMPACT_1 = ModSounds.registerSoundEvent("impact_1");
    public static final Supplier<SoundEvent> IMPACT_2 = ModSounds.registerSoundEvent("impact_2");
    public static final Supplier<SoundEvent> INTRO_VIDEO_AUDIO = ModSounds.registerSoundEvent("intro_video_audio");
    public static final Supplier<SoundEvent> CHASE = ModSounds.registerSoundEvent("chase");
    public static final Supplier<SoundEvent> BONE_0 = ModSounds.registerSoundEvent("bone_snap");
    public static final Supplier<SoundEvent> BONE_1 = ModSounds.registerSoundEvent("bone_break");
    public static final Supplier<SoundEvent> JUMPSCARE = ModSounds.registerSoundEvent("jumpscare");
    public static final DeferredHolder<SoundEvent, SoundEvent> VERITY_DISC_SOUND = SOUND_EVENTS.register("verity_disc", () -> SoundEvent.createVariableRangeEvent((ResourceLocation)ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"verity_disc")));

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath((String)"verity", (String)name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent((ResourceLocation)id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

