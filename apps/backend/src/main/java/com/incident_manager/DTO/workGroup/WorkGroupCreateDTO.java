package com.incident_manager.DTO.workGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for work group creation request.
 */
@Schema(
        name = "WorkGroupCreateRequest",
        description = "Request payload to create a new work group"
)
public record WorkGroupCreateDTO(

        @NotBlank
        @Size(max = 50)
        @Schema(
                description = "Unique name of the work group",
                example = "IT Support"
        )
        String name,

        @Size(max = 255)
        @Schema(
                description = "Optional description of the work group",
                example = "Support and maintenance team"
        )
        String description,

        @Schema(
                description = "Indicates whether the group is active. Defaults to true",
                example = "true"
        )
        Boolean active
) { }
