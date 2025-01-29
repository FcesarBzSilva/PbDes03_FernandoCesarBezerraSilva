package org.example.msticketmanager.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.example.msticketmanager.models.Ticket;

@Getter @Setter
@JsonPropertyOrder({"ticketId", "cpf", "customerName", "customerEmail", "event", "amountBRL", "amountUSD", "status"})
public class TicketDTO {
    private String ticketId;
    private String cpf;
    private String customerName;
    private String customerEmail;
    private EventDTO event;
    private String amountBRL;
    private String amountUSD;
    private String status;

    public TicketDTO(EventDTO event, Ticket ticket) {
        this.event = event;
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerEmail = ticket.getCustomerEmail();
        this.amountBRL = ticket.getAmountBRL();
        this.amountUSD = ticket.getAmountUSD();
        this.status = ticket.getStatus();
    }
}
