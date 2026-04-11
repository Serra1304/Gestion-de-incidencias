package com.incident_manager.DTO.ticket;

import com.incident_manager.entity.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TicketCreateDTO(
        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        TicketPriority priority,

        @NotNull
        UUID workGroupId,

        UUID assigneeId
) {
}
