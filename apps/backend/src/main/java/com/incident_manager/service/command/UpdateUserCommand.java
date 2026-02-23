package com.incident_manager.service.command;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Command to update an existing user.
 * <p>
 * All fields except {@code userId} are optional.
 * If a field is {@code null}, the corresponding attribute
 * will not be modified.
 */
public record UpdateUserCommand(
        @NotNull UUID userId,
        String name,
        String lastName,
        String secondLastName,
        String address,
        String addressNumber,
        String city,
        String province,
        String postalCode,
        String phone,
        String phoneBusiness,
        String phoneExtension,
        String email,
        Boolean active,
        List<UUID> groups
) {
}
