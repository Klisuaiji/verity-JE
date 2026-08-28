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
import varmite.verity.entity.demon.VerityDemonEntity;

public class ClientSoundHandler {
    public static void playDemonChaseSound(VerityDemonEntity demon) {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)new DemonChaseSoundInstance(demon));
    }
}

