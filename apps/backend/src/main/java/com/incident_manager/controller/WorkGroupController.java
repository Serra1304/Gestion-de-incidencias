package com.incident_manager.controller;

import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.workGroup.*;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.mapper.WorkGroupMapper;
import com.incident_manager.service.WorkGroupService;

import com.incident_manager.service.command.UpdateWorkGroupCommand;
import com.incident_manager.service.data.WorkGroupFullData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class WorkGroupController {

    private final WorkGroupService service;
    private final WorkGroupMapper groupMapper;
    private final UserMapper userMapper;

    @Operation(
            summary = "Create work group",
            description = "Creates a new work group in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Group already exists",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping(
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            "application/problem+json"
                    }
            )
    public ResponseEntity<WorkGroupResponseDTO> create(@Valid @RequestBody WorkGroupCreateDTO dto) {
        WorkGroup group = service.createGroup(
                groupMapper.toCreateWorkGroupCommand(dto));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupMapper.toWorkGroupResponseDTO(group));
    }

    // Update group
    @PutMapping("/{groupId}")
    public ResponseEntity<WorkGroupFullDTO> updateGroup(@PathVariable UUID groupId, @RequestBody WorkGroupSaveDTO dto
    ) {
        UpdateWorkGroupCommand cmd = new UpdateWorkGroupCommand(
                groupId,
                dto.name(),
                dto.description(),
                dto.active(),
                dto.userIds()
        );

        WorkGroupFullData result = service.updateGroup(cmd);

        return ResponseEntity.ok(groupMapper.toFullGroupDTO(result));
    }

    // Delete group
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@PathVariable UUID groupId) {
        service.deleteGroup(groupId);

        return ResponseEntity.noContent().build();
    }

    // Assign user to group
    @PostMapping("/{groupId}/assign/{userId}")
    public ResponseEntity<WorkGroupResponseDTO> assignUser(@PathVariable UUID groupId, @PathVariable UUID userId) {
        WorkGroup group = service.assignUser(groupId, userId);

        return ResponseEntity.ok(groupMapper.toWorkGroupResponseDTO(group));
    }

    // Remove user from group
    @DeleteMapping("/{groupId}/remove/{userId}")
    public ResponseEntity<WorkGroupResponseDTO> removeUser(@PathVariable UUID groupId, @PathVariable UUID userId) {
        WorkGroup group = service.removeUser(groupId, userId);

        return ResponseEntity.ok(groupMapper.toWorkGroupResponseDTO(group));
    }

    // Get list group
    @GetMapping
    public ResponseEntity<List<WorkGroupResponseDTO>> listGroup() {
        return ResponseEntity.ok(
                service.listGroups()
                .stream()
                .map(groupMapper::toWorkGroupResponseDTO)
                .toList());
    }

    // Get group
    @GetMapping("/{groupId}")
    public ResponseEntity<WorkGroupResponseDTO> getGroup(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupMapper.toWorkGroupResponseDTO(service.getGroup(groupId)));
    }

    // Get full group
    @GetMapping("/{groupId}/full")
    public ResponseEntity<WorkGroupFullDTO> getFullGroup(@PathVariable UUID groupId) {

        return ResponseEntity.ok(groupMapper.toFullGroupDTO(service.getGroupFullData(groupId)));
    }

    // Get users from group
    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<UserSummaryDTO>> getGroupUsers(@PathVariable UUID groupId) {
        Set<AuthUser> users = service.getGroupUsers(groupId);

        return ResponseEntity.ok(users
                .stream()
                .map(AuthUser::getProfile)
                .map(userMapper::toUserSummaryDTO)
                .toList());
    }

    // Get Available user from group
    @GetMapping("/{groupId}/users/available")
    public ResponseEntity<List<UserSummaryDTO>> getAvailableUsers(@PathVariable UUID groupId) {
        List<AuthUser> users = service.getAvailableUsers(groupId);

        return ResponseEntity.ok(users
                .stream()
                .map(AuthUser::getProfile)
                .map(userMapper::toUserSummaryDTO)
                .toList());
    }

    // Update group users list
    @PostMapping("/{groupId}/users")
    public ResponseEntity<WorkGroupFullDTO> updateGroupUsers(@PathVariable UUID groupId, @RequestBody GroupUsersUpdateDTO dto) {

        return ResponseEntity.ok(groupMapper.toFullGroupDTO(service.updateGroupUsers(groupId, dto.userIds())));
    }
}

