package com.incident_manager.DTO.workGroup;

import java.util.UUID;

/**
 * Data Transfer Object for work group information.
 *
 * <p>Minimal work group data (id and name) used when embedding work group information
 * in other DTOs like tickets or users.
 */
public record WorkGroupInfoDTO(
        UUID id,
        String name
) {
}
