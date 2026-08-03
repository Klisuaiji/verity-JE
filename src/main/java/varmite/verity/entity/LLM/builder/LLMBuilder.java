/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 * Builds a langchain4j ChatModel for the configured provider.
 */
package varmite.verity.entity.LLM.builder;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModelName;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import varmite.verity.AiProvider;

public class LLMBuilder {
    private AiProvider provider;
    private String model;
    private String endpoint;
    private String apiKey;
    private boolean thinking = false;

    public LLMBuilder setProvider(AiProvider provider) {
        this.provider = provider;
        return this;
    }

    public LLMBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public LLMBuilder setEndpoint(String endpoint) {
        // Java's HttpClient resolves "localhost" via IPv6 first on Windows, which
        // Ollama does not bind by default — force the IPv4 loopback.
        this.endpoint = endpoint == null ? null : endpoint.replace("localhost", "127.0.0.1");
        return this;
    }

    public LLMBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public LLMBuilder setThinking(boolean thinking) {
        this.thinking = thinking;
        return this;
    }

    public ChatModel build() {
        return switch (this.provider) {
            case OLLAMA -> OllamaChatModel.builder()
                    .baseUrl(this.endpoint)
                    .modelName(this.model)
                    .numCtx(16000)
                    .think(this.thinking)
                    .temperature(0.8)
                    .build();
            case OPENAI -> OpenAiChatModel.builder()
                    .baseUrl(this.endpoint)
                    .apiKey(this.apiKey)
                    .modelName(this.model)
                    .returnThinking(this.thinking)
                    .temperature(0.8)
                    .build();
            case GEMINI -> GoogleAiGeminiChatModel.builder()
                    .apiKey(this.apiKey)
                    .modelName(this.model)
                    .temperature(0.8)
                    .build();
            case GROQ -> OpenAiChatModel.builder()
                    .baseUrl("https://api.groq.com/openai/v1/")
                    .apiKey(this.apiKey)
                    .modelName(this.model)
                    .returnThinking(this.thinking)
                    .temperature(0.8)
                    .build();
            case OPENROUTER -> OpenAiChatModel.builder()
                    .baseUrl("https://openrouter.ai/api/v1/")
                    .apiKey(this.apiKey)
                    .modelName(this.model)
                    .returnThinking(this.thinking)
                    .temperature(0.8)
                    .build();
            case MISTRAL -> MistralAiChatModel.builder()
                    .apiKey(this.apiKey)
                    .modelName(MistralAiChatModelName.MISTRAL_SMALL_LATEST)
                    .temperature(0.8)
                    .build();
        };
    }
}
