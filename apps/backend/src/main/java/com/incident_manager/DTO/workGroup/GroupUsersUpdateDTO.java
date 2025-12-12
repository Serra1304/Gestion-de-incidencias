package com.incident_manager.DTO.workGroup;

import java.util.List;
import java.util.UUID;

public record GroupUsersUpdateDTO(
        List<UUID> userIds
) { }
