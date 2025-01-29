package org.example.msticketmanager.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    @Value("${mq.queues.ticketQ}")
    private String queueName;

    @Bean
    public Queue queueTicket() {
        return new Queue(queueName, true);
    }
}
