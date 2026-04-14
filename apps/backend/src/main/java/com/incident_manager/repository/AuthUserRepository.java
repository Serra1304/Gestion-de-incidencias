package com.incident_manager.repository;

import com.incident_manager.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link AuthUser} entities.
 *
 * <p>Provides database access methods for authentication users.
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    /**
     * Finds a user by email address.
     *
     * @param email the user's email address
     * @return Optional containing the AuthUser if found
     */
    Optional<AuthUser> findByEmail(String email);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email address to check
     * @return true if a user with this email exists, false otherwise
     */
    boolean existsByEmail(String email);
}

