package com.incident_manager.DTO.workGroup;

import java.util.UUID;

public record WorkGroupResponseDTO(
        UUID id,
        String name,
        String description,
        Boolean active
) {}
