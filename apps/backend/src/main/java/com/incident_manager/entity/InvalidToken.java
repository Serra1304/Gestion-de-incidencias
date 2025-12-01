package com.incident_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invalid_token")
public class InvalidToken {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String token;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}

