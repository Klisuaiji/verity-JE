package varmite.verity.entity.AI;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.sound.sampled.AudioFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import varmite.verity.entity.AI.AiAPI;

/**
 * Local offline TTS via Sherpa-ONNX (Piper/VITS). All access to the optional
 * sherpa-onnx library is funnelled through {@link SherpaBridge} so the mod
 * compiles and runs without it; when the engine is absent, voice simply
 * degrades to silent.
 */
public class VerityLocalTTS {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerityLocalTTS.class);
    private static Object ttsEngine;

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
            ttsEngine = SherpaBridge.createTts(
                    tempDir.resolve("en_US-ryan-medium.onnx").toAbsolutePath().toString(),
                    tempDir.resolve("tokens.txt").toAbsolutePath().toString(),
                    tempDir.resolve("espeak-ng-data").toAbsolutePath().toString(),
                    2);
            if (ttsEngine != null) {
                LOGGER.info("[Verity Local TTS] Piper Engine Initialized Successfully.");
            } else {
                LOGGER.error("[Verity Local TTS] Failed to initialize local AI engine (sherpa-onnx unavailable?).");
            }
        }
        catch (Throwable e) {
            LOGGER.error("[Verity Local TTS] Failed to initialize local AI engine!", e);
        }
    }

    public static byte[] generateSpeech(String text) {
        if (ttsEngine == null) {
            LOGGER.info("[Verity Local TTS] Lazy loading offline AI engine...");
            VerityLocalTTS.init();
        }
        if (ttsEngine == null) {
            LOGGER.error("[Verity Local TTS] Engine failed to lazy load! Aborting.");
            return null;
        }
        try {
            float[] samples2 = SherpaBridge.generate(ttsEngine, text);
            if (samples2 == null) {
                return null;
            }
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
            LOGGER.error("[Verity Local TTS] Speech generation crashed!", e);
            return null;
        }
    }

    public static AudioFormat getFormat() {
        float sampleRate = SherpaBridge.getSampleRate(ttsEngine, 22050.0f);
        return new AudioFormat(sampleRate, 16, 1, true, false);
    }
}
