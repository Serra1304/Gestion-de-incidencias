package com.incident_manager.service.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;


/**
 * Command that represents the intent to create a new user.
 * <p>
 * This object contains only structural validation rules.
 * Business validations (e.g. email uniqueness, group existence)
 * are handled at the service layer.
 */
public record CreateUserCommand(

        @NotBlank
        String name,

        @NotBlank
        String lastname,

        String secondLastname,

        @NotBlank
        String address,

        @NotBlank
        String addressNumber,

        @NotBlank
        String city,

        @NotBlank
        String province,

        @NotBlank
        String postalCode,

        @NotBlank
        String phone,

        String phoneBusiness,

        String phoneExtension,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8)
        String password,

        Boolean active,

        List<UUID> groups
) {
}
