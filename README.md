# Event Ticket Platform

Event ticketing system with Keycloak authentication, built with Spring Boot 3.5.7 and Java 25.

## Prerequisites

- Java 25
- Maven 3.9+
- Docker & Docker Compose
- PostgreSQL 

## Quick Start

1. **Start infrastructure**
```bash
docker-compose up -d
```

This starts:
- PostgreSQL (port 5432)
- Keycloak (port 9090)
- Adminer (port 8888)

2. **Configure Keycloak**
- Access: http://localhost:9090
- Login: admin/admin
- Create realm: `event-ticket-platform`
- Create client for the application
- Configure JWT issuer

3. **Run application**
```bash
./mvnw spring-boot:run
```

## Architecture

**Entities:**
- User
- Event
- TicketType
- Ticket
- QrCode
- TicketValidation

**Key Features:**
- OAuth2 JWT authentication via Keycloak
- Automatic user provisioning on first login
- Event management with ticket types
- QR code generation for tickets
- Ticket validation tracking

## Configuration

Edit `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: username
    password: password
  
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9090/realms/event-ticket-platform
```

## API Authentication

All endpoints require JWT bearer token from Keycloak:

```bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/...
```

## Tech Stack

- Spring Boot 3.5.7
- Spring Security + OAuth2 Resource Server
- Spring Data JPA
- PostgreSQL
- Keycloak
- Lombok
- MapStruct 1.6.3
