package com.incident_manager.service.command;

import java.util.List;
import java.util.UUID;

public record UpdateUserCommand(
        UUID userId,
        String name,
        String lastname,
        String secondLastname,
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
