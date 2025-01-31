package org.example.mseventmanager.services;


import org.example.mseventmanager.clients.TicketServiceClient;
import org.example.mseventmanager.clients.ViaCepClient;
import org.example.mseventmanager.exceptions.EventNotFoundException;
import org.example.mseventmanager.exceptions.InvalidCepException;
import org.example.mseventmanager.exceptions.InvalidEventException;
import org.example.mseventmanager.models.Address;
import org.example.mseventmanager.models.Event;
import org.example.mseventmanager.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private TicketServiceClient ticketServiceClient;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEvent_ShouldAddEvent_WhenDataIsValid() {
        Event event = new Event();
        event.setEventName("Sample Event");
        event.setCep("12345-678");

        Address address = new Address();
        address.setLogradouro("Sample Street");
        address.setBairro("Sample Neighborhood");
        address.setLocalidade("Sample City");
        address.setUf("SC");

        when(viaCepClient.getEndereco("12345-678")).thenReturn(address);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.addEvent(event);

        assertNotNull(result);
        assertEquals("Sample Street", result.getLogradouro());
        assertEquals("Sample City", result.getCidade());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void addEvent_ShouldThrowException_WhenEventNameIsInvalid() {
        Event event = new Event();
        event.setCep("12345-678");

        assertThrows(InvalidEventException.class, () -> {
            eventService.addEvent(event);
        });

        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void addEvent_ShouldThrowException_WhenCepIsInvalid() {
        Event event = new Event();
        event.setEventName("Sample Event");

        assertThrows(InvalidCepException.class, () -> {
            eventService.addEvent(event);
        });

        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void getEventById_ShouldReturnEvent_WhenEventExists() {
        Event event = new Event();
        event.setId("event123");

        when(eventRepository.findById("event123")).thenReturn(Optional.of(event));

        Event result = eventService.getEventById("event123");

        assertNotNull(result);
        assertEquals("event123", result.getId());
    }

    @Test
    void getEventById_ShouldThrowException_WhenEventDoesNotExist() {
        when(eventRepository.findById("event123")).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            eventService.getEventById("event123");
        });
    }

    @Test
    void deleteEventById_ShouldDeleteEvent_WhenNoTicketsSold() {
        Event event = new Event();
        event.setId("event123");

        when(ticketServiceClient.getTicketsByEventId("event123")).thenReturn(new ArrayList<>());
        when(eventRepository.findById("event123")).thenReturn(Optional.of(event));

        eventService.deleteEventById("event123");

        verify(eventRepository, times(1)).delete(event);
    }
}
