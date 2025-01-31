# Ticket Manager Microservice

Este é um micro serviço Spring Boot para gerenciar tickets de eventos.

## Funcionalidades

- Criação de tickets (somente se um evento estiver associado)
- Recuperação de tickets por ID
- Atualização de tickets
- Exclusão lógica de tickets (soft delete)
- Listagem de tickets por ID de evento

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data MongoDB
- JSON Processing (Jackson)
- Mensageria (RabbitMQ)
- Feign Client para comunicação com serviços externos

## Como Executar

1. Clone o repositório:
   ```sh
   git clone https://github.com/FcesarBzSilva/PbDes03_FernandoCesarBezerraSilva.git
   cd PbDes03_FernandoCesarBezerraSilva
