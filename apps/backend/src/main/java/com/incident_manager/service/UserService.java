package com.incident_manager.service;

import com.incident_manager.DTO.UserInfoDTO;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserProfileRepository userRepository;

    public UserService(UserProfileRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public UserInfoDTO getUserInfoByEmail(String email) {
//        UserProfile user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        return new UserInfoDTO(
//                user.getName(),
//                user.getLastName(),
//                user.getSecondLastName());
//    }

    public UserInfoDTO getUserByAuthUserId(UUID id) {
        UserProfile user = userRepository.findByAuthUserId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UserInfoDTO(
                user.getName(),
                user.getLastName(),
                user.getSecondLastName());
    }
}
