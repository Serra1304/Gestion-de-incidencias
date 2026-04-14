package com.incident_manager.DTO.workGroup;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for updating work group users.
 *
 * <p>Used to replace the entire list of users assigned to a work group.
 *
 * @param userIds list of user identifiers to be assigned to the group
 */
public record GroupUsersUpdateDTO(
        @NotNull(message = "User IDs list cannot be null")
        List<@NotNull UUID> userIds
) { }
