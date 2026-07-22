/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.k2fsa.sherpa.onnx.GeneratedAudio
 *  com.k2fsa.sherpa.onnx.GenerationConfig
 *  com.k2fsa.sherpa.onnx.OfflineTts
 *  com.k2fsa.sherpa.onnx.OfflineTtsConfig
 *  com.k2fsa.sherpa.onnx.OfflineTtsModelConfig
 *  com.k2fsa.sherpa.onnx.OfflineTtsVitsModelConfig
 *  varmite.verity.entity.AI.AiAPI
 *  varmite.verity.entity.AI.VerityLocalTTS
 */
package varmite.verity.entity.AI;

import com.k2fsa.sherpa.onnx.GeneratedAudio;
import com.k2fsa.sherpa.onnx.GenerationConfig;
import com.k2fsa.sherpa.onnx.OfflineTts;
import com.k2fsa.sherpa.onnx.OfflineTtsConfig;
import com.k2fsa.sherpa.onnx.OfflineTtsModelConfig;
import com.k2fsa.sherpa.onnx.OfflineTtsVitsModelConfig;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.sound.sampled.AudioFormat;
import varmite.verity.entity.AI.AiAPI;

/*
 * Exception performing whole class analysis ignored.
 */
public class VerityLocalTTS {
    private static OfflineTts ttsEngine;

    public static void init() {
        try {
            Path tempDir = Files.createTempDirectory("verity_tts_engine", new FileAttribute[0]);
            InputStream zipStream = VerityLocalTTS.class.getClassLoader().getResourceAsStream("assets/verity/tts/piper.zip");
            if (zipStream == null) {
                zipStream = VerityLocalTTS.class.getResourceAsStream("/assets/verity/tts/piper.zip");
            }
            if (zipStream == null) {
                throw new Exception("Could not find piper.zip in resources!");
            }
            try (ZipInputStream zis = new ZipInputStream(zipStream);){
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    Path resolvedPath = tempDir.resolve(entry.getName());
                    if (entry.isDirectory()) {
                        Files.createDirectories(resolvedPath, new FileAttribute[0]);
                        continue;
                    }
                    Files.createDirectories(resolvedPath.getParent(), new FileAttribute[0]);
                    Files.copy(zis, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            OfflineTtsVitsModelConfig vitsModelConfig = OfflineTtsVitsModelConfig.builder().setModel(tempDir.resolve("en_US-ryan-medium.onnx").toAbsolutePath().toString()).setTokens(tempDir.resolve("tokens.txt").toAbsolutePath().toString()).setDataDir(tempDir.resolve("espeak-ng-data").toAbsolutePath().toString()).build();
            OfflineTtsModelConfig modelConfig = OfflineTtsModelConfig.builder().setVits(vitsModelConfig).setNumThreads(2).setDebug(false).build();
            OfflineTtsConfig config = OfflineTtsConfig.builder().setModel(modelConfig).build();
            ttsEngine = new OfflineTts(config);
            System.out.println("[Verity Local TTS] Piper Engine Initialized Successfully.");
        }
        catch (Throwable e) {
            System.err.println("[Verity Local TTS] Failed to initialize local AI engine!");
            e.printStackTrace();
        }
    }

    public static byte[] generateSpeech(String text) {
        if (ttsEngine == null) {
            System.out.println("[Verity Local TTS] Lazy loading offline AI engine...");
            VerityLocalTTS.init();
        }
        if (ttsEngine == null) {
            System.err.println("[Verity Local TTS] Engine failed to lazy load! Aborting.");
            return null;
        }
        try {
            GenerationConfig genConfig = new GenerationConfig();
            genConfig.setSid(0);
            genConfig.setSpeed(1.0f);
            genConfig.setSilenceScale(0.2f);
            GeneratedAudio audio = ttsEngine.generateWithConfigAndCallback(text, genConfig, samples -> AiAPI.cancelCurrentSpeech ? 0 : 1);
            if (audio == null || audio.getSamples() == null) {
                return null;
            }
            float[] samples2 = audio.getSamples();
            byte[] pcmData = new byte[samples2.length * 2];
            for (int i = 0; i < samples2.length; ++i) {
                float clamped = Math.max(-1.0f, Math.min(1.0f, samples2[i]));
                short val = (short)(clamped * 32767.0f);
                pcmData[i * 2] = (byte)(val & 0xFF);
                pcmData[i * 2 + 1] = (byte)(val >> 8 & 0xFF);
            }
            return pcmData;
        }
        catch (Throwable e) {
            System.err.println("[Verity Local TTS] Speech generation crashed!");
            e.printStackTrace();
            return null;
        }
    }

    public static AudioFormat getFormat() {
        float sampleRate = ttsEngine != null ? (float)ttsEngine.getSampleRate() : 22050.0f;
        return new AudioFormat(sampleRate, 16, 1, true, false);
    }
}

