/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 *
 * 6.1 replaced the hand-rolled JSON/HTTP AI client with langchain4j:
 *   - the model is built by LLMBuilder from the AI_PROVIDER config enum
 *   - world interaction happens through @Tool methods on {@link Tools}
 *   - conversation history lives in a MessageWindowChatMemory backed by
 *     MinecraftChatMemoryStore
 *   - the system prompt is an XML template (/prompts/verity.xml)
 *
 * Porting notes:
 *   - SRG names (m_xxx_/f_xxx_) replaced with official Mojang mappings.
 *   - The optional sherpa-onnx STT engine is reached through the reflective
 *     SherpaBridge instead of a hard compile-time dependency.
 *   - Client-only feedback (Minecraft.getInstance()) is guarded behind
 *     FMLEnvironment.dist so a dedicated server never loads client classes.
 */
package varmite.verity.entity.LLM;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.text2speech.Narrator;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolErrorHandlerResult;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import varmite.verity.AiProvider;
import varmite.verity.KokoroVoice;
import varmite.verity.VerityConfig;
import varmite.verity.entity.AI.SherpaBridge;
import varmite.verity.entity.LLM.actions.Tools;
import varmite.verity.entity.LLM.builder.LLMBuilder;
import varmite.verity.entity.LLM.builder.LocalTTSBuilder;
import varmite.verity.entity.LLM.builder.PromptBuilder;
import varmite.verity.entity.LLM.store.chat.ChatMemoryManager;
import varmite.verity.entity.LLM.store.chat.MinecraftChatMemoryStore;
import varmite.verity.entity.VerityState;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.triggers.ModTriggers;
import varmite.verity.types.STTProvider;
import varmite.verity.types.TTSProvider;
import varmite.verity.util.ModelExtractor;

public class AiAPI {
    private static final String GLOBAL_MEMORY_ID = "verity-chat-memory";

    public static volatile boolean cancelCurrentSpeech = false;

    /** Reflective handle on com.k2fsa.sherpa.onnx.OfflineRecognizer (may stay null). */
    private static Object sherpaRecognizer = null;

    // --------------------------------------------------------------- audio fx

