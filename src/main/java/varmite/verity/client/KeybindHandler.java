/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.event.TickEvent$ClientTickEvent
 *  net.neoforged.neoforge.event.TickEvent$Phase
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  varmite.verity.client.KeybindHandler
 *  varmite.verity.client.KeybindRegistry
 *  varmite.verity.client.audio.MicrophoneManager
 *  varmite.verity.client.audio.MicrophoneRecorder
 *  varmite.verity.entity.AI.AiAPI
 */
package varmite.verity.client;

import java.util.concurrent.CompletableFuture;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import varmite.verity.client.KeybindRegistry;
import varmite.verity.client.audio.MicrophoneManager;
import varmite.verity.client.audio.MicrophoneRecorder;
import varmite.verity.entity.AI.AiAPI;

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
        if (KeybindRegistry.CYCLE_MIC.consumeClick()) {
            MicrophoneManager.cycleMicrophone();
        }
        if ((isKeyDown = KeybindRegistry.PUSH_TO_TALK.isDown()) && !isRecording) {
            isRecording = true;
            AiAPI.interruptSpeech();
            RECORDER.startRecording();
        } else if (!isKeyDown && isRecording) {
            isRecording = false;
            byte[] recordedAudio = RECORDER.stopRecording();
            if (recordedAudio != null && recordedAudio.length > 0) {
                CompletableFuture.supplyAsync(() -> AiAPI.transcribeAudio((byte[])recordedAudio, (AudioFormat)RECORDER.getAudioFormat())).thenAccept(KeybindHandler::accept);
            }
        }
    }

    private static void accept(String transcribedText) {
        if (transcribedText != null && !transcribedText.trim().isEmpty()) {
            String lowerCaseText = transcribedText.trim().toLowerCase();
            if (lowerCaseText.equals("thank you.")) {
                return;
            }
            Minecraft.getInstance().execute(() -> {
                String safeText;
                String string = safeText = transcribedText.length() > 256 ? transcribedText.substring(0, 256) : transcribedText;
                if (Minecraft.getInstance().getConnection() != null) {
                    Minecraft.getInstance().getConnection().sendChat(safeText);
                }
            });
        }
    }
}

