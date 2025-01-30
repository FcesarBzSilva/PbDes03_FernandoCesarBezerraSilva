package org.example.mseventmanager.services;

import org.example.mseventmanager.clients.ViaCepClient;
import org.example.mseventmanager.exceptions.EventNotFoundException;
import org.example.mseventmanager.exceptions.InvalidCepException;
import org.example.mseventmanager.exceptions.InvalidEventException;
import org.example.mseventmanager.models.Address;
import org.example.mseventmanager.models.Event;
import org.example.mseventmanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ViaCepClient viaCepClient;

    public Event addEvent(Event event) {
        Address address = viaCepClient.getEndereco(event.getCep());
        if (event.getEventName() == null || event.getEventName().isEmpty()) {
            throw new InvalidEventException("Event name cannot be null or empty.");
        }
        if (event.getCep() == null || event.getCep().isEmpty() ) {
            throw new InvalidCepException("Invalid CEP");
        } else {
            event.setLogradouro(address.getLogradouro());
            event.setBairro(address.getBairro());
            event.setCidade(address.getLocalidade());
            event.setUf(address.getUf());
        }
            return eventRepository.save(event);
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found for ID: " + id));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getAllEventsSorted() {
        List<Event> events = eventRepository.findAll();
        events.sort(Comparator.comparing(Event::getEventName));
        return events;
    }
}
