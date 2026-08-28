/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.text2speech.Narrator
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.client.audio;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.k2fsa.sherpa.onnx.OfflineModelConfig;
import com.k2fsa.sherpa.onnx.OfflineRecognizer;
import com.k2fsa.sherpa.onnx.OfflineRecognizerConfig;
import com.k2fsa.sherpa.onnx.OfflineStream;
import com.k2fsa.sherpa.onnx.OfflineWhisperModelConfig;
import com.mojang.text2speech.Narrator;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import varmite.verity.VerityConfig;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.llm.builder.LocalTTSBuilder;
import varmite.verity.entity.llm.builder.ModelExtractor;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.environment.items.ModItems;
import varmite.verity.types.KokoroVoice;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;

public class TTSHandler {
    public static volatile boolean cancelCurrentSpeech = false;
    private static OfflineRecognizer sherpaRecognizer = null;
    private static float muffledFilterState = 0.0f;

    public static String getGroqApiKey() {
        return (String)VerityConfig.GROQ_KEY.get();
    }

    public static void apply3DEffect(SourceDataLine line, Player player, VerityEntity verity) {
        if (line == null || player == null || verity == null) {
            return;
        }
        double distance = player.m_20182_().m_82554_(verity.m_20182_());
        float maxDist = 32.0f;
        float volumeMultiplier = 1.0f - (float)(distance / (double)maxDist);
        volumeMultiplier = Math.max(0.0f, Math.min(1.0f, volumeMultiplier));
        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volControl = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = volumeMultiplier <= 0.001f ? -80.0f : (float)(Math.log10(volumeMultiplier) * 20.0);
            volControl.setValue(dB);
        }
        if (line.isControlSupported(FloatControl.Type.PAN)) {
            Vec3 toVerity = verity.m_20182_().m_82546_(player.m_20182_()).m_82541_();
            Vec3 playerLook = player.m_20252_(1.0f).m_82541_();
            double pan = playerLook.f_82479_ * toVerity.f_82481_ - playerLook.f_82481_ * toVerity.f_82479_;
            float finalPan = (float)Math.max(-1.0, Math.min(1.0, pan));
            FloatControl panControl = (FloatControl)line.getControl(FloatControl.Type.PAN);
            panControl.setValue(finalPan);
        }
    }

    public static boolean isVerityMuffled(Player player, VerityEntity verity) {
        if (verity != null) {
            return false;
        }
        if (player == null) {
            return false;
        }
        if (!player.m_21205_().m_41619_() && player.m_21205_().m_150930_((Item)ModItems.VERITY_ITEM.get())) {
            return false;
        }
        if (!player.m_21206_().m_41619_() && player.m_21206_().m_150930_((Item)ModItems.VERITY_ITEM.get())) {
            return false;
        }
        for (int i = 0; i < player.m_150109_().f_35974_.size(); ++i) {
            ItemStack stack = (ItemStack)player.m_150109_().f_35974_.get(i);
            if (stack.m_41619_() || !stack.m_150930_((Item)ModItems.VERITY_ITEM.get())) continue;
            return true;
        }
        return false;
    }

    public static void interruptSpeech() {
        cancelCurrentSpeech = true;
        Narrator.getNarrator().clear();
    }

    public static void initLocalSTT() {
        if (VerityConfig.STT_PROVIDER.get() != STTProvider.NATIVE) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            try {
                Path modelPath = ModelExtractor.getOrExtractModel();
                OfflineWhisperModelConfig whisperConfig = OfflineWhisperModelConfig.builder().setEncoder(modelPath.resolve("tiny.en-encoder.int8.onnx").toString()).setDecoder(modelPath.resolve("tiny.en-decoder.int8.onnx").toString()).build();
                OfflineModelConfig modelConfig = OfflineModelConfig.builder().setWhisper(whisperConfig).setTokens(modelPath.resolve("tiny.en-tokens.txt").toString()).setNumThreads(2).setDebug(false).build();
                OfflineRecognizerConfig config = OfflineRecognizerConfig.builder().setOfflineModelConfig(modelConfig).build();
                sherpaRecognizer = new OfflineRecognizer(config);
                System.out.println("[Verity] Offline Sherpa-ONNX Engine initialized!");
            }
            catch (Exception e) {
                System.err.println("[Verity] Failed to load Sherpa model. Check your model filenames!");
                e.printStackTrace();
            }
        });
    }

    public static String transcribeAudio(byte[] pcmData, AudioFormat format) {
        if (pcmData == null || pcmData.length == 0) {
            return "";
        }
        if (VerityConfig.STT_PROVIDER.get() == STTProvider.NATIVE) {
            if (sherpaRecognizer == null) {
                System.out.println("[Verity] First microphone use detected! Initializing Sherpa engine...");
                TTSHandler.initLocalSTT();
                return "";
            }
            try {
                OfflineStream stream = sherpaRecognizer.createStream();
                float[] floatAudio = new float[pcmData.length / 2];
                for (int i = 0; i < pcmData.length; i += 2) {
                    short sample = (short)(pcmData[i + 1] << 8 | pcmData[i] & 0xFF);
                    floatAudio[i / 2] = (float)sample / 32768.0f;
                }
                stream.acceptWaveform(floatAudio, (int)format.getSampleRate());
                sherpaRecognizer.decode(stream);
                String result = sherpaRecognizer.getResult(stream).getText();
                stream.release();
                return result != null ? result.trim() : "";
            }
            catch (Exception e) {
                System.err.println("[Verity STT] Failed to process speech locally with Sherpa.");
                e.printStackTrace();
                return "";
            }
        }
        try {
            HttpRequest request;
            byte[] wavData;
            try (ByteArrayInputStream bais = new ByteArrayInputStream(pcmData);
                 AudioInputStream ais = new AudioInputStream(bais, format, pcmData.length / format.getFrameSize());
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();){
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, baos);
                wavData = baos.toByteArray();
            }
            String boundary = "----VerityBoundary" + System.currentTimeMillis();
            ByteArrayOutputStream bodyStream = new ByteArrayOutputStream();
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            bodyStream.write((twoHyphens + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
            bodyStream.write(("Content-Disposition: form-data; name=\"model\"" + lineEnd + lineEnd).getBytes(StandardCharsets.UTF_8));
            if (VerityConfig.STT_PROVIDER.get() == STTProvider.WHISPER) {
                bodyStream.write(((String)VerityConfig.STT_MODEL.get() + lineEnd).getBytes(StandardCharsets.UTF_8));
            } else {
                bodyStream.write(("whisper-large-v3-turbo" + lineEnd).getBytes(StandardCharsets.UTF_8));
            }
            bodyStream.write((twoHyphens + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
            if (VerityConfig.STT_PROVIDER.get() == STTProvider.WHISPER) {
                bodyStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"audio.wav\"" + lineEnd).getBytes(StandardCharsets.UTF_8));
                bodyStream.write(("Content-Type: audio/wav" + lineEnd + lineEnd).getBytes(StandardCharsets.UTF_8));
            } else {
                bodyStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"audio.wav\"" + lineEnd + lineEnd).getBytes(StandardCharsets.UTF_8));
            }
            bodyStream.write(wavData);
            bodyStream.write(lineEnd.getBytes(StandardCharsets.UTF_8));
            bodyStream.write((twoHyphens + boundary + twoHyphens + lineEnd).getBytes(StandardCharsets.UTF_8));
            byte[] multipartBody = bodyStream.toByteArray();
            System.out.println(new String(multipartBody, StandardCharsets.ISO_8859_1));
            if (VerityConfig.STT_PROVIDER.get() == STTProvider.WHISPER) {
                String STTEndpoint = Optional.ofNullable((String)VerityConfig.STT_ENDPOINT.get()).filter(s -> !s.isEmpty()).orElseGet(() -> ((STTProvider)((Object)((Object)VerityConfig.STT_PROVIDER.get()))).getDefaultUrl());
                request = HttpRequest.newBuilder().uri(URI.create(STTEndpoint + "audio/transcriptions")).header("Content-Type", "multipart/form-data; boundary=" + boundary).version(HttpClient.Version.HTTP_1_1).POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody)).build();
            } else {
                request = HttpRequest.newBuilder().uri(URI.create("https://api.groq.com/openai/v1/audio/transcriptions")).header("Authorization", "Bearer " + TTSHandler.getGroqApiKey()).header("Content-Type", "multipart/form-data; boundary=" + boundary).POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody)).build();
            }
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30L)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonObject responseJson = JsonParser.parseString((String)response.body()).getAsJsonObject();
                String transcribedText = responseJson.get("text").getAsString().trim();
                if (".".equals(transcribedText) || transcribedText.isEmpty()) {
                    return "";
                }
                return transcribedText;
            }
            System.err.println("[Verity STT Error]: " + response.statusCode() + " - " + response.body());
            return "";
        }
        catch (Exception e) {
            System.err.println("[Verity STT] Failed to process speech upload stream.");
            e.printStackTrace();
            return "";
        }
    }

    public static void applyMuffledFilter(byte[] pcmData, int offset, int length) {
        float alpha = 0.15f;
        for (int i = offset; i < offset + length - 1; i += 2) {
            int out;
            short sample = (short)(pcmData[i + 1] << 8 | pcmData[i] & 0xFF);
            if ((out = Math.round((muffledFilterState += alpha * ((float)sample - muffledFilterState)) * 0.75f)) > Short.MAX_VALUE) {
                out = Short.MAX_VALUE;
            }
            if (out < Short.MIN_VALUE) {
                out = Short.MIN_VALUE;
            }
            pcmData[i] = (byte)(out & 0xFF);
            pcmData[i + 1] = (byte)(out >> 8 & 0xFF);
        }
    }

    public static void resetMuffledFilter() {
        muffledFilterState = 0.0f;
    }

    public static void playNativeTTS(String text, VerityEntity verity) {
        if (!((Boolean)VerityConfig.USE_TTS.get()).booleanValue()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            cancelCurrentSpeech = false;
            try {
                Minecraft.m_91087_().execute(() -> {
                    if (verity != null) {
                        verity.clientIsTalking = true;
                    }
                });
                Narrator osNarrator = Narrator.getNarrator();
                VerityState.isClientTalking = true;
                osNarrator.say(text, true);
                int wordCount = text.split("\\s+").length;
                int punctuationCount = text.replaceAll("[^.,!?]", "").length();
                long estimatedTimeMs = (long)wordCount * 400L + (long)punctuationCount * 300L;
                for (long sleptMs = 0L; sleptMs < Math.max(1500L, estimatedTimeMs); sleptMs += 100L) {
                    if (cancelCurrentSpeech) {
                        osNarrator.clear();
                        break;
                    }
                    Thread.sleep(100L);
                }
            }
            catch (Exception e) {
                System.err.println("[Verity Native TTS] Failed to use OS Narrator.");
                e.printStackTrace();
            }
            finally {
                Minecraft.m_91087_().execute(() -> {
                    if (verity != null) {
                        verity.clientIsTalking = false;
                    }
                    VerityState.isClientTalking = false;
                });
            }
        });
    }

    private static HttpRequest.Builder createAudioRequestBuilder() {
        if (VerityConfig.TTS_PROVIDER.get() == TTSProvider.KOKORO) {
            return HttpRequest.newBuilder().uri(URI.create((String)VerityConfig.TTS_ENDPOINT.get() + "/audio/speech")).version(HttpClient.Version.HTTP_1_1);
        }
        return HttpRequest.newBuilder().uri(URI.create("https://api.groq.com/openai/v1/audio/speech")).header("Authorization", "Bearer " + TTSHandler.getGroqApiKey());
    }

    public static void playLocalTTS(String text, Player player, VerityEntity verity) {
        if (!((Boolean)VerityConfig.USE_TTS.get()).booleanValue()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            cancelCurrentSpeech = false;
            TTSHandler.resetMuffledFilter();
            try {
                byte[] pcmData = LocalTTSBuilder.generateSpeech(text);
                if (pcmData == null || pcmData.length == 0) {
                    System.err.println("[Verity Local TTS] No audio generated (engine may have failed to load).");
                    return;
                }
                AudioFormat format = LocalTTSBuilder.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                try (SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);){
                    line.open(format);
                    line.start();
                    if (verity != null) {
                        verity.clientIsTalking = true;
                    }
                    VerityState.isClientTalking = true;
                    int chunkSize = 4096;
                    for (int offset = 0; offset < pcmData.length; offset += chunkSize) {
                        if (cancelCurrentSpeech) {
                            line.flush();
                            break;
                        }
                        int len = Math.min(chunkSize, pcmData.length - offset);
                        if (TTSHandler.isVerityMuffled(player, verity)) {
                            TTSHandler.applyMuffledFilter(pcmData, offset, len);
                        }
                        TTSHandler.apply3DEffect(line, player, verity);
                        line.write(pcmData, offset, len);
                    }
                    if (!cancelCurrentSpeech) {
                        line.drain();
                    }
                }
                finally {
                    if (verity != null) {
                        verity.clientIsTalking = false;
                    }
                    VerityState.isClientTalking = false;
                }
            }
            catch (Exception e) {
                if (verity != null) {
                    verity.clientIsTalking = false;
                }
                System.err.println("[Verity Local TTS] Failed to play local voice.");
                e.printStackTrace();
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void playEndpointTTS(Player player, String text, VerityEntity verity) {
        block34: {
            cancelCurrentSpeech = false;
            TTSHandler.resetMuffledFilter();
            try {
                JsonObject json = new JsonObject();
                if (VerityConfig.TTS_PROVIDER.get() == TTSProvider.KOKORO) {
                    json.addProperty("model", (String)VerityConfig.KOKORO_MODEL.get());
                    json.addProperty("input", text);
                    json.addProperty("voice", ((KokoroVoice)((Object)VerityConfig.KOKORO_VOICE.get())).name().toLowerCase(Locale.ROOT));
                } else {
                    json.addProperty("model", "canopylabs/orpheus-v1-english");
                    json.addProperty("input", text);
                    json.addProperty("voice", ((String)VerityConfig.VOICE.get()).toLowerCase());
                }
                json.addProperty("response_format", "wav");
                json.addProperty("speed", (Number)1.2);
                HttpRequest request = TTSHandler.createAudioRequestBuilder().header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json.toString())).build();
                HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30L)).build();
                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                if (response.statusCode() == 200) {
                    try (InputStream rawStream = response.body();
                         AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(rawStream));){
                        AudioFormat format = audioStream.getFormat();
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                        try (SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);){
                            int bytesRead;
                            line.open(format);
                            line.start();
                            if (verity != null) {
                                verity.clientIsTalking = true;
                            }
                            VerityState.isClientTalking = true;
                            byte[] buffer = new byte[4096];
                            while ((bytesRead = audioStream.read(buffer)) != -1) {
                                if (cancelCurrentSpeech) {
                                    line.flush();
                                    break;
                                }
                                if (TTSHandler.isVerityMuffled(player, verity)) {
                                    TTSHandler.applyMuffledFilter(buffer, 0, bytesRead);
                                }
                                TTSHandler.apply3DEffect(line, player, verity);
                                line.write(buffer, 0, bytesRead);
                            }
                            if (!cancelCurrentSpeech) {
                                line.drain();
                            }
                            break block34;
                        }
                        finally {
                            if (verity != null) {
                                verity.clientIsTalking = false;
                            }
                            VerityState.isClientTalking = false;
                        }
                    }
                }
                String errorBody = new String(response.body().readAllBytes());
                if (errorBody.contains("rate_limit_exceeded")) {
                    player.m_5661_((Component)Component.m_237113_((String)"You ran out of TTS tokens! Try switching to Native TTS.").m_130940_(ChatFormatting.RED), true);
                }
                System.out.println("[Verity TTS Error]: " + errorBody);
            }
            catch (Exception e) {
                if (verity != null) {
                    verity.clientIsTalking = false;
                }
                System.err.println("[Verity TTS] Failed to play voice.");
                e.printStackTrace();
            }
        }
    }

    public static void playTTS(String text, Player player, VerityEntity verity) {
        if (!((Boolean)VerityConfig.USE_TTS.get()).booleanValue()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            switch ((TTSProvider)((Object)((Object)VerityConfig.TTS_PROVIDER.get()))) {
                case NATIVE: {
                    TTSHandler.playNativeTTS(text, verity);
                    break;
                }
                case LOCAL: {
                    TTSHandler.playLocalTTS(text, player, verity);
                    break;
                }
                case GROQ: 
                case KOKORO: {
                    TTSHandler.playEndpointTTS(player, text, verity);
                }
            }
        }).whenComplete((answer, throwable) -> {
            if (throwable != null) {
                System.err.println("[Verity TTS] Failed playTTS.");
                throwable.printStackTrace();
            } else if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
                System.out.println("AiAPI response: " + String.valueOf(answer));
            }
        });
    }
}

