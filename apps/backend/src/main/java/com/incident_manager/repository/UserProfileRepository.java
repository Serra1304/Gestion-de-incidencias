package com.incident_manager.repository;

import com.incident_manager.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link UserProfile} entities.
 *
 * <p>Provides database access methods for user profiles.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    /**
     * Finds a user profile by its associated AuthUser identifier.
     *
     * @param userId the AuthUser identifier
     * @return Optional containing the UserProfile if found
     */
    Optional<UserProfile> findByAuthUserId(UUID userId);
}
