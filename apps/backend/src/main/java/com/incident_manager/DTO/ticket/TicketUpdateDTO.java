package com.incident_manager.DTO.ticket;

import com.incident_manager.entity.TicketPriority;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TicketUpdateDTO(
        @Size(max = 150, message = "Title must not exceed 150 characters")
        String title,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        TicketPriority priority,
        UUID assigneeId
) {}
