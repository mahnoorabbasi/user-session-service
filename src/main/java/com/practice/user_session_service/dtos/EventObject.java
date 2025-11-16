package com.practice.user_session_service.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EventObject implements Serializable {

    // Unique ID for this message instance (useful for tracing)
    private UUID messageId;

    // Identifier for the aggregate (e.g., userId, sessionId, etc.)
    private UUID aggregateId;

    // Logical grouping of entities (e.g., "UserPreference", "UserSession")
    private String aggregateType;

    // Type of message: COMMAND or EVENT
    private MessageType messageType;

    // The specific action (e.g., UPDATE_PREFERENCE, LOGIN_SUCCESS)
    private String action;

    // The serialized business payload (JSON string)
    private Object payload;

    // Versioning for concurrency or replay control
    private int version;

    // Timestamp for audit and replay
    private Instant timestamp;

    // Optional metadata (can include correlationId, source, etc.)
    private String source;
    private String correlationId;

    public static EventObject createCommand(UUID aggregateId, String aggregateType, String action, Object payload) {
        return EventObject.builder()
                .messageId(UUID.randomUUID())
                .aggregateId(aggregateId)
                .aggregateType(aggregateType)
                .messageType(MessageType.COMMAND)
                .action(action)
                .payload(payload)
                .version(1)
                .timestamp(Instant.now())
                .build();
    }

    public static EventObject createEvent(UUID aggregateId, String aggregateType, String action, Object payload) {
        return EventObject.builder()
                .messageId(UUID.randomUUID())
                .aggregateId(aggregateId)
                .aggregateType(aggregateType)
                .messageType(MessageType.EVENT)
                .action(action)
                .payload(payload)
                .version(1)
                .timestamp(Instant.now())
                .build();
    }

    public enum MessageType {
        COMMAND,
        EVENT
    }
}
