package com.incident_manager.repository;

import com.incident_manager.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for managing {@link InvalidToken} entities.
 *
 * <p>Provides database access methods for invalidated tokens.
 * Used for token blacklisting during logout operations.
 */
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, UUID> {
    /**
     * Checks if a token is in the invalidation list.
     *
     * @param token the JWT token to check
     * @return true if the token is invalidated, false otherwise
     */
    boolean existsByToken(String token);
}

