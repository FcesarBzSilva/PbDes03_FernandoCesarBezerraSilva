# Ticket Manager Microservice

Este é um microsserviço desenvolvido com Spring Boot responsável pelo gerenciamento de **tickets** associados a eventos. Ele faz parte de um sistema composto por múltiplos microsserviços, sendo este o responsável por criar, listar, atualizar e cancelar tickets.

## ✨ Funcionalidades

- ✅ Criação de tickets (com validação do evento via outro microsserviço)
- 🔍 Recuperação de ticket por ID
- 🔄 Atualização de informações do ticket
- ❌ Cancelamento de ticket (soft delete)
- 📋 Listagem de tickets por ID de evento

## 🧱 Estrutura de Pacotes

```bash
└── java/org/example/msticketmanager
    ├── clients              # Feign client para consumir o microsserviço de eventos
    ├── config               # Configurações do RabbitMQ
    ├── controller           # API REST para gerenciamento de tickets
    ├── dto                  # Objetos de transferência de dados
    ├── exceptions           # Exceções customizadas e handler global
    ├── infra/mqueue         # Publicação de mensagens no RabbitMQ
    ├── models               # Representações das entidades e objetos de fila
    ├── repository           # Interface com MongoDB
    ├── services             # Lógica de negócio
    └── resources            # Configurações da aplicação
```

## ⚙️ Tecnologias e Bibliotecas

- **Java 17**
- **Spring Boot**
- **Spring Data MongoDB**
- **RabbitMQ** (mensageria)
- **Feign Client** (para comunicação com o microsserviço de eventos)
- **Jackson** (processamento JSON)
- **JUnit + Mockito** (testes automatizados)

## 🚀 Como Executar Localmente

1. Clone o repositório e entre na pasta:
```bash
git clone https://github.com/FcesarBzSilva/PbDes03_FernandoCesarBezerraSilva.git
cd PbDes03_FernandoCesarBezerraSilva/msticketmanager
```

2. Verifique se os serviços dependentes (como o microsserviço de eventos e o RabbitMQ) estão em execução.

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

4. A aplicação será iniciada na porta configurada (por padrão, `8081`).

## 🔁 Comunicação Entre Microsserviços

- O `TicketManager` utiliza um **Feign Client** para buscar informações de eventos no `EventManager`, garantindo que o ticket só seja criado se o evento existir.
- Ao criar um ticket, uma **mensagem é publicada no RabbitMQ** com os dados do cliente.

## 📡 Endpoints da API

| Método | Caminho                         | Descrição                           |
|--------|----------------------------------|-------------------------------------|
| POST   | `/tickets/create-ticket`        | Cria um ticket (verifica o evento) |
| GET    | `/tickets/get-ticket/{id}`      | Busca ticket por ID                 |
| PUT    | `/tickets/update-ticket/{id}`   | Atualiza um ticket existente        |
| DELETE | `/tickets/cancel-ticket/{id}`   | Cancela um ticket (soft delete)     |
| GET    | `/tickets/check-tickets-by-event/{eventId}` | Lista tickets por evento       |

## 🧪 Testes Automatizados

Os testes estão localizados em:

```bash
test/java/org/example/msticketmanager/services
```

Cobrem os principais cenários:

- ✅ Criação de ticket com evento válido
- ❌ Tentativa de criação sem evento
- 🔁 Atualização de ticket
- ❌ Cancelamento (soft delete)
- 🔍 Busca de ticket existente e inexistente

Executar testes:

```bash
./mvnw test
```

## 📂 Configurações

Edite `src/main/resources/application.properties` para alterar configurações como porta, URI do MongoDB, filas RabbitMQ etc.

---

Desenvolvido por **Fernando Cesar Bezerra Silva** como parte de um desafio técnico com foco em arquitetura de microsserviços.