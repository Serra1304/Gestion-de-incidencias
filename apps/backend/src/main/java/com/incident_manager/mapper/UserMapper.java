package com.incident_manager.mapper;

import com.incident_manager.DTO.user.UserCreateDTO;
import com.incident_manager.DTO.user.UserResponseDTO;
import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.user.UserUpdateDTO;
import com.incident_manager.DTO.workGroup.WorkGroupInfoDTO;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.service.command.CreateUserCommand;
import com.incident_manager.service.command.UpdateUserCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserMapper {

    public UserSummaryDTO toUserSummaryDTO(UserProfile user) {
        return new UserSummaryDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getSecondLastName()
        );
    }

    public UserResponseDTO toUserResponseDTO(UserProfile user) {
        List<WorkGroupInfoDTO> groups = user.getAuthUser().getGroups()
                .stream()
                .map(group -> new WorkGroupInfoDTO(
                        group.getId(),
                        group.getName()))
                .toList();

        return new UserResponseDTO(
                user.getId(), user.getName(),
                user.getLastName(),
                user.getSecondLastName(),
                user.getAuthUser().getEmail(),
                user.getAuthUser().isEnabled(),
                groups,
                user.getAddress(),
                user.getAddressNumber(),
                user.getCity(),
                user.getProvince(),
                user.getPostalCode(),
                user.getPhone(),
                user.getPhoneBusiness(),
                user.getPhoneExtension()
        );
    }

    public CreateUserCommand toCreateUserCommand(UserCreateDTO user) {
        return new CreateUserCommand(
                user.name(),
                user.lastname(),
                user.secondLastname(),
                user.address(),
                user.addressNumber(),
                user.city(),
                user.province(),
                user.postalCode(),
                user.phone(),
                user.phoneBusiness(),
                user.phoneExtension(),
                user.email(),
                user.password(),
                user.active(),
                user.groups()
        );
    }

    public UpdateUserCommand toUpdateUserCommand(UUID id, UserUpdateDTO user) {
        return new UpdateUserCommand(
                id,
                user.name(),
                user.lastName(),
                user.secondLastName(),
                user.address(),
                user.addressNumber(),
                user.city(),
                user.province(),
                user.postalCode(),
                user.phone(),
                user.phoneBusiness(),
                user.phoneExtension(),
                user.email(),
                user.active(),
                user.groups()
        );
    }
}
