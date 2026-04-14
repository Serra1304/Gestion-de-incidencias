package com.incident_manager.DTO.workGroup;

import com.incident_manager.DTO.user.UserSummaryDTO;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for complete work group information.
 *
 * <p>Includes work group details along with current members and available users
 * who can be added to the group.
 *
 * @param id work group identifier
 * @param name work group name
 * @param description work group description
 * @param active whether the group is active
 * @param users users currently assigned to the group
 * @param availableUsers users who can be added to the group
 */
public record WorkGroupFullDTO(
        UUID id,
        String name,
        String description,
        Boolean active,
        List<UserSummaryDTO> users,
        List<UserSummaryDTO> availableUsers
) {}
