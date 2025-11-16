package com.practice.user_session_service.entities;


/*
*
* CREATE TABLE IF NOT EXISTS event_store (
  id BIGSERIAL PRIMARY KEY,
  aggregate_id UUID NOT NULL,
  aggregate_type TEXT NOT NULL,
  event_type TEXT NOT NULL,
  payload JSONB NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now(),
  version INT NOT NULL
);
*
* */


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_store")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EventStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long user_id;
    @Column(name = "aggregate_id")
    private UUID aggregate_id;
    @Column(name = "aggregate_type")
    private String aggregate_type;
    @Column(name = "event_type")
    private String event_type;

    @Column(name = "payload")
    private String payload;

    @Column(name = "version")
    private int version;
    @Column(name = "created_at")
    private LocalDateTime created_at;
}
