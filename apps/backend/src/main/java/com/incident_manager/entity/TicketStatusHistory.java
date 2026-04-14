package com.incident_manager.entity;

import com.incident_manager.common.Auditable;
import com.incident_manager.common.TimestampListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a record of ticket status changes.
 *
 * <p>Maintains an audit trail of all status transitions for a ticket,
 * including who made the change and when it was made.
 *
 * <p>Key relationships:
 * <ul>
 *   <li>Many-to-one with {@link Ticket}</li>
 *   <li>Many-to-one with {@link AuthUser} (who made the change)</li>
 * </ul>
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the status change occurred</li>
 *   <li>updatedAt: timestamp of the last modification to this record</li>
 * </ul>
 */
@Entity
@EntityListeners(TimestampListener.class)
@Table(name = "ticket_status_history")
@Getter
@Setter
@NoArgsConstructor
public class TicketStatusHistory implements Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private TicketStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private TicketStatus toStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUser changedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
