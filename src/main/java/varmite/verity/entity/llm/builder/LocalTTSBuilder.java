/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 *
 * Offline TTS (Piper / VITS through Sherpa-ONNX). 6.1 links against
 * com.k2fsa.sherpa.onnx directly; this port keeps the existing reflective
 * SherpaBridge so the mod still compiles and runs when the optional
 * sherpa-onnx jar is absent — voice then degrades to silence instead of
 * crashing the game.
 */
package varmite.verity.entity.llm.builder;

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
import varmite.verity.entity.AI.SherpaBridge;

public class LocalTTSBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalTTSBuilder.class);
    private static Object ttsEngine;

    public static void init() {
        try {
            Path tempDir = Files.createTempDirectory("verity_tts_engine", new FileAttribute[0]);
            InputStream zipStream =
                    LocalTTSBuilder.class.getClassLoader().getResourceAsStream("assets/verity/tts/piper.zip");
            if (zipStream == null) {
                zipStream = LocalTTSBuilder.class.getResourceAsStream("/assets/verity/tts/piper.zip");
            }
            if (zipStream == null) {
                LOGGER.error("[Verity Local TTS] Could not find piper.zip in resources!");
                return;
            }
            try (ZipInputStream zis = new ZipInputStream(zipStream)) {
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
        } catch (Throwable e) {
            LOGGER.error("[Verity Local TTS] Failed to initialize local AI engine!", e);
        }
    }

    public static byte[] generateSpeech(String text) {
        if (ttsEngine == null) {
            LOGGER.info("[Verity Local TTS] Lazy loading offline AI engine...");
            init();
        }
        if (ttsEngine == null) {
            LOGGER.error("[Verity Local TTS] Engine failed to lazy load! Aborting.");
            return null;
        }
        try {
            float[] samples = SherpaBridge.generate(ttsEngine, text);
            if (samples == null) {
                return null;
            }
            byte[] pcmData = new byte[samples.length * 2];
            for (int i = 0; i < samples.length; ++i) {
                float clamped = Math.max(-1.0f, Math.min(1.0f, samples[i]));
                short val = (short) (clamped * 32767.0f);
                pcmData[i * 2] = (byte) (val & 0xFF);
                pcmData[i * 2 + 1] = (byte) (val >> 8 & 0xFF);
            }
            return pcmData;
        } catch (Throwable e) {
            LOGGER.error("[Verity Local TTS] Speech generation crashed!", e);
            return null;
        }
    }

    public static AudioFormat getFormat() {
        float sampleRate = SherpaBridge.getSampleRate(ttsEngine, 22050.0f);
        return new AudioFormat(sampleRate, 16, 1, true, false);
    }
}
