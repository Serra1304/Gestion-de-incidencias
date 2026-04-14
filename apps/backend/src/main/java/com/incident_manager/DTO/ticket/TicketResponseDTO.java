package com.incident_manager.DTO.ticket;

import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.workGroup.WorkGroupInfoDTO;
import com.incident_manager.entity.TicketPriority;
import com.incident_manager.entity.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for ticket response.
 *
 * <p>Complete ticket information returned by API endpoints.
 * Includes ticket details, users involved, work group, and timestamps.
 */
public record TicketResponseDTO(

        UUID id,
        String title,
        String description,
        TicketPriority priority,
        TicketStatus status,

        UserSummaryDTO reporter,
        UserSummaryDTO assignee,

        WorkGroupInfoDTO workGroup,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
