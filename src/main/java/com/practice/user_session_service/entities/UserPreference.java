package com.practice.user_session_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
/*
*
* CREATE TABLE IF NOT EXISTS user_preferences_read (
  user_id UUID PRIMARY KEY,
  preferences JSONB DEFAULT '{}',
  last_updated TIMESTAMPTZ DEFAULT now()
);
*
* */
@Entity
@Table(name = "user_preference")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder

public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID user_id;
    @Column(name = "preferences")
    private String preferences;

    @Column(name = "last_updated")
    private LocalDateTime last_updated;
}
