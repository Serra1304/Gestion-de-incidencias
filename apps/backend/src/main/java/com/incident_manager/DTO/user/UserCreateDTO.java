package com.incident_manager.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UserCreateDTO(
        @NotNull
        String name,

        @NotNull
        String lastname,

        String secondLastname,

        @NotNull
        String address,

        @NotNull
        String addressNumber,

        @NotNull
        String city,

        @NotNull
        String province,

        @NotNull
        String postalCode,

        @NotNull
        String phone,

        String phoneBusiness,

        String phoneExtension,

        @NotNull
        @Email
        String email,

        String password,

        Boolean active,

        List<UUID> groups
) {
}
