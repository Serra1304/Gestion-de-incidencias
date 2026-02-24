package com.incident_manager.mapper;

import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.workGroup.WorkGroupCreateDTO;
import com.incident_manager.DTO.workGroup.WorkGroupResponseDTO;
import com.incident_manager.DTO.workGroup.WorkGroupFullDTO;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.service.command.CreateWorkGroupCommand;
import com.incident_manager.service.data.WorkGroupFullData;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WorkGroupMapper {

    public WorkGroupResponseDTO toWorkGroupResponseDTO(WorkGroup group) {
        return new WorkGroupResponseDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getActive()
        );
    }

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

    public CreateWorkGroupCommand toCreateWorkGroupCommand(WorkGroupCreateDTO dto) {
        return new CreateWorkGroupCommand(
                dto.name(),
                dto.description(),
                dto.active()
        );
    }
}
