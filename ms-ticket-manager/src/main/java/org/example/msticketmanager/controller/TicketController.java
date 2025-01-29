package org.example.msticketmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.msticketmanager.dto.TicketDTO;
import org.example.msticketmanager.models.Ticket;
import org.example.msticketmanager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody Ticket ticket) throws JsonProcessingException {
        TicketDTO savedTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<Ticket> getEventById(@PathVariable String id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    
}
