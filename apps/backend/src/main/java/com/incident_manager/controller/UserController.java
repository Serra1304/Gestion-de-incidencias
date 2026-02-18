package com.incident_manager.controller;

import com.incident_manager.DTO.user.UserCreateDTO;
import com.incident_manager.DTO.user.UserDTO;
import com.incident_manager.DTO.user.UserInfoDTO;
import com.incident_manager.DTO.user.UserSaveDTO;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.service.UserService;
import com.incident_manager.service.command.UpdateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserProfile user = userService.createUser(userMapper.toCreateUserCommand(userCreateDTO));

        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.getUserById(userId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody UserSaveDTO dto) {
        UpdateUserCommand cmd = userMapper.toUpdateUserCommand(userId, dto);
        UserProfile user = userService.updateUser(cmd);

        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
