package org.example.msticketmanager.clients;

import org.example.msticketmanager.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080", path = "/events", value = "ms-event-manager")
public interface EventResourceClient {
    @GetMapping("/get-event/{id}")
    EventDTO getEventById(@PathVariable String id);
}
