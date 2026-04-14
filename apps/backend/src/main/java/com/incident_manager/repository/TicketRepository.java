package com.incident_manager.repository;

import com.incident_manager.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for managing {@link Ticket} entities.
 *
 * <p>Provides database access methods for incident tickets.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
