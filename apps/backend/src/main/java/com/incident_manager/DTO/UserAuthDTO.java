package com.incident_manager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthDTO(
    @NotBlank
    @Email
    String email,

    String securityRole
) {}
