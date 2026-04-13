package com.incident_manager.DTO.workGroup;

import com.incident_manager.DTO.user.UserSummaryDTO;

import java.util.List;
import java.util.UUID;

public record WorkGroupFullDTO(
        UUID id,
        String name,
        String description,
        Boolean active,
        List<UserSummaryDTO> users,
        List<UserSummaryDTO> availableUsers
) {}
