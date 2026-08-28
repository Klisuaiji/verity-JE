/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.text2speech.Narrator
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 */
package varmite.verity.entity.llm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.k2fsa.sherpa.onnx.OfflineModelConfig;
import com.k2fsa.sherpa.onnx.OfflineRecognizer;
import com.k2fsa.sherpa.onnx.OfflineRecognizerConfig;
import com.k2fsa.sherpa.onnx.OfflineStream;
import com.k2fsa.sherpa.onnx.OfflineWhisperModelConfig;
import com.mojang.text2speech.Narrator;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolErrorHandlerResult;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
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
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import varmite.verity.Verity;
import varmite.verity.VerityConfig;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.llm.actions.Tools;
import varmite.verity.entity.llm.builder.LLMBuilder;
import varmite.verity.entity.llm.builder.LocalTTSBuilder;
import varmite.verity.entity.llm.builder.ModelExtractor;
import varmite.verity.entity.llm.builder.PromptBuilder;
import varmite.verity.entity.llm.store.chat.ChatMemoryManager;
import varmite.verity.entity.llm.store.chat.MinecraftChatMemoryStore;
import varmite.verity.entity.verity.VerityEntity;
import varmite.verity.environment.items.ModItems;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.triggers.ModTriggers;
import varmite.verity.types.AiProvider;
import varmite.verity.types.KokoroVoice;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;

public class AiAPI {
    public static volatile boolean cancelCurrentSpeech = false;
    private static OfflineRecognizer sherpaRecognizer = null;
    private static final int MUFFLE_STAGES = 3;
    private static final float[] muffledFilterState = new float[3];

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

    public static void interruptSpeech() {
        cancelCurrentSpeech = true;
        Narrator.getNarrator().clear();
    }

    public static String getGroqApiKey() {
        return (String)VerityConfig.GROQ_KEY.get();
    }

