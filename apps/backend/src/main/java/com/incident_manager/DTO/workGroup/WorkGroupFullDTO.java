package com.incident_manager.DTO.workGroup;

import com.incident_manager.DTO.UserInfoDTO;

import java.util.List;
import java.util.UUID;

public record WorkGroupFullDTO(
        UUID id,
        String name,
        String description,
        Boolean active,
        List<UserInfoDTO> userIds,
        List<UserInfoDTO>availableUsers
) {}
