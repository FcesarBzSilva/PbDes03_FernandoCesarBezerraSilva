package org.example.mseventmanager.clients;

import org.example.mseventmanager.models.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ViaCepClient {
    @GetMapping("{cep}/json")
    Address getEndereco(@PathVariable String cep);
}
