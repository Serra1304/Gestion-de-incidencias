package com.incident_manager.service.command;

import com.incident_manager.entity.TicketPriority;

import java.util.UUID;

public record UpdateTicketCommand(

        UUID ticketId,

        String title,

        String description,

        TicketPriority priority,

        UUID assigneeId

) {}
