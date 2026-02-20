package com.incident_manager.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(
        name = "UserSummary",
        description = "Basic user information returned in user listings"
)
public record UserSummaryDTO(

        @Schema(description = "User unique identifier", example = "c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21")
        UUID id,

        @Schema(description = "User first name", example = "Juan")
        String name,

        @Schema(description = "User last name", example = "González")
        String lastName,

        @Schema(description = "User second last name", example = "Torres")
        String secondLastName) {
}
