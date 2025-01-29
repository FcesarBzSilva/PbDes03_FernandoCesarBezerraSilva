package org.example.msticketmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;

@Configuration
public class MqConfig {
    @Value("${mq.queues.ticketQ}")
    private String queueName;

    @Bean
    public Queue queueTicketPublish(){
        return new Queue(queueName, true);
    }
}
