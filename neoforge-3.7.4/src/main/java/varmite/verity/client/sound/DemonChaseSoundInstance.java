/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 */
package varmite.verity.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.sounds.ModSounds;

public class DemonChaseSoundInstance
extends AbstractTickableSoundInstance {
    private final VerityDemonEntity demon;

    public DemonChaseSoundInstance(VerityDemonEntity demon) {
        super(ModSounds.CHASE.get(), SoundSource.HOSTILE, RandomSource.create());
        this.demon = demon;
        this.looping = true;
        this.delay = 0;
        this.volume = 3.0f;
        this.pitch = 0.6f;
        this.x = demon.getX();
        this.y = demon.getY();
        this.z = demon.getZ();
    }

    public void tick() {
        if (this.demon.isRemoved() || this.demon.isDeadOrDying() || this.demon.getDemonState() != 1) {
            this.stop();
            this.demon.isPlayingChaseSound = false;
        } else {
            this.x = this.demon.getX();
            this.y = this.demon.getY();
            this.z = this.demon.getZ();
        }
    }
}

