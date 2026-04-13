package com.incident_manager.DTO.workGroup;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record GroupUsersUpdateDTO(
        @NotNull(message = "User IDs list cannot be null")
        List<@NotNull UUID> userIds
) { }
