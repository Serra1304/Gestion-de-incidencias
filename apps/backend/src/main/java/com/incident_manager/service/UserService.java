package com.incident_manager.service;

import com.incident_manager.Exeption.ConflictException;
import com.incident_manager.Exeption.ResourceNotFoundException;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.entity.WorkGroup;
import com.incident_manager.repository.AuthUserRepository;
import com.incident_manager.repository.UserProfileRepository;
import com.incident_manager.repository.WorkGroupRepository;
import com.incident_manager.service.command.CreateUserCommand;
import com.incident_manager.service.command.UpdateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Application service responsible for managing users.
 *
 * <p>This service coordinates the creation, update, retrieval and deletion of users,
 * handling both profile data ({@link UserProfile}) and authentication data ({@link AuthUser}).
 *
 * <p>Main responsibilities:
 * <ul>
 *   <li>Enforce business rules (email uniqueness, group existence)</li>
 *   <li>Handle transactional consistency between AuthUser and UserProfile</li>
 *   <li>Centralize user-related domain logic</li>
 * </ul>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserProfileRepository userRepository;
    private final AuthUserRepository authUserRepository;
    private final WorkGroupRepository workGroupRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user along with its authentication data.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Email must be unique</li>
     *   <li>All provided work groups must exist</li>
     *   <li>Password is stored encoded</li>
     * </ul>
     *
     * @param cmd command containing user creation data
     * @return persisted {@link UserProfile}
     *
     * @throws ConflictException if the email already exists
     * @throws ResourceNotFoundException if any provided group does not exist
     */
    public UserProfile createUser(CreateUserCommand cmd) {
        if (authUserRepository.existsByEmail(cmd.email())) {
            throw new ConflictException("Email already exists");
        }

        AuthUser authUser = new AuthUser();
        authUser.setEmail(cmd.email());
        authUser.setPasswordHash(passwordEncoder.encode(cmd.password()));
        authUser.setEnabled(cmd.active());

        Set<WorkGroup> groups = new HashSet<>();
        if (cmd.groups() != null && !cmd.groups().isEmpty()) {
            List<WorkGroup> foundGroups = workGroupRepository.findAllById(cmd.groups());
            if (foundGroups.size() != cmd.groups().size()) {
                throw new ResourceNotFoundException("One or more groups do not exist");
            }
            groups.addAll(foundGroups);
        }
        authUser.setGroups(groups);

        UserProfile user = buildUserProfile(cmd, authUser);
        userRepository.save(user);

        return user;
    }

    /**
     * Retrieves a user profile by its associated authentication user id.
     *
     * @param id AuthUser identifier
     * @return {@link UserProfile}
     *
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Transactional(readOnly = true)
    public UserProfile getUserProfileByAuthUserId(UUID id) {
        return userRepository.findByAuthUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Retrieves a user profile by its profile id.
     *
     * @param userId user profile identifier
     * @return {@link UserProfile}
     *
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Transactional(readOnly = true)
    public UserProfile getUserProfileById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Retrieves all users.
     *
     * @return list of {@link UserProfile}
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing user.
     *
     * <p>Only non-null fields in the command are applied.
     *
     * <p>Business rules:
     * <ul>
     *   <li>Email changes must preserve uniqueness</li>
     *   <li>All provided work groups must exist</li>
     * </ul>
     *
     * @param cmd command containing fields to update
     * @return updated {@link UserProfile}
     *
     * @throws ResourceNotFoundException if the user or any group does not exist
     * @throws ConflictException if the new email already exists
     */
    public UserProfile updateUser(UpdateUserCommand cmd) {
        UserProfile user = userRepository.findById(cmd.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        AuthUser authUser = user.getAuthUser();

        if (cmd.name() != null) user.setName(cmd.name());
        if (cmd.lastName() != null) user.setLastName(cmd.lastName());
        if (cmd.secondLastName() != null) user.setSecondLastName(cmd.secondLastName());
        if (cmd.address() != null) user.setAddress(cmd.address());
        if (cmd.addressNumber() != null) user.setAddressNumber(cmd.addressNumber());
        if (cmd.city() != null) user.setCity(cmd.city());
        if (cmd.province() != null) user.setProvince(cmd.province());
        if (cmd.postalCode() != null) user.setPostalCode(cmd.postalCode());
        if (cmd.phone() != null) user.setPhone(cmd.phone());
        if (cmd.phoneBusiness() != null) user.setPhoneBusiness(cmd.phoneBusiness());
        if (cmd.phoneExtension() != null) user.setPhoneExtension(cmd.phoneExtension());

        if (cmd.email() != null && !cmd.email().equals(authUser.getEmail())) {
            if (authUserRepository.existsByEmail(cmd.email())) {
                throw new ConflictException("Email already exists");
            }
            authUser.setEmail(cmd.email());
        }

        if (cmd.active() != null) {
            authUser.setEnabled(cmd.active());
        }

        if (cmd.groups() != null) {
            Set<WorkGroup> groups = new HashSet<>();
            if (!cmd.groups().isEmpty()) {
                List<WorkGroup> foundGroups = workGroupRepository.findAllById(cmd.groups());
                if (foundGroups.size() != cmd.groups().size()) {
                    throw new ResourceNotFoundException("One or more groups do not exist");
                }
                groups.addAll(foundGroups);
            }
            authUser.setGroups(groups);
        }

        userRepository.save(user);

        return user;
    }


    /**
     * Deletes a user by id.
     *
     * <p>The operation requires the user to exist.
     *
     * @param id user profile identifier
     * @throws ResourceNotFoundException if the user does not exist
     */
    public void deleteUser(UUID id) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    /**
     * Builds a {@link UserProfile} entity from the creation command.
     *
     * <p>This method centralizes profile initialization logic.
     */
    private UserProfile buildUserProfile(CreateUserCommand cmd, AuthUser authUser) {
        UserProfile user = new UserProfile();
        user.setName(cmd.name());
        user.setLastName(cmd.lastname());
        user.setSecondLastName(cmd.secondLastname());
        user.setAddress(cmd.address());
        user.setAddressNumber(cmd.addressNumber());
        user.setCity(cmd.city());
        user.setProvince(cmd.province());
        user.setPostalCode(cmd.postalCode());
        user.setPhone(cmd.phone());
        user.setPhoneBusiness(cmd.phoneBusiness());
        user.setPhoneExtension(cmd.phoneExtension());
        user.setAuthUser(authUser);
        authUser.setProfile(user);

        return user;
    }
}
