# Event Manager Microservice

Este é um micro serviço Spring Boot para gerenciar eventos.

## Funcionalidades

- Criação de eventos
- Recuperação de eventos por ID
- Listagem de todos os eventos
- Listagem de todos os eventos ordenados por nome
- Exclusão de eventos (somente se não houver tickets vendidos)
- Atualização de eventos

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data MongoDB
- JSON Processing (Jackson)
- Feign Client para comunicação com serviços externos
- ViaCep para obtenção de endereço baseado no CEP

## Como Executar

1. Clone o repositório:
   ```sh
   git clone https://github.com/FcesarBzSilva/PbDes03_FernandoCesarBezerraSilva.git
   cd PbDes03_FernandoCesarBezerraSilva
