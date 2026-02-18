package com.incident_manager.DTO.user;

import com.incident_manager.DTO.workGroup.WorkGroupInfoDTO;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String lastName,
        String secondLastName,
        String email,
        boolean active,
        List<WorkGroupInfoDTO> groups,
        String address,
        String addressNumber,
        String city,
        String province,
        String postalCode,
        String phone,
        String phoneBusiness,
        String phoneExtension
) {
}
