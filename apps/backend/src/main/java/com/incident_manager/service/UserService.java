package com.incident_manager.service;

import com.incident_manager.DTO.user.UserSummaryDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserProfileRepository userRepository;
    private final AuthUserRepository authUserRepository;
    private final WorkGroupRepository workGroupRepository;

    public UserService(UserProfileRepository userRepository, AuthUserRepository authUserRepository, WorkGroupRepository workGroupRepository) {
        this.userRepository = userRepository;
        this.authUserRepository = authUserRepository;
        this.workGroupRepository = workGroupRepository;
    }

    public UserProfile createUser(CreateUserCommand cmd) {
        if (authUserRepository.existsByEmail(cmd.email())) {
            throw new ConflictException("User name already exists");
        }

        AuthUser authUser = new AuthUser();
        authUser.setEmail(cmd.email());
        authUser.setPasswordHash(cmd.password());
        authUser.setEnabled(cmd.active());

        System.out.println(cmd.password());

        Set<WorkGroup> groups = new HashSet<>(
                workGroupRepository.findAllById(cmd.groups())
        );
        authUser.setGroups(groups);

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
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user.setAuthUser(authUser);
        authUser.setProfile(user);

        userRepository.save(user);

        return user;
    }

    public UserProfile updateUser(UpdateUserCommand cmd) {
        UserProfile user = userRepository.findById(cmd.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Datos de usuario
        if (cmd.name() != null) user.setName(cmd.name());
        if (cmd.lastname() != null) user.setLastName(cmd.lastname());
        if (cmd.secondLastname() != null) user.setSecondLastName(cmd.secondLastname());
        if (cmd.address() != null) user.setAddress(cmd.address());
        if (cmd.addressNumber() != null) user. setAddressNumber(cmd.addressNumber());
        if (cmd.city() != null) user.setCity(cmd.city());
        if (cmd.province() != null) user.setProvince(cmd.province());
        if (cmd.postalCode() != null) user.setPostalCode(cmd.postalCode());
        if (cmd.phone() != null) user.setPhone(cmd.phone());
        if (cmd.phoneBusiness() != null) user.setPhoneBusiness(cmd.phoneBusiness());
        if (cmd.phoneExtension() != null) user.setPhoneExtension(cmd.phoneExtension());
        if (cmd.email() != null) user.getAuthUser().setEmail(cmd.email());
        if (cmd.active() != null) user.getAuthUser().setEnabled(cmd.active());

        // Grupos de usuario
        if (cmd.groups() != null) {
            Set<WorkGroup> groups = new HashSet<>(
                    workGroupRepository.findAllById(cmd.groups())
            );
            user.getAuthUser().setGroups(groups);
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    public UserSummaryDTO getUserByAuthUserId(UUID id) {
        UserProfile user = userRepository.findByAuthUserId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UserSummaryDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getSecondLastName());
    }

    public UserProfile getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // Find all users
    public List<UserProfile> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
