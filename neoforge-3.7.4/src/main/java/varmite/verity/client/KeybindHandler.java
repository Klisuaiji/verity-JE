/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 */
package varmite.verity.client;

import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import varmite.verity.client.KeybindRegistry;
import varmite.verity.client.audio.MicrophoneManager;
import varmite.verity.client.audio.MicrophoneRecorder;
import varmite.verity.entity.AI.AiAPI;

@EventBusSubscriber(modid="verity", value={Dist.CLIENT})
public class KeybindHandler {
    private static boolean isRecording = false;
    private static final MicrophoneRecorder RECORDER = new MicrophoneRecorder();

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        boolean isKeyDown;
        if (KeybindRegistry.PUSH_TO_TALK == null || KeybindRegistry.CYCLE_MIC == null) {
            return;
        }
        if (KeybindRegistry.CYCLE_MIC.consumeClick()) {
            MicrophoneManager.cycleMicrophone();
        }
        if ((isKeyDown = KeybindRegistry.PUSH_TO_TALK.isDown()) && !isRecording) {
            isRecording = true;
            RECORDER.startRecording();
        } else if (!isKeyDown && isRecording) {
            isRecording = false;
            byte[] recordedAudio = RECORDER.stopRecording();
            if (recordedAudio.length > 0) {
                CompletableFuture.supplyAsync(() -> AiAPI.transcribeAudio(recordedAudio, RECORDER.getAudioFormat())).thenAccept(transcribedText -> {
                    if (transcribedText != null && !transcribedText.trim().isEmpty()) {
                        System.out.println("GROQ TRANSCRIBED: " + transcribedText);
                        Minecraft.getInstance().execute(() -> {
                            String safeText;
                            String string = safeText = transcribedText.length() > 256 ? transcribedText.substring(0, 256) : transcribedText;
                            if (Minecraft.getInstance().getConnection() != null) {
                                Minecraft.getInstance().getConnection().sendChat(safeText);
                            }
                        });
                    } else {
                        System.out.println("Audio was empty or transcription failed.");
                    }
                });
            }
        }
    }
}

