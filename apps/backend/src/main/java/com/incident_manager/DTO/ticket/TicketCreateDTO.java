package com.incident_manager.DTO.ticket;

import com.incident_manager.entity.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object for ticket creation request.
 *
 * <p>Used to create a new incident ticket. The reporter is determined from the authenticated user.
 *
 * @param title the ticket title
 * @param description the ticket description
 * @param priority the priority level
 * @param workGroupId the work group to assign the ticket to
 * @param assigneeId optional user identifier to assign the ticket to
 */
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
