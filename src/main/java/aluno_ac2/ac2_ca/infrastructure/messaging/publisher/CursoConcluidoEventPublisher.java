package aluno_ac2.ac2_ca.infrastructure.messaging.publisher;

import aluno_ac2.ac2_ca.infrastructure.messaging.event.CursoConcluidoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
    Publisher responsável por enviar o CursoConcluidoEvent para o RabbitMQ.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CursoConcluidoEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${ac2.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${ac2.rabbitmq.routing_key}")
    private String routingKey;

    public void publish(CursoConcluidoEvent event) {
        try {
            log.info(
                    "Publicando CursoConcluidoEvent: alunoId={}, mediaFinal={}, cursosConcluidos={}, cursosLiberados={}, plano={}",
                    event.alunoId(), event.mediaFinal(),
                    event.cursosConcluidos(), event.cursosLiberados(), event.plano()
            );

            rabbitTemplate.convertAndSend(exchangeName, routingKey, event);

            log.debug("Evento publicado no exchange '{}' com routing key '{}'",
                    exchangeName, routingKey);
        } catch (AmqpException e) {
            log.error("Falha ao publicar CursoConcluidoEvent", e);
        }
    }
}
