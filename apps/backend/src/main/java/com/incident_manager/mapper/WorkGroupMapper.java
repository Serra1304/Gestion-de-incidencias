package com.incident_manager.mapper;

import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.workGroup.WorkGroupCreateDTO;
import com.incident_manager.DTO.workGroup.WorkGroupResponseDTO;
import com.incident_manager.DTO.workGroup.WorkGroupFullDTO;
import com.incident_manager.DTO.workGroup.WorkGroupUpdateDTO;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.service.command.CreateWorkGroupCommand;
import com.incident_manager.service.command.UpdateUserCommand;
import com.incident_manager.service.command.UpdateWorkGroupCommand;
import com.incident_manager.service.data.WorkGroupFullData;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper component for converting between WorkGroup entities and DTOs.
 *
 * <p>Handles conversion between:
 * <ul>
 *   <li>WorkGroup entity ↔ WorkGroupResponseDTO</li>
 *   <li>WorkGroupFullData ↔ WorkGroupFullDTO</li>
 *   <li>WorkGroupCreateDTO ↔ CreateWorkGroupCommand</li>
 *   <li>WorkGroupUpdateDTO ↔ UpdateWorkGroupCommand</li>
 * </ul>
 */
@Component
public class WorkGroupMapper {

    /**
     * Converts a WorkGroup entity to a WorkGroupResponseDTO.
     *
     * <p>Includes basic group information (id, name, description, active status).
     *
     * @param group the WorkGroup entity
     * @return WorkGroupResponseDTO with group information
     */
    public WorkGroupResponseDTO toWorkGroupResponseDTO(WorkGroup group) {
        return new WorkGroupResponseDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getActive()
        );
    }

    /**
     * Converts WorkGroupFullData to a WorkGroupFullDTO.
     *
     * <p>Includes complete group information with associated users and available users.
     *
     * @param groupFullData the WorkGroupFullData containing group and user lists
     * @return WorkGroupFullDTO with complete group and user information
     */
    public WorkGroupFullDTO toFullGroupDTO(WorkGroupFullData groupFullData) {
        return new WorkGroupFullDTO(
                groupFullData.group().getId(),
                groupFullData.group().getName(),
                groupFullData.group().getDescription(),
                groupFullData.group().getActive(),
                groupFullData.groupUsers()
                        .stream()
                        .map(user -> new UserSummaryDTO(
                                user.getId(),
                                user.getProfile().getName(),
                                user.getProfile().getLastName(),
                                user.getProfile().getSecondLastName()))
                        .collect(Collectors.toList()),
                groupFullData.availableUsers()
                        .stream()
                        .map(user -> new UserSummaryDTO(
                                user.getId(),
                                user.getProfile().getName(),
                                user.getProfile().getLastName(),
                                user.getProfile().getSecondLastName()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a WorkGroupCreateDTO to a CreateWorkGroupCommand.
     *
     * @param dto the WorkGroupCreateDTO with group creation data
     * @return CreateWorkGroupCommand ready for service layer processing
     */
    public CreateWorkGroupCommand toCreateWorkGroupCommand(WorkGroupCreateDTO dto) {
        return new CreateWorkGroupCommand(
                dto.name(),
                dto.description(),
                dto.active()
        );
    }

    /**
     * Converts a WorkGroupUpdateDTO to an UpdateWorkGroupCommand.
     *
     * @param id the work group identifier
     * @param dto the WorkGroupUpdateDTO with fields to update
     * @return UpdateWorkGroupCommand ready for service layer processing
     */
    public UpdateWorkGroupCommand toUpdateWorkGroupCommand(UUID id, WorkGroupUpdateDTO dto) {
        return new UpdateWorkGroupCommand(
                id,
                dto.name(),
                dto.description(),
                dto.active(),
                dto.userIds()
        );
    }
}
