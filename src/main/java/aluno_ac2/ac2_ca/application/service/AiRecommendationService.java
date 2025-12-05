package aluno_ac2.ac2_ca.application.service;

import aluno_ac2.ac2_ca.infrastructure.messaging.event.CursoConcluidoEvent;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
   Serviço responsável por gerar recomendações personalizadas usando IA (Gemini),
 */
@Slf4j
@Service
public class AiRecommendationService {

    private final ChatLanguageModel chatModel;

    public AiRecommendationService(@Nullable ChatLanguageModel chatModel) {
        this.chatModel = chatModel;
        if (chatModel == null) {
            log.warn("AiRecommendationService inicializado SEM modelo de IA. Será usado fallback.");
        }
    }

    public String generateRecommendation(CursoConcluidoEvent event) {
        if (chatModel == null) {
            log.info("Modelo de IA não disponível. Gerando mensagem de fallback.");
            return generateFallbackMessage(event);
        }

        String prompt = buildPrompt(event);

        try {
            String resposta = chatModel.generate(prompt);
            log.info("Recomendação gerada pela IA para aluno {}:\n{}",
                    event.alunoNome(), resposta);
            return resposta;
        } catch (Exception e) {
            log.error("Erro ao gerar recomendação de IA, usando fallback", e);
            return generateFallbackMessage(event);
        }
    }

    private String buildPrompt(CursoConcluidoEvent event) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Você é um assistente educacional amigável.\n\n");
        prompt.append("Dados do aluno:\n");
        prompt.append("Nome: ").append(event.alunoNome()).append("\n");
        prompt.append("Email: ").append(event.alunoEmail()).append("\n");
        prompt.append("Plano: ").append(event.plano()).append("\n");
        prompt.append("Média final: ").append(String.format("%.2f", event.mediaFinal())).append("\n");
        prompt.append("Cursos concluídos: ").append(event.cursosConcluidos()).append("\n");
        prompt.append("Cursos liberados atualmente: ").append(event.cursosLiberados()).append("\n\n");

        prompt.append("Com base nesses dados, escreva uma mensagem motivacional e ");
        prompt.append("Sugira próximos passos de estudo (por exemplo, mais cursos, upgrade de plano, etc.). ");
        prompt.append("Responda em português, em tom amigável, em até 3 parágrafos.\n");

        return prompt.toString();
    }

    private String generateFallbackMessage(CursoConcluidoEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Olá, ").append(event.alunoNome()).append("!\n\n");
        sb.append("Parabéns pelo seu progresso na plataforma. ");
        sb.append("Sua média atual é ").append(String.format("%.2f", event.mediaFinal())).append(" e ");
        sb.append("Você já concluiu ").append(event.cursosConcluidos()).append(" curso(s).\n\n");
        sb.append("Continue aproveitando os cursos liberados do seu plano ");
        sb.append(event.plano()).append(" e mantenha o foco nos estudos!");
        return sb.toString();
    }
}
