package org.example.mseventmanager.services;

import org.example.mseventmanager.clients.ViaCepClient;
import org.example.mseventmanager.models.Address;
import org.example.mseventmanager.models.Event;
import org.example.mseventmanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ViaCepClient viaCepClient;

    public Event addEvent(Event event) {
        Address address = viaCepClient.getEndereco(event.getCep());

        if (address != null) {
            event.setLogradouro(address.getLogradouro());
            event.setBairro(address.getBairro());
            event.setCidade(address.getLocalidade());
            event.setUf(address.getUf());
        }
        return eventRepository.save(event);
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id).get();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