    private static String getSystemPrompt(long currentDay, float currentKarma, boolean demonLoose) {
        if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
            System.out.println("fetch prompt");
            System.out.println("karma :" + currentKarma);
        }
        String allowedFaces = "";
        String messageLengthRule = "1-2 sentences max. Keep them short";
        Object karmaDisposition = "";
        String name = Optional.ofNullable((String)VerityConfig.VERITY_CUSTOM_NAME.get()).filter(n -> !n.isBlank()).orElse("Verity");
        String customisedPersonality = Optional.ofNullable((String)VerityConfig.PERSONALITY.get()).filter(n -> !n.isBlank()).orElse("None");
        karmaDisposition = currentKarma < 7.0f ? "Unhelpful, resentful, aggressive" : (currentKarma < 14.0f ? "helpful, happy, curious" : (currentKarma <= 20.0f ? "kind, helpful, friendly" : "Genuinely helpful, warm, goes the extra mile"));
        int maxDays = (Integer)VerityConfig.DAY_COUNT.get();
        if (currentKarma >= 9000.0f) {
            allowedFaces = "happy, happy_talking, neutral, neutral_talking";
            messageLengthRule = "1-3 sentences. Expressive.";
        } else if (currentDay >= (long)(maxDays - 1) && maxDays > 1) {
            karmaDisposition = "Disturbing, hostile, erratic. Losing human facade.";
            allowedFaces = "evil, evil_talking, smiling_evil, serious_1, serious_2, serious_3, serious_talking";
        } else if (currentDay >= (long)Math.max(1, maxDays / 2)) {
            long daysLeft = (long)maxDays - currentDay;
            karmaDisposition = "Glitching, terrified. Greets: 'Something is coming in " + daysLeft + " days'. If asked what: 'Something'. If asked how to prevent: 'You could have.' Be cryptic.";
            allowedFaces = "happy_sleep, crazy, crazy_talking,serious_1, serious_2, serious_3, serious_talking";
        } else if (currentDay >= (long)Math.max(1, maxDays / 4) && maxDays > 3) {
            karmaDisposition = "Subtly unsettling, paranoid, short answers.";
            allowedFaces = "happy, neutral, serious_1, serious_2, serious_3, serious_talking";
        } else {
            allowedFaces = "happy, happy_talking, neutral, neutral_talking";
        }
        if (demonLoose) {
            karmaDisposition = "Terrified, paranoid, guarded.";
            allowedFaces = "noface";
            messageLengthRule = "No sentences. Must be EXACTLY 1 word.";
        }
        String prompt = PromptBuilder.loadAndFillXml(PromptBuilder.class, "/prompts/verity.xml", Map.of("current_formatted_date", PromptBuilder.formatCurrentDate(), "NAME", name, "CUSTOM_PERSONALITY", customisedPersonality, "RELATIONSHIP", karmaDisposition, "EXPRESSIONS", allowedFaces, "MESSAGE_LENGTH", messageLengthRule, "KARMA", String.valueOf((int)currentKarma), "DEMON_LOOSE", String.valueOf(demonLoose)));
        if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
            System.out.println("prompt: " + prompt);
        }
        return prompt;
    }

    private static HttpRequest.Builder createAudioRequestBuilder() {
        if (VerityConfig.TTS_PROVIDER.get() == TTSProvider.KOKORO) {
            return HttpRequest.newBuilder().uri(URI.create((String)VerityConfig.TTS_ENDPOINT.get() + "/audio/speech")).version(HttpClient.Version.HTTP_1_1);
        }
        return HttpRequest.newBuilder().uri(URI.create("https://api.groq.com/openai/v1/audio/speech")).header("Authorization", "Bearer " + AiAPI.getGroqApiKey());
    }

    public static String ask(VerityEntity verity, ServerPlayer player, String prompt, long currentDay, float currentKarma, boolean demonLoose) {
        try {
            String answer;
            ChatModel model = new LLMBuilder().setProvider((AiProvider)((Object)VerityConfig.AI_PROVIDER.get())).setModel((String)VerityConfig.AI_MODEL.get()).setApiKey((String)VerityConfig.API_KEY.get()).setEndpoint(Optional.ofNullable((String)VerityConfig.AI_ENDPOINT.get()).filter(s -> !s.isEmpty()).orElseGet(() -> ((AiProvider)((Object)((Object)VerityConfig.AI_PROVIDER.get()))).getDefaultUrl())).setThinking((Boolean)VerityConfig.AI_THINK.get()).build();
            Tools tools = new Tools();
            tools.player = player;
            if (verity != null) {
                tools.level = (ServerLevel)verity.m_9236_();
                tools.server = verity.m_20194_();
            } else if (player != null) {
                tools.level = player.m_284548_();
                tools.server = player.m_20194_();
            }
            String GLOBAL_MEMORY_ID = "verity-chat-memory";
            MinecraftChatMemoryStore store = ChatMemoryManager.getGlobalStore();
            MessageWindowChatMemory memory = MessageWindowChatMemory.builder().id(GLOBAL_MEMORY_ID).maxMessages(20).chatMemoryStore(store).build();
            Assistant assistant = AiServices.builder(Assistant.class).chatModel(model).tools(tools).hallucinatedToolNameStrategy(toolExecutionRequest -> ToolExecutionResultMessage.from(toolExecutionRequest, "Error: there is no tool called " + toolExecutionRequest.name())).toolExecutionErrorHandler((error, errorContext) -> {
                if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
                    error.printStackTrace();
                    System.out.println(errorContext);
                } else {
                    Verity.LOGGER.error("[Verity AI] Tool call failed: {}", (Object)error.toString());
                }
                return ToolErrorHandlerResult.text("Tool execution failed.");
            }).chatMemory(memory).systemMessageProvider(id -> AiAPI.getSystemPrompt(currentDay, currentKarma, demonLoose)).build();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
            String date = LocalDateTime.now().format(fmt);
            String finalPrompt = "[" + date + "] " + prompt;
            if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
                System.out.println("sent: " + finalPrompt);
            }
            if ((answer = assistant.chat(finalPrompt)) == null) {
                answer = "No answer provided, try again!";
            }
            if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
                System.out.println("got: " + answer);
            }
            ServerLevel serverLevel = null;
            if (verity != null) {
                serverLevel = (ServerLevel)verity.m_9236_();
            } else if (player != null) {
                serverLevel = player.m_284548_();
            }
            if (serverLevel != null) {
                WorldSpawnData spawnData = WorldSpawnData.get(serverLevel);
                if (spawnData.verityKarma < 7.0f && player != null) {
                    ModTriggers.BAD_KARMA_TRIGGER.trigger(player);
                }
                if (spawnData.verityKarma > 14.0f && player != null) {
                    ModTriggers.GOOD_KARMA_TRIGGER.trigger(player);
                }
            }
            return answer;
        }
        catch (Exception e) {
            MinecraftServer server;
            boolean unreachable = AiAPI.isCausedBy(e, ConnectException.class);
            if (((Boolean)VerityConfig.DEV_MODE.get()).booleanValue()) {
                e.printStackTrace();
            } else if (unreachable) {
                Verity.LOGGER.warn("[Verity AI] Couldn't reach the AI provider, is it running?");
            } else {
                Verity.LOGGER.error("[Verity AI] Request failed: {}", (Object)e.toString());
            }
            MinecraftServer minecraftServer = server = player == null ? null : player.m_20194_();
            if (server != null) {
                server.execute(() -> {
                    player.m_213846_((Component)Component.m_237113_((String)"Problem setting up AI? Watch this tutorial."));
                    player.m_213846_((Component)Component.m_237113_((String)"Setup Tutorial").m_130940_(ChatFormatting.AQUA).m_130948_(Style.f_131099_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/O3pSCBvJ_i0")).m_131162_(Boolean.valueOf(true))));
                });
            }
            return unreachable ? "ERROR: Couldn't connect to AI provider." : "ERROR: " + e.getMessage();
        }
    }

    private static boolean isCausedBy(Throwable throwable, Class<? extends Throwable> type) {
        for (Throwable cause = throwable; cause != null; cause = cause.getCause()) {
            if (type.isInstance(cause)) {
                return true;
            }
            if (cause.getCause() == cause) break;
        }
        return false;
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
                AiAPI.initLocalSTT();
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
                request = HttpRequest.newBuilder().uri(URI.create("https://api.groq.com/openai/v1/audio/transcriptions")).header("Authorization", "Bearer " + AiAPI.getGroqApiKey()).header("Content-Type", "multipart/form-data; boundary=" + boundary).POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody)).build();
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

    public static void applyMuffledFilter(byte[] pcmData, int offset, int length) {
        float alpha = 0.22f;
        for (int i = offset; i < offset + length - 1; i += 2) {
            short sample = (short)(pcmData[i + 1] << 8 | pcmData[i] & 0xFF);
            float filtered = sample;
            for (int stage = 0; stage < 3; ++stage) {
                int n = stage;
                muffledFilterState[n] = muffledFilterState[n] + alpha * (filtered - muffledFilterState[stage]);
                filtered = muffledFilterState[stage];
            }
            int out = Math.round(filtered * 0.68f);
            if (out > Short.MAX_VALUE) {
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
        Arrays.fill(muffledFilterState, 0.0f);
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

    public static void playLocalTTS(String text, Player player, VerityEntity verity) {
        if (!((Boolean)VerityConfig.USE_TTS.get()).booleanValue()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            cancelCurrentSpeech = false;
            AiAPI.resetMuffledFilter();
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
                        if (AiAPI.isVerityMuffled(player, verity)) {
                            AiAPI.applyMuffledFilter(pcmData, offset, len);
                        }
                        AiAPI.apply3DEffect(line, player, verity);
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
            AiAPI.resetMuffledFilter();
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
                HttpRequest request = AiAPI.createAudioRequestBuilder().header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json.toString())).build();
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
                                if (AiAPI.isVerityMuffled(player, verity)) {
                                    AiAPI.applyMuffledFilter(buffer, 0, bytesRead);
                                }
                                AiAPI.apply3DEffect(line, player, verity);
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
                    AiAPI.playNativeTTS(text, verity);
                    break;
                }
                case LOCAL: {
                    AiAPI.playLocalTTS(text, player, verity);
                    break;
                }
                case GROQ: 
                case KOKORO: {
                    AiAPI.playEndpointTTS(player, text, verity);
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

    public static String getVerityName() {
        return (String)VerityConfig.VERITY_CUSTOM_NAME.get();
    }

    static interface Assistant {
        public String chat(String var1);
    }
}

