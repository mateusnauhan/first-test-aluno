package aluno_ac2.ac2_ca.infrastructure.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
   Configuração do RabbitMQ.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${ac2.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${ac2.rabbitmq.queue.ai_recommendation}")
    private String aiRecommendationQueueName;

    @Value("${ac2.rabbitmq.queue.dlq}")
    private String dlqName;

    @Value("${ac2.rabbitmq.routing_key}")
    private String routingKey;

    // Exchange principal
    @Bean
    public TopicExchange ac2Exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(dlqName).build();
    }

    // Fila de IA, com DLQ configurada
    @Bean
    public Queue aiRecommendationQueue() {
        return QueueBuilder.durable(aiRecommendationQueueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    // Binding da fila de IA no exchange
    @Bean
    public Binding aiRecommendationBinding(Queue aiRecommendationQueue,
                                           TopicExchange ac2Exchange) {
        return BindingBuilder
                .bind(aiRecommendationQueue)
                .to(ac2Exchange)
                .with(routingKey);
    }

    // Conversor JSON
    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Template do RabbitMQ
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter jacksonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonMessageConverter);
        return rabbitTemplate;
    }
}
