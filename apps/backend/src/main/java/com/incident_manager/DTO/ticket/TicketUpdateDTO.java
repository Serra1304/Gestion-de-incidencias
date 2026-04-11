package com.incident_manager.DTO.ticket;

import com.incident_manager.entity.TicketPriority;

import java.util.UUID;

public record TicketUpdateDTO(
        String title,
        String description,
        TicketPriority priority,
        UUID assigneeId
) {}
