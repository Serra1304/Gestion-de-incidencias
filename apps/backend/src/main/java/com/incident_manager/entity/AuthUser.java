package com.incident_manager.entity;

import com.incident_manager.common.Auditable;
import com.incident_manager.common.TimestampListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a user in the authentication and authorization system.
 *
 * <p>This entity stores authentication credentials and security information.
 * User profile details are stored separately in {@link UserProfile}.
 *
 * <p>Key relationships:
 * <ul>
 *   <li>One-to-one with {@link UserProfile}</li>
 *   <li>Many-to-many with {@link WorkGroup}</li>
 * </ul>
 *
 * <p>Email must be unique in the system and is used for login.
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the user was created</li>
 *   <li>updatedAt: timestamp of the last modification</li>
 * </ul>
 */
@Entity
@EntityListeners(TimestampListener.class)
@Getter @Setter
@NoArgsConstructor
@Table(name = "auth_user")
public class AuthUser implements Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "security_role")
    private String securityRole;

    private boolean enabled = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "authUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile profile;

    @ManyToMany
    @JoinTable(
            name = "work_group_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<WorkGroup> groups = new HashSet<>();
}


