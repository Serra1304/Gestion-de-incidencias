package com.incident_manager.mapper;

import com.incident_manager.DTO.user.UserInfoDTO;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfoDTO toUserInfoDTO(AuthUser user) {
        return new UserInfoDTO(
                user.getProfile().getId(),
                user.getProfile().getName(),
                user.getProfile().getLastName(),
                user.getProfile().getSecondLastName()
        );
    }

    public UserInfoDTO toUserInfoDTO(UserProfile user) {
        return new UserInfoDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getSecondLastName()
        );
    }
}
