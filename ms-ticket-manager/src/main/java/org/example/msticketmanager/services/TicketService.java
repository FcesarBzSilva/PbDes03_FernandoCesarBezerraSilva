package org.example.msticketmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.msticketmanager.clients.EventResourceClient;
import org.example.msticketmanager.dto.EventDTO;
import org.example.msticketmanager.dto.TicketDTO;
import org.example.msticketmanager.exceptions.EventNotFoundException;
import org.example.msticketmanager.exceptions.TicketNotFoundException;
import org.example.msticketmanager.exceptions.TicketProcessingException;
import org.example.msticketmanager.infra.mqueue.TicketMqPublisher;
import org.example.msticketmanager.models.Ticket;
import org.example.msticketmanager.models.TicketDataMq;
import org.example.msticketmanager.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        EventDTO event;
        try {
            event = eventResourceClient.getEventById(ticket.getEventId());
        } catch (Exception e) {
            throw new TicketProcessingException("Failed to process ticket data.");
        }

        if (event == null) {
            throw new EventNotFoundException("Event does not exist!");
        }

        TicketDataMq ticketDataMq = new TicketDataMq(ticket.getCustomerName(),
                ticket.getCustomerEmail(), ticket.getCpf());

        try {
            ticketMqPublisher.publishTicketNotification(ticketDataMq);
        } catch (Exception e) {
            throw new TicketProcessingException("Failed to publish ticket notification. "+ e);
        }

        ticket.setStatus("Complete");

        Ticket savedTicket;
        try {
            savedTicket = ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new TicketProcessingException("Failed to save ticket. "+ e);
        }

        return new TicketDTO(event, savedTicket);
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id)
                .filter(u -> !u.getStatus().equals("Canceled"))
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found for ID: " + id));
    }

    public Ticket updateTicket(String id, Ticket updatedTicket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setCustomerName(updatedTicket.getCustomerName());
        existingTicket.setCustomerEmail(updatedTicket.getCustomerEmail());
        existingTicket.setCpf(updatedTicket.getCpf());
        try {
            return ticketRepository.save(existingTicket);
        } catch (Exception e) {
            throw new TicketProcessingException("Failed to update ticket. "+ e);
        }
    }

    public Ticket softDeleteTicket(String id) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus("Canceled");
        try {
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new TicketProcessingException("Failed to delete ticket. "+ e);
        }
    }

    public List<Ticket> getTicketsByEventId(String eventId) {
        try {
            return ticketRepository.findByEventIdAndStatus(eventId, "Complete");
        } catch (Exception e) {
            throw new TicketProcessingException("Failed to fetch tickets by event ID. "+ e);
        }
    }
}
