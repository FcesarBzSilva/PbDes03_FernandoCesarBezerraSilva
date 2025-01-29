package org.example.msticketmanager.models;

import lombok.Data;

@Data
public class TicketDataMq {
    private String customerName;
    private String customerEmail;
    private String cpf;

    public TicketDataMq(String customerName, String customerEmail, String cpf) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.cpf = cpf;
    }
}
