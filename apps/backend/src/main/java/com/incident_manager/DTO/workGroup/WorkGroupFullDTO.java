package com.incident_manager.DTO.workGroup;

import com.incident_manager.DTO.user.UserInfoDTO;

import java.util.List;
import java.util.UUID;

public record WorkGroupFullDTO(
        UUID id,
        String name,
        String description,
        Boolean active,
        List<UserInfoDTO> users,
        List<UserInfoDTO>availableUsers
) {}
