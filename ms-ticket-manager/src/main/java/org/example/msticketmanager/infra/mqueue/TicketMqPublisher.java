package org.example.msticketmanager.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.msticketmanager.models.TicketDataMq;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMqPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueTicket;

    public void publishTicketNotification(TicketDataMq ticketDataMQ) throws JsonProcessingException {
        var json = convertIntoJson(ticketDataMQ);
        rabbitTemplate.convertAndSend(queueTicket.getName(), json);
    }

    private String convertIntoJson(TicketDataMq ticketDataMQ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(ticketDataMQ);
        return json;
    }
}
