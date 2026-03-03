package com.incident_manager.entity;

import com.incident_manager.common.Auditable;
import com.incident_manager.common.TimestampListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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

