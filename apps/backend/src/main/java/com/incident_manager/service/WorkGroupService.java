package com.incident_manager.service;

import com.incident_manager.Exeption.BadRequestException;
import com.incident_manager.Exeption.ConflictException;
import com.incident_manager.Exeption.ResourceNotFoundException;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.repository.AuthUserRepository;
import com.incident_manager.repository.WorkGroupRepository;

import com.incident_manager.service.command.CreateWorkGroupCommand;
import com.incident_manager.service.command.UpdateWorkGroupCommand;
import com.incident_manager.service.data.WorkGroupFullData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkGroupService {
    private final WorkGroupRepository groupRepository;
    private final AuthUserRepository userRepository;

    /**
     * Creates a new work group in the system.
     *
     * @param cmd command containing group data
     * @return persisted WorkGroup
     * @throws ConflictException if a group with the same name already exists
     */
    public WorkGroup createGroup(CreateWorkGroupCommand cmd) {
        String normalizedName = cmd.name().trim();
        if (groupRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new ConflictException("Group name already exists");
        }

        WorkGroup group = new WorkGroup();
        group.setName(cmd.name());
        group.setDescription(cmd.description());
        group.setActive(Boolean.TRUE.equals(cmd.active()));

        return groupRepository.save(group);
    }

    public WorkGroup updateGroup(UpdateWorkGroupCommand cmd) {
        WorkGroup group = groupRepository.findById(cmd.groupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        if (cmd.name() != null) group.setName(cmd.name());
        if (cmd.description() != null) group.setDescription(cmd.description());
        if (cmd.active() != null) group.setActive(cmd.active());
        if (cmd.userIds() != null) {
            Set<AuthUser> users = new HashSet<>(
                    userRepository.findAllById(cmd.userIds())
            );
            group.setUsers(users);
        }

        groupRepository.save(group);

        return group;
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

    public List<UserProfile> getGroupUsers(UUID groupId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        return group
                .getUsers()
                .stream()
                .map(AuthUser::getProfile)
                .toList();
    }

    public List<UserProfile> getAvailableUsers(UUID groupId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Set<AuthUser> usersInGroup = group.getUsers();

        return userRepository.findAll()
                .stream()
                .filter(u -> !usersInGroup.contains(u))
                .map(AuthUser::getProfile)
                .toList();
    }

    public WorkGroup updateGroupUsers(UUID groupId, List<UUID> userIds) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));


        List<AuthUser> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new BadRequestException("Some users do not exist");
        }

        group.setUsers(new HashSet<>(users));
        groupRepository.save(group);

        return group;
    }

//    public WorkGroupFullData getGroupFullData(UUID groupId) {
//        WorkGroup group = getGroup(groupId);
//        Set<AuthUser> groupUsers = getGroupUsers(groupId);
//        List<AuthUser> availableUsers = getAvailableUsers(groupId);
//
//        return new WorkGroupFullData(group, groupUsers, availableUsers);
//    }

}
