package org.example.msticketmanager.services;

import org.example.msticketmanager.clients.EventResourceClient;
import org.example.msticketmanager.dto.EventDTO;
import org.example.msticketmanager.dto.TicketDTO;
import org.example.msticketmanager.models.Ticket;
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

    @Transactional
    public TicketDTO createTicket(Ticket ticket) {
        EventDTO event = eventResourceClient.getEventById(ticket.getEventId());

        if (event == null) {
            throw new IllegalArgumentException("Event does not exist!");
        }
        Ticket savedTicket = ticketRepository.save(ticket);
        return new TicketDTO(event, savedTicket);
    }
}
