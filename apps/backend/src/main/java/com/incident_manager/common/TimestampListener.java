package com.incident_manager.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class TimestampListener {

    private static Clock clock;

    @Autowired
    public void setClock(Clock clockBean) {
        TimestampListener.clock = clockBean;
    }

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now(Objects.requireNonNull(clock, "Clock not initialized"));
            auditable.setCreatedAt(now);
            auditable.setUpdatedAt(now);
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Auditable auditable) {
            auditable.setUpdatedAt(LocalDateTime.now(Objects.requireNonNull(clock, "Clock not initialized")));
        }
    }
}
