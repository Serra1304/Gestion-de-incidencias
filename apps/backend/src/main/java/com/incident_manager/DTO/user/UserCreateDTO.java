package com.incident_manager.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

@Schema(
        name = "UserCreateRequest",
        description = "Request payload to create a new user in the system"
)
public record UserCreateDTO(

        @NotBlank
        @Size(max = 50)
        @Schema(
                description = "User first name",
                example = "Juan"
        )
        String name,

        @NotBlank
        @Size(max = 50)
        @Schema(
                description = "User last name",
                example = "González"
        )
        String lastname,

        @Size(max = 50)
        @Schema(
                description = "User second last name (optional)",
                example = "Torres"
        )
        String secondLastname,

        @NotBlank
        @Size(max = 100)
        @Schema(
                description = "Street address",
                example = "Calle Mayor"
        )
        String address,

        @NotBlank
        @Size(max = 10)
        @Schema(
                description = "Street number",
                example = "12B"
        )
        String addressNumber,

        @NotBlank
        @Size(max = 50)
        @Schema(
                description = "City of residence",
                example = "Madrid"
        )
        String city,

        @NotBlank
        @Size(max = 50)
        @Schema(
                description = "Province or region",
                example = "Madrid"
        )
        String province,

        @NotBlank
        @Pattern(regexp = "^[0-9]{5}$")
        @Schema(
                description = "Postal code (5 digits)",
                example = "28001"
        )
        String postalCode,

        @NotBlank
        @Pattern(regexp = "^[0-9]{9}$")
        @Schema(
                description = "Personal contact phone number (9 digits)",
                example = "612345678"
        )
        String phone,

        @Pattern(regexp = "^[0-9]{9}$")
        @Schema(
                description = "Business phone number (optional)",
                example = "912345678"
        )
        String phoneBusiness,

        @Size(max = 10)
        @Schema(
                description = "Business phone extension",
                example = "7233"
        )
        String phoneExtension,

        @NotBlank
        @Email
        @Size(max = 100)
        @Schema(
                description = "User email address (must be unique)",
                example = "juan.gonzalez@email.com"
        )
        String email,

        @NotBlank
        @Size(min = 8, max = 64)
        @Schema(
                description = "User password (minimum 8 characters)",
                example = "P@ssw0rd123"
        )
        String password,

        @Schema(
                description = "Indicates whether the user is active. Defaults to true",
                example = "true"
        )
        Boolean active,

        @Schema(
                description = "List of group identifiers the user belongs to",
                example = "[\"c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21\"]"
        )
        List<@NotNull UUID> groups
) {
}
