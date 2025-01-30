package org.example.msticketmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.msticketmanager.clients.EventResourceClient;
import org.example.msticketmanager.dto.EventDTO;
import org.example.msticketmanager.dto.TicketDTO;
import org.example.msticketmanager.infra.mqueue.TicketMqPublisher;
import org.example.msticketmanager.models.Ticket;
import org.example.msticketmanager.models.TicketDataMq;
import org.example.msticketmanager.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventResourceClient eventResourceClient;

    @Autowired
    private TicketMqPublisher ticketMqPublisher;

    @Transactional
    public TicketDTO createTicket(Ticket ticket) throws JsonProcessingException {
        EventDTO event = eventResourceClient.getEventById(ticket.getEventId());

        if (event == null) {
            throw new IllegalArgumentException("Event does not exist!");
        }

        TicketDataMq ticketDataMq = new TicketDataMq(ticket.getCustomerName(),
                ticket.getCustomerEmail(), ticket.getCpf());

        ticketMqPublisher.publishTicketNotification(ticketDataMq);

        ticket.setStatus("Complete");

        Ticket savedTicket = ticketRepository.save(ticket);

        return new TicketDTO(event, savedTicket);
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id)
                .filter(u -> !u.getStatus().equals("Canceled"))
                .orElseThrow(() -> new RuntimeException("Event not found for ID: " + id));
    }

    public Ticket updateTicket(String id, Ticket updatedTicket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setCustomerName(updatedTicket.getCustomerName());
        existingTicket.setCustomerEmail(updatedTicket.getCustomerEmail());
        existingTicket.setCpf(updatedTicket.getCpf());
        return ticketRepository.save(existingTicket);
    }

    public Ticket softDeleteTicket(String id) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus("Canceled");
        return ticketRepository.save(ticket);
    }
}
