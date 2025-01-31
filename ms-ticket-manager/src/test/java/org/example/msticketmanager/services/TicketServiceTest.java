package org.example.msticketmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.msticketmanager.clients.EventResourceClient;
import org.example.msticketmanager.dto.EventDTO;
import org.example.msticketmanager.dto.TicketDTO;
import org.example.msticketmanager.exceptions.TicketNotFoundException;
import org.example.msticketmanager.infra.mqueue.TicketMqPublisher;
import org.example.msticketmanager.models.Ticket;
import org.example.msticketmanager.models.TicketDataMq;
import org.example.msticketmanager.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventResourceClient eventResourceClient;

    @Mock
    private TicketMqPublisher ticketMqPublisher;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket_ShouldCreateTicket_WhenEventExists() throws JsonProcessingException {
        Ticket ticket = new Ticket();
        ticket.setEventId("event123");

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId("event123");

        when(eventResourceClient.getEventById("event123")).thenReturn(eventDTO);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDTO result = ticketService.createTicket(ticket);

        assertNotNull(result);
        verify(ticketMqPublisher, times(1)).publishTicketNotification(any(TicketDataMq.class));
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void getTicketById_ShouldThrowException_WhenTicketDoesNotExist() {
        when(ticketRepository.findById("ticket123")).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getTicketById("ticket123");
        });
    }

    @Test
    void updateTicket_ShouldUpdateTicket_WhenTicketExists() {
        Ticket existingTicket = new Ticket();
        existingTicket.setTicketId("ticket123");
        existingTicket.setStatus("Complete");

        Ticket updatedTicket = new Ticket();
        updatedTicket.setCustomerName("New Customer");

        when(ticketRepository.findById("ticket123")).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        Ticket result = ticketService.updateTicket("ticket123", updatedTicket);

        assertNotNull(result);
        assertEquals("New Customer", result.getCustomerName());
        verify(ticketRepository, times(1)).save(existingTicket);
    }

    @Test
    void softDeleteTicket_ShouldSoftDeleteTicket_WhenTicketExists() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("ticket123");
        ticket.setStatus("Complete");

        when(ticketRepository.findById("ticket123")).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.softDeleteTicket("ticket123");

        assertNotNull(result);
        assertEquals("Canceled", result.getStatus());
        verify(ticketRepository, times(1)).save(ticket);
    }
}


