package com.incident_manager.service;

import com.incident_manager.Exception.BadRequestException;
import com.incident_manager.Exception.ConflictException;
import com.incident_manager.Exception.ResourceNotFoundException;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.repository.AuthUserRepository;
import com.incident_manager.repository.WorkGroupRepository;

import com.incident_manager.service.command.CreateWorkGroupCommand;
import com.incident_manager.service.command.UpdateWorkGroupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Application service responsible for managing work groups.
 *
 * <p>This service coordinates the creation, update, retrieval and deletion of work groups,
 * handling both group data and user assignments.
 *
 * <p>Main responsibilities:
 * <ul>
 *   <li>Enforce business rules (group name uniqueness, user existence)</li>
 *   <li>Handle user assignment and removal from groups</li>
 *   <li>Manage transactional consistency for group operations</li>
 * </ul>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class WorkGroupService {
    private final WorkGroupRepository groupRepository;
    private final AuthUserRepository userRepository;

    /**
     * Creates a new work group in the system.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Group name must be unique (case-insensitive)</li>
     * </ul>
     *
     * @param cmd command containing group data
     * @return persisted {@link WorkGroup}
     *
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

    /**
     * Updates an existing work group.
     *
     * <p>Only non-null fields in the command are applied.
     *
     * @param cmd command containing fields to update
     * @return updated {@link WorkGroup}
     *
     * @throws ResourceNotFoundException if the group does not exist
     */
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

    /**
     * Deletes a work group by id.
     *
     * @param id work group identifier
     *
     * @throws ResourceNotFoundException if the group does not exist
     */
    public void deleteGroup(UUID id) {
        groupRepository.deleteById(id);
    }

    /**
     * Adds a user to a work group.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Both group and user must exist</li>
     *   <li>User must not already be assigned to the group</li>
     * </ul>
     *
     * @param groupId work group identifier
     * @param userId user identifier
     * @return updated {@link WorkGroup}
     *
     * @throws ResourceNotFoundException if group or user does not exist
     * @throws ConflictException if user is already assigned to the group
     */
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

    /**
     * Removes a user from a work group.
     *
     * @param groupId work group identifier
     * @param userId user identifier
     * @return updated {@link WorkGroup}
     *
     * @throws ResourceNotFoundException if group or user does not exist
     * @throws BadRequestException if user is not part of the group
     */
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

    /**
     * Retrieves a work group by its identifier.
     *
     * @param id work group identifier
     * @return {@link WorkGroup}
     *
     * @throws ResourceNotFoundException if the group does not exist
     */
    public WorkGroup getGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }

    /**
     * Retrieves all work groups in the system.
     *
     * @return list of {@link WorkGroup}
     */
    public List<WorkGroup> listGroups() {
        return groupRepository.findAll();
    }

    /**
     * Retrieves all users that belong to a work group.
     *
     * @param groupId work group identifier
     * @return list of {@link UserProfile} belonging to the group
     *
     * @throws ResourceNotFoundException if the group does not exist
     */
    public List<UserProfile> getGroupUsers(UUID groupId) {
        WorkGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        return group
                .getUsers()
                .stream()
                .map(AuthUser::getProfile)
                .toList();
    }

    /**
     * Retrieves all users that do not belong to a work group.
     *
     * @param groupId work group identifier
     * @return list of {@link UserProfile} not assigned to the group
     *
     * @throws ResourceNotFoundException if the group does not exist
     */
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

    /**
     * Replaces the entire list of users for a work group.
     *
     * @param groupId work group identifier
     * @param userIds list of user identifiers to be assigned to the group
     * @return updated {@link WorkGroup}
     *
     * @throws ResourceNotFoundException if the group does not exist
     * @throws BadRequestException if any provided user does not exist
     */
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
}
