/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  varmite.verity.client.audio.MicrophoneManager
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/*
 * Exception performing whole class analysis ignored.
 */
@OnlyIn(value=Dist.CLIENT)
public class MicrophoneManager {
    private static final List<Mixer.Info> AVAILABLE_MICS = new ArrayList();
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
        return (Mixer.Info)AVAILABLE_MICS.get(currentIndex);
    }

    public static void cycleMicrophone() {
        if (AVAILABLE_MICS.isEmpty()) {
            MicrophoneManager.sendClientMessage((Component)Component.translatable("verity.msg.mic_none"));
            return;
        }
        currentIndex = (currentIndex + 1) % AVAILABLE_MICS.size();
        Mixer.Info selected = (Mixer.Info)AVAILABLE_MICS.get(currentIndex);
        MicrophoneManager.sendClientMessage((Component)Component.translatable("verity.msg.mic_switched", selected.getName()));
    }

    private static void sendClientMessage(Component text) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(text);
        }
    }
}

