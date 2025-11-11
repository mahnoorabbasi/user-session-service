# user-session-service


A stateless Java 17 microservice that manages user authentication sessions and user preferences. It uses CQRS: a write-side handles commands (login, logout, update-preferences) and emits events to an append-only event store; a read-side maintains fast materialized views (for queries) that are asynchronously updated. Authentication uses short-lived JWT access tokens + refresh tokens stored/validated server-side (Redis) for revocation. Preferences updates are validated by a strict schema and sanitized. The service is designed to run locally (Docker Compose) and scale horizontally (stateless services, Kafka/event bus, Redis cache, PostgreSQL event store) in production.


```mermaid
flowchart TD

subgraph Client["Client / Frontend"]
    UI["Web / Mobile App"]
end

subgraph Service["User Session & Preference Microservice (Java 17, Spring Boot)"]
    direction TB

    Controller["REST Controller (Login, Logout, Update Preferences)"]
    Security["JWT Security Layer"]
    
    subgraph CQRS["CQRS Layer"]
        CommandService["Command Service (Handle Login, Logout, Preference Updates)"]
        QueryService["Query Service (Fetch Session, Preferences)"]
    end
    
    Validator["Input Validator (Preference Validation)"]
    EventBus["Event Publisher (Kafka / Async Queue)"]
end

subgraph ReadDB["Read Database (Postgres - Query Model)"]
    Projection["Projections / Materialized Views"]
end

subgraph WriteDB["Write Database (Postgres - Command Model)"]
    UserTable["User Table"]
    SessionTable["Session Table"]
    PreferenceTable["Preference Table"]
end

UI -->|REST API| Controller
Controller --> Security
Security --> CommandService
Controller --> QueryService
CommandService --> Validator
CommandService --> WriteDB
CommandService --> EventBus
EventBus --> Projection
Projection --> ReadDB
QueryService --> ReadDB
Security --> WriteDB

subgraph Infra["Supporting Components"]
    Kafka["Kafka (for CQRS Event Propagation)"]
    Redis["Redis (Optional: Session Cache / Token Blacklist)"]
end

EventBus --> Kafka
Security --> Redis





#Preference Service Diagram
```mermaid
flowchart TD
    A[Client / Frontend] -->|HTTP Request| B[PreferenceController]

    B -->|Send Command / Get Query| C[PreferenceCommandService - CircuitBreaker, Retry, Fallback]
    B -->|Get Query| D[PreferenceQueryService - CircuitBreaker, Retry, Fallback]

    C -->|Publish Event| E[EventPublisher - Kafka Producer - CircuitBreaker, Retry, Fallback]
    E -->|Async Event| F[Kafka Topic: user-preferences-events]
    F -->|Consume Event| G[EventConsumer - Projector - CircuitBreaker, Retry, Fallback]

    G -->|Update Read DB| H[Postgres: user_preferences_read]
    G -->|Update Cache| I[Redis: user:preferences]

    D -->|Read Cache| I
    D -->|Fallback Read| H

    %% Highlight resilience-enabled services
    classDef resilience fill:#ffeb99,stroke:#333,stroke-width:1px;
    class C,D,E,G resilience;



# User sesssion service diagram
```mermaid
flowchart TD
    A[Client / Frontend] -->|HTTP POST /login| B[LoginController]

    B -->|Authenticate User| C[UserSessionService - CircuitBreaker, Retry, Fallback]
    C -->|Validate Credentials| D[Postgres: users table]
    C -->|Generate JWT Token| E[Redis: session cache]

    B -->|Logout| F[LogoutService - CircuitBreaker, Retry, Fallback]
    F -->|Invalidate JWT Token| E[Redis: session cache]

    %% Highlight resilience-enabled services
    classDef resilience fill:#ffeb99,stroke:#333,stroke-width:1px;
    class C,F resilience;


