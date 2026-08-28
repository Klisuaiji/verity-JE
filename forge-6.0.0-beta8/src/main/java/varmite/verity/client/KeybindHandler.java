/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 */
package varmite.verity.client;

import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import varmite.verity.client.KeybindRegistry;
import varmite.verity.client.audio.MicrophoneManager;
import varmite.verity.client.audio.MicrophoneRecorder;
import varmite.verity.client.audio.TTSHandler;

@Mod.EventBusSubscriber(modid="verity", value={Dist.CLIENT})
public class KeybindHandler {
    private static boolean isRecording = false;
    private static final MicrophoneRecorder RECORDER = new MicrophoneRecorder();

    public static boolean isRecording() {
        return isRecording;
    }

    public static MicrophoneRecorder getRecorder() {
        return RECORDER;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        boolean isKeyDown;
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (KeybindRegistry.PUSH_TO_TALK == null || KeybindRegistry.CYCLE_MIC == null) {
            return;
        }
        if (KeybindRegistry.CYCLE_MIC.m_90859_()) {
            MicrophoneManager.cycleMicrophone();
        }
        if ((isKeyDown = KeybindRegistry.PUSH_TO_TALK.m_90857_()) && !isRecording) {
            isRecording = true;
            TTSHandler.interruptSpeech();
            RECORDER.startRecording();
        } else if (!isKeyDown && isRecording) {
            isRecording = false;
            byte[] recordedAudio = RECORDER.stopRecording();
            if (recordedAudio != null && recordedAudio.length > 0) {
                CompletableFuture.supplyAsync(() -> TTSHandler.transcribeAudio(recordedAudio, RECORDER.getAudioFormat())).thenAccept(KeybindHandler::accept);
            }
        }
    }

    private static void accept(String transcribedText) {
        if (transcribedText != null && !transcribedText.trim().isEmpty()) {
            String lowerCaseText = transcribedText.trim().toLowerCase();
            if (lowerCaseText.equals("thank you.")) {
                return;
            }
            Minecraft.m_91087_().execute(() -> {
                String safeText;
                String string = safeText = transcribedText.length() > 256 ? transcribedText.substring(0, 256) : transcribedText;
                if (Minecraft.m_91087_().m_91403_() != null) {
                    Minecraft.m_91087_().m_91403_().m_246175_(safeText);
                }
            });
        }
    }
}

