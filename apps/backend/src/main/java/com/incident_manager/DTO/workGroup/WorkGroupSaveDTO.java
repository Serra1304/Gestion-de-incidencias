package com.incident_manager.DTO.workGroup;

import java.util.List;
import java.util.UUID;

public record WorkGroupSaveDTO(
        String name,
        String description,
        Boolean active,
        List<UUID> userIds
) {}
