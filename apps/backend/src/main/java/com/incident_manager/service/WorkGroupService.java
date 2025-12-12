package com.incident_manager.service;

import com.incident_manager.DTO.workGroup.WorkGroupCreateDTO;
import com.incident_manager.DTO.workGroup.WorkGroupUpdateDTO;
import com.incident_manager.Exeption.BadRequestException;
import com.incident_manager.Exeption.ConflictException;
import com.incident_manager.Exeption.ResourceNotFoundException;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.repository.AuthUserRepository;
import com.incident_manager.repository.WorkGroupRepository;

import com.incident_manager.service.data.WorkGroupFullData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkGroupService {

    private final WorkGroupRepository groupRepository;
    private final AuthUserRepository userRepository;

    public WorkGroup createGroup(WorkGroupCreateDTO dto) {
        if (groupRepository.existsByName(dto.name())) {
            throw new ConflictException("Group name already exists");
        }

        WorkGroup group = new WorkGroup();
        group.setName(dto.name());
        group.setDescription(dto.description());
        group.setActive(dto.active() != null ? dto.active() : true);

        return groupRepository.save(group);
    }

    public WorkGroup updateGroup(UUID id, WorkGroupUpdateDTO dto) {
        WorkGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        if (dto.name() != null) group.setName(dto.name());
        if (dto.description() != null) group.setDescription(dto.description());
        if (dto.active() != null) group.setActive(dto.active());

        group.setUpdatedAt(java.time.LocalDateTime.now());
        return groupRepository.save(group);
    }

    public void deleteGroup(UUID id) {
        groupRepository.deleteById(id);
    }

    public WorkGroup assignUser(UUID groupId, UUID userId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        AuthUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean added = group.getUsers().add(user);
        if (!added) {
            throw new ConflictException("User is already assigned to this group");
        }

        return groupRepository.save(group);
    }

    public WorkGroup removeUser(UUID groupId, UUID userId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        AuthUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean removed = group.getUsers().remove(user);
        if (!removed) {
            throw new BadRequestException("User is not part of this group");
        }

        return groupRepository.save(group);
    }

    public WorkGroup getGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }

    public List<WorkGroup> listGroups() {
        return groupRepository.findAll();
    }

    public Set<AuthUser> getGroupUsers(UUID groupId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        return group.getUsers();
    }

    public List<AuthUser> getAvailableUsers(UUID groupId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Set<AuthUser> usersInGroup = group.getUsers();

        return userRepository.findAll()
                .stream()
                .filter(u -> !usersInGroup.contains(u))
                .toList();
    }

    public WorkGroupFullData updateGroupUsers(UUID groupId, List<UUID> userIds) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));


        List<AuthUser> newUsers = userRepository.findAllById(userIds);
        if (newUsers.size() != userIds.size()) {
            throw new BadRequestException("Some users do not exist");
        }

        group.setUsers(new HashSet<>(newUsers));
        groupRepository.save(group);

        return getGroupFullData(groupId);
    }

    public WorkGroupFullData getGroupFullData(UUID groupId) {
        WorkGroup group = getGroup(groupId);
        Set<AuthUser> groupUsers = getGroupUsers(groupId);
        List<AuthUser> availableUsers = getAvailableUsers(groupId);

        return new WorkGroupFullData(group, groupUsers, availableUsers);
    }

}
