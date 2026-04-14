package com.incident_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an invalidated JWT token.
 *
 * <p>This entity is used to maintain a blacklist of tokens that have been invalidated
 * during logout operations. When a user logs out, their token is added to this blacklist
 * to prevent its further use.
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the token was invalidated</li>
 * </ul>
 */
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