    public static void apply3DEffect(SourceDataLine line, Player player, VerityEntity verity) {
        if (line == null || player == null || verity == null) {
            return;
        }
        double distance = player.position().distanceTo(verity.position());
        float maxDist = 32.0f;
        float volumeMultiplier = 1.0f - (float) (distance / (double) maxDist);
        volumeMultiplier = Math.max(0.0f, Math.min(1.0f, volumeMultiplier));

        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = volumeMultiplier <= 0.001f ? -80.0f : (float) (Math.log10(volumeMultiplier) * 20.0);
            volControl.setValue(dB);
        }
        if (line.isControlSupported(FloatControl.Type.PAN)) {
            Vec3 toVerity = verity.position().subtract(player.position()).normalize();
            Vec3 playerLook = player.getViewVector(1.0f).normalize();
            double pan = playerLook.x * toVerity.z - playerLook.z * toVerity.x;
            float finalPan = (float) Math.max(-1.0, Math.min(1.0, pan));
            FloatControl panControl = (FloatControl) line.getControl(FloatControl.Type.PAN);
            panControl.setValue(finalPan);
        }
    }

    public static void interruptSpeech() {
        cancelCurrentSpeech = true;
        try {
            Narrator.getNarrator().clear();
        } catch (Throwable ignored) {
            // Narrator is client-only and may be unavailable in some environments.
        }
    }

    public static String getGroqApiKey() {
        return VerityConfig.GROQ_KEY.get();
    }

    // ------------------------------------------------------------ system prompt

    private static String getSystemPrompt(long currentDay, float currentKarma) {
        String allowedFaces;
        String messageLengthRule = "Message length: 1-2 sentences max. Keep them short";
        String karmaDisposition;

        String name = VerityConfig.VERITY_CUSTOM_NAME.get();
        if (name == null || name.isBlank()) {
            name = "Verity";
        }
        String customisedPersonality = VerityConfig.PERSONALITY.get();
        if (customisedPersonality == null) {
            customisedPersonality = "normal";
        }

        karmaDisposition = currentKarma < 7.0f
                ? "Unhelpful, resentful, aggressive"
                : (currentKarma < 14.0f
                        ? "helpful, happy, curious"
                        : (currentKarma <= 20.0f
                                ? "kind, helpful, friendly"
                                : "Genuinely helpful, warm, goes the extra mile"));

        int maxDays = VerityConfig.DAY_COUNT.get();

        if (currentKarma >= 9000.0f) {
            allowedFaces = "happy, happy_talking, neutral, neutral_talking";
            messageLengthRule = "Message length: 1-3 sentences. Be expressive and warm.";
        } else if (currentDay >= (long) (maxDays - 1) && maxDays > 1) {
            karmaDisposition = "Disturbing, hostile, erratic. Losing human facade.";
            allowedFaces = "evil, evil_talking, smiling_evil, serious_1, serious_2, serious_3, serious_talking";
        } else if (currentDay >= (long) Math.max(1, maxDays / 2)) {
            long daysLeft = (long) maxDays - currentDay;
            karmaDisposition = "Glitching, terrified. Greets: 'Something is coming in " + daysLeft
                    + " days'. If asked what: 'Something'. If asked how to prevent: 'You could have.' Be cryptic.";
            allowedFaces = "happy_sleep, crazy, crazy_talking,serious_1, serious_2, serious_3, serious_talking";
        } else if (currentDay >= (long) Math.max(1, maxDays / 4) && maxDays > 3) {
            karmaDisposition = "Subtly unsettling, paranoid, short answers.";
            allowedFaces = "happy, neutral, serious_1, serious_2, serious_3, serious_talking";
        } else {
            allowedFaces = "happy, happy_talking, neutral, neutral_talking";
        }

        if (VerityState.isMonstrous) {
            karmaDisposition = "evil, hostile, terrifying.";
            allowedFaces = "noface";
            messageLengthRule = "MESSAGE MUST be exactly ONE word (e.g., 'Die', 'Run'). NO sentences.";
        }

        String finalCustomised = Objects.equals(customisedPersonality, "normal") ? "" : customisedPersonality;

        return PromptBuilder.loadAndFillXml(
                PromptBuilder.class,
                "/prompts/verity.xml",
                Map.of(
                        "current_formatted_date", PromptBuilder.formatCurrentDate(),
                        "NAME", name,
                        "CUSTOM_PERSONALITY", finalCustomised,
                        "RELATIONSHIP", karmaDisposition,
                        "EXPRESSIONS", allowedFaces,
                        "MESSAGE_LENGTH", messageLengthRule,
                        "KARMA", Float.toString(currentKarma)));
    }

    private static HttpRequest.Builder createAudioRequestBuilder() {
        if (VerityConfig.TTS_PROVIDER.get() == TTSProvider.KOKORO) {
            return HttpRequest.newBuilder()
                    .uri(URI.create(VerityConfig.TTS_ENDPOINT.get() + "/audio/speech"))
                    .version(HttpClient.Version.HTTP_1_1);
        }
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.groq.com/openai/v1/audio/speech"))
                .header("Authorization", "Bearer " + getGroqApiKey());
    }

    // -------------------------------------------------------------------- ask

    public static String ask(
            VerityEntity verity, ServerPlayer player, String prompt, long currentDay, float currentKarma) {
        try {
            AiProvider provider = VerityConfig.AI_PROVIDER.get();
            ChatModel model = new LLMBuilder()
                    .setProvider(provider)
                    .setModel(VerityConfig.AI_MODEL.get())
                    .setApiKey(VerityConfig.API_KEY.get())
                    .setEndpoint(Optional.ofNullable(VerityConfig.AI_ENDPOINT.get())
                            .filter(s -> !s.isEmpty())
                            .orElseGet(provider::getDefaultUrl))
                    .setThinking(VerityConfig.AI_THINK.get())
                    .build();

            Tools tools = new Tools();
            tools.player = player;
            tools.level = (ServerLevel) verity.level();
            tools.server = verity.getServer();

            MinecraftChatMemoryStore store = ChatMemoryManager.getGlobalStore();
            MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                    .id(GLOBAL_MEMORY_ID)
                    .maxMessages(20)
                    .chatMemoryStore((ChatMemoryStore) store)
                    .build();

            Assistant assistant = AiServices.builder(Assistant.class)
                    .chatModel(model)
                    .tools(tools)
                    .toolExecutionErrorHandler((error, errorContext) -> {
                        error.printStackTrace();
                        System.out.println(errorContext);
                        return ToolErrorHandlerResult.text("Tool execution failed.");
                    })
                    .chatMemory((ChatMemory) memory)
                    .systemMessageProvider(id -> getSystemPrompt(currentDay, currentKarma))
                    .build();

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
            String date = LocalDateTime.now().format(fmt);
            String finalPrompt = "[" + date + "] " + prompt;

            String answer = assistant.chat(finalPrompt);
            if (answer == null) {
                answer = "No answer provided, try again!";
            }

            ServerLevel serverLevel = (ServerLevel) verity.level();
            WorldSpawnData spawnData = WorldSpawnData.get(serverLevel);
            if (player != null) {
                if (spawnData.verityKarma < 7.0f) {
                    ModTriggers.BAD_KARMA_TRIGGER.get().trigger(player);
                }
                if (spawnData.verityKarma > 14.0f) {
                    ModTriggers.GOOD_KARMA_TRIGGER.get().trigger(player);
                }
            }
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            showSetupHint();
            Throwable cause = e.getCause();
            if (cause instanceof ConnectException) {
                return "ERROR: Couldn't connect to AI provider.";
            }
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Client-side "your AI isn't configured" nudge. Kept behind a dist check so
     * the dedicated server never touches net.minecraft.client.
     */
    private static void showSetupHint() {
        if (FMLEnvironment.dist != Dist.CLIENT) {
            return;
        }
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc == null || mc.player == null) {
                return;
            }
            mc.player.sendSystemMessage(Component.literal("Problem setting up AI? Watch this tutorial."));
            MutableComponent message = Component.literal("Setup Tutorial")
                    .withStyle(ChatFormatting.AQUA)
                    .withStyle(Style.EMPTY
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/O3pSCBvJ_i0"))
                            .withUnderlined(Boolean.TRUE));
            mc.player.sendSystemMessage(message);
        } catch (Throwable ignored) {
            // Never let the hint itself break error reporting.
        }
    }

    // -------------------------------------------------------------------- STT

    public static void initLocalSTT() {
        if (VerityConfig.STT_PROVIDER.get() != STTProvider.NATIVE) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            try {
                if (!SherpaBridge.isAvailable()) {
                    System.err.println("[Verity] sherpa-onnx is not on the classpath; native STT stays disabled.");
                    return;
                }
                Path modelPath = ModelExtractor.getOrExtractModel();
                sherpaRecognizer = SherpaBridge.createRecognizer(
                        modelPath.resolve("tiny.en-encoder.int8.onnx").toString(),
                        modelPath.resolve("tiny.en-decoder.int8.onnx").toString(),
                        modelPath.resolve("tiny.en-tokens.txt").toString(),
                        2);
                if (sherpaRecognizer != null) {
                    System.out.println("[Verity] Offline Sherpa-ONNX Engine initialized!");
                } else {
                    System.err.println("[Verity] Failed to load Sherpa model. Check your model filenames!");
                }
            } catch (Throwable e) {
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
                initLocalSTT();
                return "";
            }
            try {
                float[] floatAudio = new float[pcmData.length / 2];
                for (int i = 0; i + 1 < pcmData.length; i += 2) {
                    short sample = (short) (pcmData[i + 1] << 8 | pcmData[i] & 0xFF);
                    floatAudio[i / 2] = (float) sample / 32768.0f;
                }
                String result = SherpaBridge.recognize(sherpaRecognizer, floatAudio, (int) format.getSampleRate());
                return result != null ? result.trim() : "";
            } catch (Throwable e) {
                System.err.println("[Verity STT] Failed to process speech locally with Sherpa.");
                e.printStackTrace();
                return "";
            }
        }

        try {
            byte[] wavData;
            try (ByteArrayInputStream bais = new ByteArrayInputStream(pcmData);
                    AudioInputStream ais =
                            new AudioInputStream(bais, format, pcmData.length / format.getFrameSize());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, baos);
                wavData = baos.toByteArray();
            }

            String boundary = "----VerityBoundary" + System.currentTimeMillis();
            ByteArrayOutputStream bodyStream = new ByteArrayOutputStream();
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            boolean whisper = VerityConfig.STT_PROVIDER.get() == STTProvider.WHISPER;

            bodyStream.write((twoHyphens + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
            bodyStream.write(("Content-Disposition: form-data; name=\"model\"" + lineEnd + lineEnd)
                    .getBytes(StandardCharsets.UTF_8));
            if (whisper) {
                bodyStream.write((VerityConfig.STT_MODEL.get() + lineEnd).getBytes(StandardCharsets.UTF_8));
            } else {
                bodyStream.write(("whisper-large-v3-turbo" + lineEnd).getBytes(StandardCharsets.UTF_8));
            }
            bodyStream.write((twoHyphens + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
            if (whisper) {
                bodyStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"audio.wav\"" + lineEnd)
                        .getBytes(StandardCharsets.UTF_8));
                bodyStream.write(("Content-Type: audio/wav" + lineEnd + lineEnd).getBytes(StandardCharsets.UTF_8));
            } else {
                bodyStream.write(
                        ("Content-Disposition: form-data; name=\"file\"; filename=\"audio.wav\"" + lineEnd + lineEnd)
                                .getBytes(StandardCharsets.UTF_8));
            }
            bodyStream.write(wavData);
            bodyStream.write(lineEnd.getBytes(StandardCharsets.UTF_8));
            bodyStream.write((twoHyphens + boundary + twoHyphens + lineEnd).getBytes(StandardCharsets.UTF_8));
            byte[] multipartBody = bodyStream.toByteArray();

            HttpRequest request;
            if (whisper) {
                String sttEndpoint = Optional.ofNullable(VerityConfig.STT_ENDPOINT.get())
                        .filter(s -> !s.isEmpty())
                        .orElseGet(() -> VerityConfig.STT_PROVIDER.get().getDefaultUrl());
                request = HttpRequest.newBuilder()
                        .uri(URI.create(sttEndpoint + "audio/transcriptions"))
                        .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                        .version(HttpClient.Version.HTTP_1_1)
                        .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.groq.com/openai/v1/audio/transcriptions"))
                        .header("Authorization", "Bearer " + getGroqApiKey())
                        .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                        .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                        .build();
            }

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30L))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
                String transcribedText = responseJson.get("text").getAsString().trim();
                if (".".equals(transcribedText) || transcribedText.isEmpty()) {
                    return "";
                }
                return transcribedText;
            }
            System.err.println("[Verity STT Error]: " + response.statusCode() + " - " + response.body());
            return "";
        } catch (Exception e) {
            System.err.println("[Verity STT] Failed to process speech upload stream.");
            e.printStackTrace();
            return "";
        }
    }

    // -------------------------------------------------------------------- TTS

    public static void playNativeTTS(String text, VerityEntity verity) {
        if (!VerityConfig.USE_TTS.get()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            cancelCurrentSpeech = false;
            try {
                setTalking(verity, true);
                Narrator osNarrator = Narrator.getNarrator();
                osNarrator.say(text, true);

                int wordCount = text.split("\\s+").length;
                int punctuationCount = text.replaceAll("[^.,!?]", "").length();
                long estimatedTimeMs = (long) wordCount * 400L + (long) punctuationCount * 300L;
                for (long sleptMs = 0L; sleptMs < Math.max(1500L, estimatedTimeMs); sleptMs += 100L) {
                    if (cancelCurrentSpeech) {
                        osNarrator.clear();
                        break;
                    }
                    Thread.sleep(100L);
                }
            } catch (Exception e) {
                System.err.println("[Verity Native TTS] Failed to use OS Narrator.");
                e.printStackTrace();
            } finally {
                setTalking(verity, false);
            }
        });
    }

    private static void setTalking(VerityEntity verity, boolean talking) {
        if (verity == null) {
            return;
        }
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Minecraft.getInstance().execute(() -> verity.clientIsTalking = talking);
        } else {
            verity.clientIsTalking = talking;
        }
    }

    public static void playLocalTTS(String text, Player player, VerityEntity verity) {
        if (!VerityConfig.USE_TTS.get()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            cancelCurrentSpeech = false;
            try {
                byte[] pcmData = LocalTTSBuilder.generateSpeech(text);
                if (pcmData == null || pcmData.length == 0) {
                    System.err.println("[Verity Local TTS] No audio generated (engine may have failed to load).");
                    return;
                }
                AudioFormat format = LocalTTSBuilder.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                    line.open(format);
                    line.start();
                    if (verity != null) {
                        verity.clientIsTalking = true;
                    }
                    int chunkSize = 4096;
                    for (int offset = 0; offset < pcmData.length; offset += chunkSize) {
                        if (cancelCurrentSpeech) {
                            line.flush();
                            break;
                        }
                        int len = Math.min(chunkSize, pcmData.length - offset);
                        apply3DEffect(line, player, verity);
                        line.write(pcmData, offset, len);
                    }
                    if (!cancelCurrentSpeech) {
                        line.drain();
                    }
                } finally {
                    if (verity != null) {
                        verity.clientIsTalking = false;
                    }
                }
            } catch (Exception e) {
                if (verity != null) {
                    verity.clientIsTalking = false;
                }
                System.err.println("[Verity Local TTS] Failed to play local voice.");
                e.printStackTrace();
            }
        });
    }

    public static void playEndpointTTS(Player player, String text, VerityEntity verity) {
        cancelCurrentSpeech = false;
        try {
            JsonObject json = new JsonObject();
            if (VerityConfig.TTS_PROVIDER.get() == TTSProvider.KOKORO) {
                json.addProperty("model", VerityConfig.KOKORO_MODEL.get());
                json.addProperty("input", text);
                KokoroVoice kokoroVoice = VerityConfig.KOKORO_VOICE.get();
                json.addProperty("voice", kokoroVoice.name().toLowerCase(Locale.ROOT));
            } else {
                json.addProperty("model", "canopylabs/orpheus-v1-english");
                json.addProperty("input", text);
                json.addProperty("voice", VerityConfig.VOICE.get().toLowerCase(Locale.ROOT));
            }
            json.addProperty("response_format", "wav");
            json.addProperty("speed", 1.2);

            HttpRequest request = createAudioRequestBuilder()
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30L))
                    .build();
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() == 200) {
                try (InputStream rawStream = response.body();
                        AudioInputStream audioStream =
                                AudioSystem.getAudioInputStream(new BufferedInputStream(rawStream))) {
                    AudioFormat format = audioStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                        line.open(format);
                        line.start();
                        if (verity != null) {
                            verity.clientIsTalking = true;
                        }
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = audioStream.read(buffer)) != -1) {
                            if (cancelCurrentSpeech) {
                                line.flush();
                                break;
                            }
                            apply3DEffect(line, player, verity);
                            line.write(buffer, 0, bytesRead);
                        }
                        if (!cancelCurrentSpeech) {
                            line.drain();
                        }
                    } finally {
                        if (verity != null) {
                            verity.clientIsTalking = false;
                        }
                    }
                }
                return;
            }

            String errorBody = new String(response.body().readAllBytes(), StandardCharsets.UTF_8);
            if (errorBody.contains("rate_limit_exceeded") && player != null) {
                player.displayClientMessage(
                        Component.literal("You ran out of TTS tokens! Try switching to Native TTS.")
                                .withStyle(ChatFormatting.RED),
                        true);
            }
            System.out.println("[Verity TTS Error]: " + errorBody);
        } catch (Exception e) {
            if (verity != null) {
                verity.clientIsTalking = false;
            }
            System.err.println("[Verity TTS] Failed to play voice.");
            e.printStackTrace();
        }
    }

    public static void playTTS(String text, Player player, VerityEntity verity) {
        if (!VerityConfig.USE_TTS.get()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
                    switch (VerityConfig.TTS_PROVIDER.get()) {
                        case NATIVE -> playNativeTTS(text, verity);
                        case LOCAL -> playLocalTTS(text, player, verity);
                        case GROQ, KOKORO -> playEndpointTTS(player, text, verity);
                    }
                })
                .whenComplete((unused, throwable) -> {
                    if (throwable != null) {
                        System.err.println("[Verity TTS] Failed playTTS.");
                        throwable.printStackTrace();
                    }
                });
    }

    interface Assistant {
        String chat(String message);
    }
}
