/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package varmite.verity.client.audio;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import varmite.verity.client.audio.MicrophoneManager;

@OnlyIn(value=Dist.CLIENT)
public class MicrophoneRecorder {
    private TargetDataLine targetLine;
    private volatile boolean isRecording = false;
    private ByteArrayOutputStream audioStream;
    private Thread recordingThread;

    public AudioFormat getAudioFormat() {
        float sampleRate = 16000.0f;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public MicrophoneRecorder() {
        MicrophoneManager.scanForMicrophones(this.getAudioFormat());
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
                System.err.println("Microphone not supported on this system!");
                return;
            }
            this.targetLine.open(format);
            this.targetLine.start();
            this.targetLine.start();
            this.isRecording = true;
            this.audioStream = new ByteArrayOutputStream();
            this.recordingThread = new Thread(this::recordLoop, "Verity-Audio-Recorder");
            this.recordingThread.setDaemon(true);
            this.recordingThread.start();
            System.out.println("Microphone line opened. Recording...");
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void recordLoop() {
        byte[] buffer = new byte[this.targetLine.getBufferSize() / 5];
        while (this.isRecording) {
            int bytesRead = this.targetLine.read(buffer, 0, buffer.length);
            if (bytesRead <= 0) continue;
            this.audioStream.write(buffer, 0, bytesRead);
        }
    }

    public byte[] stopRecording() {
        if (!this.isRecording) {
            return new byte[0];
        }
        this.isRecording = false;
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
        System.out.println("Recording stopped. Captured " + this.audioStream.size() + " bytes.");
        return this.audioStream.toByteArray();
    }
}

