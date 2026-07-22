package varmite.verity.entity.AI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reflection-based bridge to the optional Sherpa-ONNX offline speech engine
 * (com.k2fsa.sherpa.onnx). The verity mod compiles WITHOUT the sherpa-onnx
 * jars on the classpath; all access to that library happens reflectively here
 * so that, when the native engine is absent at runtime, voice features simply
 * degrade instead of crashing the mod.
 */
public final class SherpaBridge {
    private static final Logger LOGGER = LoggerFactory.getLogger(SherpaBridge.class);
    private static final String PKG = "com.k2fsa.sherpa.onnx.";

    private SherpaBridge() {
    }

    /** @return true if the Sherpa-ONNX classes are present on the classpath. */
    public static boolean isAvailable() {
        try {
            Class.forName(PKG + "OfflineRecognizer");
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    // ---------------------------------------------------------------- STT

    public static Object createRecognizer(String encoder, String decoder, String tokens, int numThreads) {
        try {
            Class<?> whisper = Class.forName(PKG + "OfflineWhisperModelConfig");
            Object w = whisper.getMethod("builder").invoke(null);
            w = whisper.getMethod("setEncoder", String.class).invoke(w, encoder);
            w = whisper.getMethod("setDecoder", String.class).invoke(w, decoder);
            w = whisper.getMethod("build").invoke(w);

            Class<?> model = Class.forName(PKG + "OfflineModelConfig");
            Object m = model.getMethod("builder").invoke(null);
            m = model.getMethod("setWhisper", whisper).invoke(m, w);
            m = model.getMethod("setTokens", String.class).invoke(m, tokens);
            m = model.getMethod("setNumThreads", int.class).invoke(m, numThreads);
            m = model.getMethod("setDebug", boolean.class).invoke(m, false);
            m = model.getMethod("build").invoke(m);

            Class<?> cfg = Class.forName(PKG + "OfflineRecognizerConfig");
            Object c = cfg.getMethod("builder").invoke(null);
            c = cfg.getMethod("setOfflineModelConfig", model).invoke(c, m);
            c = cfg.getMethod("build").invoke(c);

            Class<?> rec = Class.forName(PKG + "OfflineRecognizer");
            return rec.getConstructor(cfg).newInstance(c);
        } catch (Throwable t) {
            LOGGER.error("[Verity] Failed to build Sherpa-ONNX recognizer", t);
            return null;
        }
    }

    public static String recognize(Object recognizer, float[] samples, int sampleRate) {
        try {
            Class<?> rec = recognizer.getClass();
            Object stream = rec.getMethod("createStream").invoke(recognizer);
            Class<?> streamCls = stream.getClass();
            streamCls.getMethod("acceptWaveform", float[].class, int.class).invoke(stream, samples, sampleRate);
            rec.getMethod("decode", streamCls).invoke(recognizer, stream);
            Object result = rec.getMethod("getResult", streamCls).invoke(recognizer, stream);
            String text = (String) result.getClass().getMethod("getText").invoke(result);
            streamCls.getMethod("release").invoke(stream);
            return text;
        } catch (Throwable t) {
            LOGGER.error("[Verity STT] Failed to process speech locally with Sherpa.", t);
            return null;
        }
    }

    // ---------------------------------------------------------------- TTS

    public static Object createTts(String model, String tokens, String dataDir, int numThreads) {
        try {
            Class<?> vits = Class.forName(PKG + "OfflineTtsVitsModelConfig");
            Object v = vits.getMethod("builder").invoke(null);
            v = vits.getMethod("setModel", String.class).invoke(v, model);
            v = vits.getMethod("setTokens", String.class).invoke(v, tokens);
            v = vits.getMethod("setDataDir", String.class).invoke(v, dataDir);
            v = vits.getMethod("build").invoke(v);

            Class<?> tm = Class.forName(PKG + "OfflineTtsModelConfig");
            Object m = tm.getMethod("builder").invoke(null);
            m = tm.getMethod("setVits", vits).invoke(m, v);
            m = tm.getMethod("setNumThreads", int.class).invoke(m, numThreads);
            m = tm.getMethod("setDebug", boolean.class).invoke(m, false);
            m = tm.getMethod("build").invoke(m);

            Class<?> cfg = Class.forName(PKG + "OfflineTtsConfig");
            Object c = cfg.getMethod("builder").invoke(null);
            c = cfg.getMethod("setModel", tm).invoke(c, m);
            c = cfg.getMethod("build").invoke(c);

            Class<?> tts = Class.forName(PKG + "OfflineTts");
            return tts.getConstructor(cfg).newInstance(c);
        } catch (Throwable t) {
            LOGGER.error("[Verity Local TTS] Failed to build Sherpa-ONNX TTS engine", t);
            return null;
        }
    }

    public static float[] generate(Object tts, String text) {
        try {
            Object audio = tts.getClass().getMethod("generate", String.class).invoke(tts, text);
            if (audio == null) {
                return null;
            }
            return (float[]) audio.getClass().getMethod("getSamples").invoke(audio);
        } catch (Throwable t) {
            LOGGER.error("[Verity Local TTS] Failed to generate speech", t);
            return null;
        }
    }

    public static float getSampleRate(Object tts, float fallback) {
        try {
            Object r = tts.getClass().getMethod("getSampleRate").invoke(tts);
            return ((Number) r).floatValue();
        } catch (Throwable t) {
            return fallback;
        }
    }
}
