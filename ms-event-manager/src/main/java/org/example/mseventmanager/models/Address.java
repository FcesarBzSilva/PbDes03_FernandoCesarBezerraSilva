package org.example.mseventmanager.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Address {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
