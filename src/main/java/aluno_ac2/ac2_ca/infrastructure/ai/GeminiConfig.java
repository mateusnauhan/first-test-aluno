package aluno_ac2.ac2_ca.infrastructure.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
   Configuração do modelo de linguagem Google Gemini via LangChain4j.
 */
@Slf4j
@Configuration
public class GeminiConfig {

    @Value("${gemini.api-key:}")
    private String apiKey;

    @Value("${gemini.model:gemini-2.5-flash}")
    private String modelName;

    @Value("${gemini.temperature:0.7}")
    private double temperature;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("GEMINI_API_KEY não configurada. IA ficará desabilitada (usaremos fallback).");
            return null;
        }

        log.info("Configurando Gemini AI com modelo {} e temperatura {}", modelName, temperature);

        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(temperature)
                .build();
    }
}
