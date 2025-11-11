# user-session-service


A stateless Java 17 microservice that manages user authentication sessions and user preferences. It uses CQRS: a write-side handles commands (login, logout, update-preferences) and emits events to an append-only event store; a read-side maintains fast materialized views (for queries) that are asynchronously updated. Authentication uses short-lived JWT access tokens + refresh tokens stored/validated server-side (Redis) for revocation. Preferences updates are validated by a strict schema and sanitized. The service is designed to run locally (Docker Compose) and scale horizontally (stateless services, Kafka/event bus, Redis cache, PostgreSQL event store) in production.
