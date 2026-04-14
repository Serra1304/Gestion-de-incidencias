package com.incident_manager.entity;

import com.incident_manager.common.Auditable;
import com.incident_manager.common.TimestampListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a work group in the system.
 *
 * <p>Work groups are organizational units that group users together.
 * They are used to organize ticket assignments and manage user access.
 *
 * <p>Key relationships:
 * <ul>
 *   <li>Many-to-many with {@link AuthUser}</li>
 * </ul>
 *
 * <p>Business rules:
 * <ul>
 *   <li>Group name must be unique</li>
 *   <li>Group can be activated or deactivated</li>
 * </ul>
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the group was created</li>
 *   <li>updatedAt: timestamp of the last modification</li>
 * </ul>
 */
@Entity
@EntityListeners(TimestampListener.class)
@Table(name = "work_group")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkGroup implements Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    private Boolean active = true;

    @ManyToMany(mappedBy = "groups")
    private Set<AuthUser> users;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

