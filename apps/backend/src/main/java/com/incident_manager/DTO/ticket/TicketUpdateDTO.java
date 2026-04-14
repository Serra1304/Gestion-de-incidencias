package com.incident_manager.DTO.ticket;

import com.incident_manager.entity.TicketPriority;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Data Transfer Object for ticket update request.
 *
 * <p>Used to update an existing ticket. All fields are optional.
 * Only provided (non-null) fields will be updated.
 *
 * @param title the new ticket title
 * @param description the new ticket description
 * @param priority the new priority level
 * @param assigneeId the user to assign the ticket to
 */
public record TicketUpdateDTO(
        @Size(max = 150, message = "Title must not exceed 150 characters")
        String title,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        TicketPriority priority,
        UUID assigneeId
) {}
