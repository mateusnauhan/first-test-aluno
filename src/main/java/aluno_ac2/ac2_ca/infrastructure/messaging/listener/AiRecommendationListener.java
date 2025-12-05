package aluno_ac2.ac2_ca.infrastructure.messaging.listener;

import aluno_ac2.ac2_ca.application.service.AiRecommendationService;
import aluno_ac2.ac2_ca.infrastructure.messaging.event.CursoConcluidoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
    Listener que consome CursoConcluidoEvent da fila de IA
    e chama o serviço de recomendação.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AiRecommendationListener {

    private final AiRecommendationService aiRecommendationService;

    @RabbitListener(queues = "${ac2.rabbitmq.queue.ai_recommendation}")
    public void onCursoConcluido(CursoConcluidoEvent event) {
        log.info("=== Início processamento AI Recommendation ===");
        log.info("Evento recebido para aluno {} (id={}): média={}, cursosConcluidos={}, cursosLiberados={}",
                event.alunoNome(),
                event.alunoId(),
                event.mediaFinal(),
                event.cursosConcluidos(),
                event.cursosLiberados()
        );

        String mensagem = aiRecommendationService.generateRecommendation(event);

        log.info("Mensagem final de recomendação:\n{}", mensagem);

        log.info("=== Fim processamento AI Recommendation ===");
    }
}
