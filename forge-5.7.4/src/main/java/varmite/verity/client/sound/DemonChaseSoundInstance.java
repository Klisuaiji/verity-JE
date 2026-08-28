/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 */
package varmite.verity.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.sounds.ModSounds;

public class DemonChaseSoundInstance
extends AbstractTickableSoundInstance {
    private final VerityDemonEntity demon;

    public DemonChaseSoundInstance(VerityDemonEntity demon) {
        super((SoundEvent)ModSounds.CHASE.get(), SoundSource.HOSTILE, RandomSource.m_216327_());
        this.demon = demon;
        this.f_119578_ = true;
        this.f_119579_ = 0;
        this.f_119573_ = 3.0f;
        this.f_119574_ = 0.6f;
        this.f_119575_ = demon.m_20185_();
        this.f_119576_ = demon.m_20186_();
        this.f_119577_ = demon.m_20189_();
    }

    public void m_7788_() {
        if (this.demon.m_213877_() || this.demon.m_21224_() || this.demon.getDemonState() != 1) {
            this.m_119609_();
            this.demon.isPlayingChaseSound = false;
        } else {
            this.f_119575_ = this.demon.m_20185_();
            this.f_119576_ = this.demon.m_20186_();
            this.f_119577_ = this.demon.m_20189_();
        }
    }
}

