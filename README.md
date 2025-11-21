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

## Frontend

The React frontend is located in `event-ticket/frontend/my-react-app/`.

### Frontend Setup

1. **Navigate to frontend directory**
```bash
cd event-ticket/frontend/my-react-app
```

2. **Install dependencies**
```bash
npm install
```

3. **Start development server**
```bash
npm run dev
```

The frontend will be available at http://localhost:5173

### Frontend Features

- **Attendee Landing Page** (`/`) - Browse and search published events
- **Organizers Landing Page** (`/organizers`) - Information for event organizers
- **Dashboard** (`/dashboard/events`) - Manage your events (requires authentication)
- **Event Creation/Editing** - Create and update events with ticket types
- **Ticket Purchase** - Purchase tickets for published events
- **OIDC Authentication** - Integrated with Keycloak for secure login

### Frontend Tech Stack

- React 19
- TypeScript
- Vite
- React Router
- React OIDC Context (Keycloak integration)
- Tailwind CSS
- Radix UI components
- Lucide React icons

### Frontend Configuration

The frontend is configured to proxy API requests to the backend:
- API proxy: `/api` → `http://localhost:8080`
- OIDC Authority: `http://localhost:9090/realms/event-ticket-platform`
- Client ID: `event-ticket-platform-app`

## Tech Stack

**Backend:**
- Spring Boot 3.5.7
- Spring Security + OAuth2 Resource Server
- Spring Data JPA
- PostgreSQL
- Keycloak
- Lombok
- MapStruct 1.6.3

**Frontend:**
- React 19 + TypeScript
- Vite
- React Router
- Tailwind CSS
- Radix UI
