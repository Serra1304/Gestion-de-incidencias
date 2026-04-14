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
 * Represents an incident ticket in the system.
 *
 * <p>Tickets are the core entity for tracking incidents and issues.
 * Each ticket tracks its reporter, assignee, status, and priority.
 *
 * <p>Key relationships:
 * <ul>
 *   <li>Many-to-one with {@link AuthUser} (reporter)</li>
 *   <li>Many-to-one with {@link AuthUser} (assignee)</li>
 *   <li>Many-to-one with {@link WorkGroup}</li>
 * </ul>
 *
 * <p>Ticket lifecycle:
 * <ul>
 *   <li>Created with OPEN status</li>
 *   <li>Can be assigned to a user within the same work group</li>
 *   <li>Status can change through OPEN -> IN_PROGRESS -> CLOSED</li>
 *   <li>resolvedAt tracks when the ticket was marked as resolved</li>
 * </ul>
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the ticket was created</li>
 *   <li>updatedAt: timestamp of the last modification</li>
 * </ul>
 */
@Entity
@EntityListeners(TimestampListener.class)
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
public class Ticket implements Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private AuthUser reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private AuthUser assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private WorkGroup workGroup;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
}
