package com.incident_manager.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Schema(
        name = "UserUpdateRequest",
        description = "Request payload to update an existing user. Only provided fields will be updated."
)
public record UserUpdateDTO(

        @Size(max = 50)
        @Schema(description = "User first name", example = "Juan")
        String name,

        @Size(max = 50)
        @Schema(description = "User last name", example = "González")
        String lastName,

        @Size(max = 50)
        @Schema(description = "User second last name", example = "Torres")
        String secondLastName,

        @Size(max = 100)
        @Schema(description = "Street address", example = "Calle Mayor")
        String address,

        @Size(max = 10)
        @Schema(description = "Street number", example = "12B")
        String addressNumber,

        @Size(max = 50)
        @Schema(description = "City of residence", example = "Madrid")
        String city,

        @Size(max = 50)
        @Schema(description = "Province or region", example = "Madrid")
        String province,

        @Pattern(regexp = "^[0-9]{5}$")
        @Schema(description = "Postal code", example = "28001")
        String postalCode,

        @Pattern(regexp = "^[0-9]{9}$")
        @Schema(description = "Personal phone number", example = "612345678")
        String phone,

        @Pattern(regexp = "^[0-9]{9}$")
        @Schema(description = "Business phone number", example = "912345678")
        String phoneBusiness,

        @Size(max = 10)
        @Schema(description = "Business phone extension", example = "7233")
        String phoneExtension,

        @Email
        @Size(max = 100)
        @Schema(description = "User email address", example = "juan.gonzalez@email.com")
        String email,

        @Schema(description = "Indicates whether the user is active", example = "true")
        Boolean active,

        @Schema(
                description = "List of group identifiers the user belongs to",
                example = "[\"c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21\"]"
        )
        List<UUID> groups
) {
}

