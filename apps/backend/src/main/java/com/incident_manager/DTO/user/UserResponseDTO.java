package com.incident_manager.DTO.user;

import com.incident_manager.DTO.workGroup.WorkGroupInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(
        name = "UserResponse",
        description = "User data returned by the API"
)
public record UserResponseDTO(

        @Schema(description = "Unique user identifier", example = "b3b6f7c4-5a94-4c9e-9c9e-7a3c5f1e8a12")
        UUID id,

        @Schema(description = "User first name", example = "Juan")
        String name,

        @Schema(description = "User last name", example = "González")
        String lastName,

        @Schema(description = "User second last name", example = "Torres", nullable = true)
        String secondLastName,

        @Schema(description = "User email address", example = "juan.gonzalez@example.com")
        String email,

        @Schema(description = "Indicates whether the user is active")
        boolean active,

        @Schema(description = "Work groups the user belongs to")
        List<WorkGroupInfoDTO> groups,

        @Schema(description = "Street address", example = "Gran Vía")
        String address,

        @Schema(description = "Address number", example = "25")
        String addressNumber,

        @Schema(description = "City", example = "Madrid")
        String city,

        @Schema(description = "Province", example = "Madrid")
        String province,

        @Schema(description = "Postal code", example = "28013")
        String postalCode,

        @Schema(description = "Personal phone number", example = "+34 600 123 456")
        String phone,

        @Schema(description = "Business phone number", example = "+34 910 123 456")
        String phoneBusiness,

        @Schema(description = "Phone extension", example = "1234")
        String phoneExtension
) {
}
