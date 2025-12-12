package com.incident_manager.DTO;

import java.util.UUID;

public record UserInfoDTO(
        UUID id,
        String name,
        String lastName,
        String secondLastName) {
}
