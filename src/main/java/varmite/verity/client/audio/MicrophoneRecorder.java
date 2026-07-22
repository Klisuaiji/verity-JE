/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  varmite.verity.client.audio.MicrophoneManager
 *  varmite.verity.client.audio.MicrophoneRecorder
 *  varmite.verity.entity.AI.AiAPI
 */
package varmite.verity.client.audio;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import varmite.verity.client.audio.MicrophoneManager;
import varmite.verity.entity.AI.AiAPI;

@OnlyIn(value=Dist.CLIENT)
public class MicrophoneRecorder {
    private TargetDataLine targetLine;
    private volatile boolean isRecording = false;
    private ByteArrayOutputStream audioStream;
    private Thread recordingThread;
    private volatile double audioLevel = 0.0;

    public AudioFormat getAudioFormat() {
        float sampleRate = 16000.0f;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public MicrophoneRecorder() {
        MicrophoneManager.scanForMicrophones((AudioFormat)this.getAudioFormat());
    }

    public double getAudioLevel() {
        return this.audioLevel;
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public void startRecording() {
        if (this.isRecording) {
            return;
        }
        try {
            AudioFormat format = this.getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            Mixer.Info selectedMixer = MicrophoneManager.getSelectedMicrophone();
            if (selectedMixer != null) {
                Mixer mixer = AudioSystem.getMixer(selectedMixer);
                this.targetLine = (TargetDataLine)mixer.getLine(info);
            } else if (AudioSystem.isLineSupported(info)) {
                this.targetLine = (TargetDataLine)AudioSystem.getLine(info);
            } else {
                System.err.println("[Verity Audio] Microphone not supported on this system!");
                return;
            }
            this.targetLine.open(format);
            this.targetLine.start();
            this.isRecording = true;
            this.audioLevel = 0.0;
            this.audioStream = new ByteArrayOutputStream();
            this.recordingThread = new Thread(() -> this.recordLoop(), "Verity-Audio-Recorder");
            this.recordingThread.setDaemon(true);
            this.recordingThread.start();
            System.out.println("[Verity Audio] Microphone line opened. Recording...");
        }
        catch (LineUnavailableException e) {
            System.err.println("[Verity Audio] Failed to open microphone line.");
            e.printStackTrace();
        }
    }

    private void recordLoop() {
        byte[] buffer = new byte[this.targetLine.getBufferSize() / 5];
        while (this.isRecording) {
            int bytesRead = this.targetLine.read(buffer, 0, buffer.length);
            if (bytesRead <= 0) continue;
            this.audioStream.write(buffer, 0, bytesRead);
            long sum = 0L;
            for (int i = 0; i < bytesRead - 1; i += 2) {
                short sample = (short)(buffer[i + 1] << 8 | buffer[i] & 0xFF);
                sum += (long)sample * (long)sample;
            }
            double rms = Math.sqrt((double)sum / ((double)bytesRead / 2.0));
            double targetLevel = Math.min(1.0, rms / 32767.0 * 4.0);
            this.audioLevel = this.audioLevel * 0.5 + targetLevel * 0.5;
        }
    }

    public void stopRecordingAndTranscribe(Consumer<String> onTranscriptionComplete) {
        if (!this.isRecording) {
            if (onTranscriptionComplete != null) {
                onTranscriptionComplete.accept("");
            }
            return;
        }
        this.isRecording = false;
        this.audioLevel = 0.0;
        if (this.targetLine != null) {
            this.targetLine.stop();
            this.targetLine.close();
        }
        CompletableFuture.supplyAsync(() -> {
            if (this.recordingThread != null && this.recordingThread.isAlive()) {
                try {
                    this.recordingThread.join(500L);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            byte[] audioData = this.audioStream.toByteArray();
            System.out.println("[Verity Audio] Recording stopped. Captured " + audioData.length + " bytes. Decoding...");
            return AiAPI.transcribeAudio((byte[])audioData, (AudioFormat)this.getAudioFormat());
        }).thenAccept(transcribedText -> {
            if (onTranscriptionComplete != null) {
                onTranscriptionComplete.accept((String)transcribedText);
            }
        });
    }

    public byte[] stopRecording() {
        if (!this.isRecording) {
            return new byte[0];
        }
        this.isRecording = false;
        this.audioLevel = 0.0;
        if (this.targetLine != null) {
            this.targetLine.stop();
            this.targetLine.close();
        }
        if (this.recordingThread != null && this.recordingThread.isAlive()) {
            try {
                this.recordingThread.join(500L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[Verity Audio] Recording stopped. Captured " + this.audioStream.size() + " bytes.");
        return this.audioStream.toByteArray();
    }
}

