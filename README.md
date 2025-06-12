# 🎟️ Microsservices – Events & Tickets

Este repositório foi criado como desafio técnico durante meu estágio. Ele reúne dois microsserviços desenvolvidos em Java com Spring Boot, que foram deployados na AWS e acessíveis inclusive via celular. Foi uma experiência incrível!

## 📂 Estrutura do Repositório

- `mseventmanager/` – microsserviço responsável pela gestão de **eventos**.
- `msticketmanager/` – microsserviço que gerencia **tickets** associados a esses eventos.

> Cada pasta contém um README específico com detalhes sobre tecnologia, execução local, endpoints, testes e integrações (Feign Client, RabbitMQ, MongoDB etc.).

## ☁️ Deploy na AWS

- Aplicações deployadas em instâncias EC2.
- Configurações de filas (RabbitMQ) e banco de dados (MongoDB) adequadas para produção.
- Acesso remoto via celular, mostrando que a solução está totalmente funcional em ambiente real.

## 🚀 Como Executar Localmente

1. Clone este repositório:
   ```bash
   git clone https://github.com/FcesarBzSilva/microservices-events-tickets.git
   ```
2. Acesse uma das pastas:
   - `cd mseventmanager` ou `cd msticketmanager`
3. Siga o README específico para configurar seu ambiente (MongoDB, RabbitMQ, variáveis de ambiente etc.) e executar os microsserviços.

---

Desenvolvido por **Fernando Cesar Bezerra Silva** com foco em arquitetura de microsserviços, integração via mensageria e deploy em nuvem. Foi um desafio divertido e enriquecedor! 😊
