/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SoundInstance
 */
package varmite.verity.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import varmite.verity.client.sound.DemonChaseSoundInstance;
import varmite.verity.entity.custom.VerityDemonEntity;

public class ClientSoundHandler {
    public static void playDemonChaseSound(VerityDemonEntity demon) {
        Minecraft.getInstance().getSoundManager().play((SoundInstance)new DemonChaseSoundInstance(demon));
    }
}

