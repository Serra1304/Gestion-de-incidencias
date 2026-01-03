package com.incident_manager.controller;

import com.incident_manager.DTO.user.UserInfoDTO;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        List<UserProfile> users = userService.getUsers();

        return ResponseEntity.ok(users.stream().map(userMapper::toUserInfoDTO).toList());
    }
}
