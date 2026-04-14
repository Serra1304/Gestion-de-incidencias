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
 * Represents a comment on a ticket.
 *
 * <p>Comments allow users to add notes and discussions to tickets.
 * Comments can be marked as internal (visible only to staff) or public (visible to all).
 *
 * <p>Key relationships:
 * <ul>
 *   <li>Many-to-one with {@link Ticket}</li>
 *   <li>Many-to-one with {@link AuthUser} (author)</li>
 * </ul>
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the comment was created</li>
 *   <li>updatedAt: timestamp of the last modification</li>
 * </ul>
 */
@Entity
@EntityListeners(TimestampListener.class)
@Table(name = "ticket_comment")
@Getter
@Setter
@NoArgsConstructor
public class TicketComment implements Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthUser author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    private boolean internal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
