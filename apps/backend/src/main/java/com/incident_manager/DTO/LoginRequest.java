package com.incident_manager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user login request.
 *
 * <p>Sent to the authentication endpoint with user credentials.
 *
 * @param email the user's email address
 * @param password the user's password (minimum 8 characters)
 */
public record LoginRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password
) {}
