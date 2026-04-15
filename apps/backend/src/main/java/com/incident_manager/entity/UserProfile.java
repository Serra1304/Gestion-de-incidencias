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
 * Represents the profile/personal information of a user.
 *
 * <p>This entity stores non-sensitive user data and maintains a one-to-one relationship
 * with {@link AuthUser} which handles authentication and authorization.
 *
 * <p>Key relationships:
 * <ul>
 *   <li>One-to-one with {@link AuthUser}</li>
 * </ul>
 *
 * <p>Audit information:
 * <ul>
 *   <li>createdAt: timestamp when the profile was created</li>
 *   <li>updatedAt: timestamp of the last modification</li>
 * </ul>
 */
@Entity
@EntityListeners(TimestampListener.class)
@Getter @Setter
@NoArgsConstructor
@Table(name = "user_profile")
public class UserProfile implements Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser authUser;

    private String name;
    private String lastName;
    private String secondLastName;
    private String address;
    private String addressNumber;
    private String city;
    private String province;
    private String postalCode;
    private String phone;
    private String phoneBusiness;
    private String phoneExtension;

    @Column(name = "email_notifications_enabled", nullable = false)
    private Boolean emailNotificationsEnabled = true;

    @Column(name = "in_app_notifications_enabled", nullable = false)
    private Boolean inAppNotificationsEnabled = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
