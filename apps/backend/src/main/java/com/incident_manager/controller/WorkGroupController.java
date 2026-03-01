package com.incident_manager.controller;

import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.workGroup.*;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.mapper.WorkGroupMapper;
import com.incident_manager.service.WorkGroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(
            summary = "Get work group by id",
            description = "Returns detailed information of a work group identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Work group found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Work group not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping(
            value = "/{groupId}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/problem+json"
            }
    )
    public ResponseEntity<WorkGroupResponseDTO> getWorkGroup(
            @Parameter(
                    description = "Unique identifier of the work group",
                    example = "c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21",
                    required = true
            )
            @PathVariable UUID groupId) {

        return ResponseEntity.ok(groupMapper.toWorkGroupResponseDTO(service.getGroup(groupId)));
    }

    @Operation(
            summary = "Get all work groups",
            description = "Returns a list of all work groups in the system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of work groups returned successfully"
            )
    })
    @GetMapping (
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<WorkGroupResponseDTO>> getAllWorkGroups() {
        return ResponseEntity.ok(
                service.listGroups()
                        .stream()
                        .map(groupMapper::toWorkGroupResponseDTO)
                        .toList());
    }


    @Operation(
            summary = "Get users of a work group",
            description = "Returns the list of users that belong to the specified work group"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Work group not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<UserSummaryDTO>> getWorkGroupUsers(@PathVariable UUID groupId) {
        List<UserProfile> users = service.getGroupUsers(groupId);

        return ResponseEntity.ok(users
                .stream()
                .map(userMapper::toUserSummaryDTO)
                .toList());
    }

    @Operation(
            summary = "Get available users for a work group",
            description = "Returns users that do not currently belong to the specified work group"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Work group not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping("/{groupId}/users/available")
    public ResponseEntity<List<UserSummaryDTO>> getAvailableUsers(@PathVariable UUID groupId) {
        List<UserProfile> users = service.getAvailableUsers(groupId);

        return ResponseEntity.ok(users
                .stream()
                .map(userMapper::toUserSummaryDTO)
                .toList());
    }

    @Operation(
            summary = "Update work group",
            description = "Updates an existing work group identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Work group updated successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Work group not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Group name already exists",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping(
            value = "/{groupId}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/problem+json"
            }
    )
    public ResponseEntity<WorkGroupResponseDTO> updateWorkGroup(
            @Parameter(
                    description = "Unique identifier of the work group",
                    example = "c1a7a3d2-9e42-4b9f-bf61-9e3c6c0d9b21",
                    required = true
            )
            @PathVariable UUID groupId,
            @Valid @RequestBody WorkGroupUpdateDTO dto
    ) {
        WorkGroup group = service.updateGroup(
                groupMapper.toUpdateWorkGroupCommand(groupId, dto));

        return ResponseEntity.ok(groupMapper.toWorkGroupResponseDTO(group));
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

    // Update group users list
    @PostMapping("/{groupId}/users")
    public ResponseEntity<WorkGroupFullDTO> updateGroupUsers(@PathVariable UUID groupId, @RequestBody GroupUsersUpdateDTO dto) {

        return ResponseEntity.ok(groupMapper.toFullGroupDTO(service.updateGroupUsers(groupId, dto.userIds())));
    }
}

