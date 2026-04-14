package com.incident_manager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user authentication information.
 *
 * <p>Contains email and security role information for authenticated users.
 *
 * @param email the user's email address
 * @param securityRole the user's security role
 */
public record UserAuthDTO(
    @NotBlank
    @Email
    String email,

    String securityRole
) {}
