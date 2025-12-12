package com.incident_manager.controller;

import com.incident_manager.DTO.*;
import com.incident_manager.DTO.workGroup.*;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.mapper.WorkGroupMapper;
import com.incident_manager.service.WorkGroupService;

import lombok.RequiredArgsConstructor;
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

    // Create group
    @PostMapping
    public ResponseEntity<WorkGroupDTO> create(@RequestBody WorkGroupCreateDTO dto) {
        WorkGroup group = service.createGroup(dto);

        return ResponseEntity.ok(groupMapper.toDTO(group));
    }

    // Update group
    @PutMapping("/{groupId}")
    public ResponseEntity<WorkGroupDTO> update(@PathVariable UUID groupId, @RequestBody WorkGroupUpdateDTO dto) {
        WorkGroup updated = service.updateGroup(groupId, dto);

        return ResponseEntity.ok(groupMapper.toDTO(updated));
    }

    // Delete group
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@PathVariable UUID groupId) {
        service.deleteGroup(groupId);

        return ResponseEntity.noContent().build();
    }

    // Assign user to group
    @PostMapping("/{groupId}/assign/{userId}")
    public ResponseEntity<WorkGroupDTO> assignUser(@PathVariable UUID groupId, @PathVariable UUID userId) {
        WorkGroup group = service.assignUser(groupId, userId);

        return ResponseEntity.ok(groupMapper.toDTO(group));
    }

    // Remove user from group
    @DeleteMapping("/{groupId}/remove/{userId}")
    public ResponseEntity<WorkGroupDTO> removeUser(@PathVariable UUID groupId, @PathVariable UUID userId) {
        WorkGroup group = service.removeUser(groupId, userId);

        return ResponseEntity.ok(groupMapper.toDTO(group));
    }

    // Get list group
    @GetMapping
    public ResponseEntity<List<WorkGroupDTO>> listGroup() {
        return ResponseEntity.ok(
                service.listGroups()
                .stream()
                .map(groupMapper::toDTO)
                .toList());
    }

    // Get group
    @GetMapping("/{groupId}")
    public ResponseEntity<WorkGroupDTO> getGroup(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupMapper.toDTO(service.getGroup(groupId)));
    }

    // Get full group
    @GetMapping("/{groupId}/full")
    public ResponseEntity<WorkGroupFullDTO> getFullGroup(@PathVariable UUID groupId) {

        return ResponseEntity.ok(groupMapper.toFullGroupDTO(service.getGroupFullData(groupId)));
    }

    // Get users from group
    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<UserInfoDTO>> getGroupUsers(@PathVariable UUID groupId) {
        Set<AuthUser> users = service.getGroupUsers(groupId);

        return ResponseEntity.ok(users.stream().map(userMapper::toUserInfoDTO).toList());
    }

    // Get Available user from group
    @GetMapping("/{groupId}/users/available")
    public ResponseEntity<List<UserInfoDTO>> getAvailableUsers(@PathVariable UUID groupId) {
        List<AuthUser> users = service.getAvailableUsers(groupId);

        return ResponseEntity.ok(users.stream().map(userMapper::toUserInfoDTO).toList());
    }

    // Update group users list
    @PostMapping("/{groupId}/users")
    public ResponseEntity<WorkGroupFullDTO> updateGroupUsers(@PathVariable UUID groupId, @RequestBody GroupUsersUpdateDTO dto) {

        return ResponseEntity.ok(groupMapper.toFullGroupDTO(service.updateGroupUsers(groupId, dto.userIds())));
    }
}

