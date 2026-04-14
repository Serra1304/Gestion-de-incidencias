package com.incident_manager.repository;

import com.incident_manager.entity.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for managing {@link WorkGroup} entities.
 *
 * <p>Provides database access methods for work groups.
 */
public interface WorkGroupRepository extends JpaRepository<WorkGroup, UUID> {
    /**
     * Checks if a work group with the given name exists.
     *
     * @param name the group name to check (case-sensitive)
     * @return true if a group with this name exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Checks if a work group with the given name exists (case-insensitive).
     *
     * @param name the group name to check
     * @return true if a group with this name exists, false otherwise
     */
    boolean existsByNameIgnoreCase(String name);
}
