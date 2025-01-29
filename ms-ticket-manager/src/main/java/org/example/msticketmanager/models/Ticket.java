package org.example.msticketmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "ticket")
public class Ticket {
    @Id
    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerEmail;
    private String eventId;
    private String amountBRL;
    private String amountUSD;
    private String status;
}
