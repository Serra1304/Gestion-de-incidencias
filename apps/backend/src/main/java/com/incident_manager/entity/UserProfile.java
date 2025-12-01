package com.incident_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
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

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}


