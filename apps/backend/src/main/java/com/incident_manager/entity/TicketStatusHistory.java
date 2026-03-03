package com.incident_manager.entity;

import com.incident_manager.common.Auditable;
import com.incident_manager.common.TimestampListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
