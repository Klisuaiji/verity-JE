/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package varmite.verity.client.audio;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class MicrophoneManager {
    private static final List<Mixer.Info> AVAILABLE_MICS = new ArrayList<Mixer.Info>();
    private static int currentIndex = -1;

    public static void scanForMicrophones(AudioFormat format) {
        AVAILABLE_MICS.clear();
        DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, format);
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(info);
            if (!mixer.isLineSupported(lineInfo)) continue;
            AVAILABLE_MICS.add(info);
        }
        if (!AVAILABLE_MICS.isEmpty() && currentIndex == -1) {
            currentIndex = 0;
        }
    }

    public static Mixer.Info getSelectedMicrophone() {
        if (AVAILABLE_MICS.isEmpty() || currentIndex < 0 || currentIndex >= AVAILABLE_MICS.size()) {
            return null;
        }
        return AVAILABLE_MICS.get(currentIndex);
    }

    public static void cycleMicrophone() {
        if (AVAILABLE_MICS.isEmpty()) {
            MicrophoneManager.sendClientMessage("No compatible microphones found on this system.");
            return;
        }
        currentIndex = (currentIndex + 1) % AVAILABLE_MICS.size();
        Mixer.Info selected = AVAILABLE_MICS.get(currentIndex);
        MicrophoneManager.sendClientMessage("Microphone switched to: " + selected.getName());
    }

    private static void sendClientMessage(String text) {
        if (Minecraft.m_91087_().f_91074_ != null) {
            Minecraft.m_91087_().f_91074_.m_5661_((Component)Component.m_237113_((String)text), false);
        }
    }
}

