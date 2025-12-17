package com.incident_manager.mapper;

import com.incident_manager.DTO.UserInfoDTO;
import com.incident_manager.entity.AuthUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfoDTO toUserInfoDTO(AuthUser user) {
        return new UserInfoDTO(
                user.getId(),
                user.getProfile().getName(),
                user.getProfile().getLastName(),
                user.getProfile().getSecondLastName()
        );
    }
}
