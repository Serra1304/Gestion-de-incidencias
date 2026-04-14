package com.incident_manager.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JPA event listener for managing audit timestamps.
 *
 * <p>Automatically sets the creation and update timestamps on entities
 * that implement the {@link Auditable} interface.
 *
 * <p>JPA lifecycle callbacks:
 * <ul>
 *   <li>@PrePersist: Sets both createdAt and updatedAt when entity is first persisted</li>
 *   <li>@PreUpdate: Updates the updatedAt timestamp when entity is modified</li>
 * </ul>
 */
@Component
public class TimestampListener {

    private static Clock clock;

    /**
     * Injects the Clock bean for consistent timestamp generation.
     *
     * @param clockBean the Clock bean from Spring context
     */
    @Autowired
    public void setClock(Clock clockBean) {
        TimestampListener.clock = clockBean;
    }

    /**
     * Called before an entity is persisted (inserted).
     * Sets both createdAt and updatedAt to the current time.
     *
     * @param entity the entity being persisted
     */
    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now(Objects.requireNonNull(clock, "Clock not initialized"));
            auditable.setCreatedAt(now);
            auditable.setUpdatedAt(now);
        }
    }

    /**
     * Called before an entity is updated.
     * Updates the updatedAt timestamp to the current time.
     *
     * @param entity the entity being updated
     */
    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Auditable auditable) {
            auditable.setUpdatedAt(LocalDateTime.now(Objects.requireNonNull(clock, "Clock not initialized")));
        }
    }
}
