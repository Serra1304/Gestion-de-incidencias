package com.incident_manager.mapper;

import com.incident_manager.DTO.ticket.TicketCreateDTO;
import com.incident_manager.DTO.ticket.TicketResponseDTO;
import com.incident_manager.DTO.ticket.TicketUpdateDTO;
import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.workGroup.WorkGroupInfoDTO;
import com.incident_manager.entity.Ticket;
import com.incident_manager.service.command.CreateTicketCommand;
import com.incident_manager.service.command.UpdateTicketCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TicketMapper {

    public CreateTicketCommand toCreateTicketCommand(TicketCreateDTO dto) {
        return new CreateTicketCommand(
                dto.title(),
                dto.description(),
                dto.priority(),
                dto.workGroupId(),
                dto.assigneeId()
        );
    }

    public UpdateTicketCommand toUpdateTicketCommand(UUID ticketId, TicketUpdateDTO dto) {
        return new UpdateTicketCommand(
                ticketId,
                dto.title(),
                dto.description(),
                dto.priority(),
                dto.assigneeId()
        );
    }

    public TicketResponseDTO toTicketResponseDTO(Ticket ticket) {

        UserSummaryDTO reporter =
                new UserSummaryDTO(
                        ticket.getReporter().getId(),
                        ticket.getReporter().getProfile().getName(),
                        ticket.getReporter().getProfile().getLastName(),
                        ticket.getReporter().getProfile().getSecondLastName()
                );

        UserSummaryDTO assignee = null;

        if (ticket.getAssignee() != null) {
            assignee = new UserSummaryDTO(
                    ticket.getAssignee().getId(),
                    ticket.getAssignee().getProfile().getName(),
                    ticket.getAssignee().getProfile().getLastName(),
                    ticket.getAssignee().getProfile().getSecondLastName()
            );
        }

        WorkGroupInfoDTO workGroup =
                new WorkGroupInfoDTO(
                        ticket.getWorkGroup().getId(),
                        ticket.getWorkGroup().getName()
                );

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                reporter,
                assignee,
                workGroup,
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}
