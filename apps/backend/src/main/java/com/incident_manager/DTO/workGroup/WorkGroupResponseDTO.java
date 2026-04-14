package com.incident_manager.DTO.workGroup;

import java.util.UUID;

/**
 * Data Transfer Object for work group response.
 *
 * <p>Contains basic work group information returned by API endpoints.
 */
public record WorkGroupResponseDTO(
        UUID id,
        String name,
        String description,
        Boolean active
) {}
