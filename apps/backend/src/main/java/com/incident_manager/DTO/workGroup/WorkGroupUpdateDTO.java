package com.incident_manager.DTO.workGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record WorkGroupUpdateDTO(

        @Schema(
                description = "Name of the work group",
                example = "Network Operations Team",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Group name is mandatory")
        @Size(max = 100, message = "Group name must not exceed 100 characters")
        String name,

        @Schema(
                description = "Detailed description of the work group responsibilities",
                example = "Responsible for monitoring and resolving network-related incidents"
        )
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @Schema(
                description = "Indicates whether the group is active",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Active flag must be specified")
        Boolean active,

        @Schema(
                description = "List of user IDs that belong to the group",
                example = "[\"c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21\"]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "User list must be provided")
        List<UUID> userIds
) {}
