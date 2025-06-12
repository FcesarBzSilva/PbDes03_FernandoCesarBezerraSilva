# Event Manager Microservice

Este é um microserviço desenvolvido em Java com Spring Boot para gerenciamento de eventos. Foi proposto como desafio técnico no estágio da UOL Compass. O serviço permite criar, consultar, atualizar e excluir eventos, além de integrar-se com serviços externos para validação de CEP e verificação de tickets vendidos.

## 🔧 Funcionalidades

- ✅ Criação de eventos com preenchimento automático de endereço via CEP.
- ✅ Recuperação de evento por ID.
- ✅ Listagem de todos os eventos.
- ✅ Listagem de eventos ordenados por nome.
- ✅ Atualização de eventos.
- ✅ Exclusão de eventos (permitida apenas se não houver tickets vendidos).
- ✅ Validações e tratamento de exceções personalizados.

## 🧠 Regras de Negócio

- Eventos devem possuir nome e CEP válidos.
- O endereço é preenchido automaticamente via API [ViaCEP](https://viacep.com.br).
- Eventos com tickets vendidos não podem ser excluídos.
- A atualização de eventos só é permitida caso o evento exista.

## ⚙️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data MongoDB
- Feign Client (comunicação entre microsserviços)
- ViaCEP API (integração externa)
- Jackson (processamento de JSON)
- JUnit e Mockito (testes)

## 📦 Estrutura de Pacotes

```
.
├── clients          # Integrações via Feign com serviços externos
├── config           # Configurações de Feign Client
├── controller       # Camadas REST (API)
├── dto              # Objetos de transferência de dados
├── exceptions       # Exceções customizadas e handler global
├── models           # Modelos de domínio (Event, Address)
├── repository       # Interface de persistência (MongoDB)
├── services         # Lógica de negócios
└── test             # Testes com JUnit e Mockito
```

## 🧪 Testes

O projeto conta com cobertura de testes unitários para a camada de serviço (`EventService`). Os testes cobrem:

- Criação de evento com dados válidos.
- Validação de nome e CEP.
- Atualização de evento.
- Exclusão de evento com/sem tickets vendidos.
- Consulta por ID com validação de existência.

## 🚀 Como Executar

Clone o repositório e execute o projeto localmente com o Maven ou diretamente pela sua IDE:

```bash
git clone https://github.com/FcesarBzSilva/PbDes03_FernandoCesarBezerraSilva.git
cd PbDes03_FernandoCesarBezerraSilva/ms-event-manager
```

Em seguida, execute o projeto com:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080/events`

## 📬 Endpoints principais

| Método | Endpoint                       | Descrição                           |
|--------|--------------------------------|-------------------------------------|
| POST   | `/events/create-event`         | Cria um novo evento                 |
| GET    | `/events/get-event/{id}`       | Retorna evento por ID               |
| GET    | `/events/get-all-events`       | Lista todos os eventos              |
| GET    | `/events/get-all-events/sorted`| Lista todos os eventos ordenados    |
| PUT    | `/events/update-event/{id}`    | Atualiza um evento existente        |
| DELETE | `/events/delete-event/{id}`    | Deleta um evento (sem tickets)      |
