package com.incident_manager.common;

import java.time.LocalDateTime;

/**
 * Interface for auditable entities.
 *
 * <p>Entities implementing this interface track when they were created and last updated.
 * Audit timestamps are automatically managed by JPA event listeners.
 */
public interface Auditable {

    /**
     * Sets the creation timestamp.
     *
     * @param time the creation timestamp
     */
    void setCreatedAt(LocalDateTime time);

    /**
     * Sets the last update timestamp.
     *
     * @param time the update timestamp
     */
    void setUpdatedAt(LocalDateTime time);
}
