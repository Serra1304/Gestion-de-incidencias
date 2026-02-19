package com.incident_manager.controller;

import com.incident_manager.DTO.user.UserCreateDTO;
import com.incident_manager.DTO.user.UserDTO;
import com.incident_manager.DTO.user.UserInfoDTO;
import com.incident_manager.DTO.user.UserSaveDTO;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.service.UserService;
import com.incident_manager.service.command.UpdateUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Users",
        description = "System user manager"
)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Create new user",
            description = "Create a new user in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "The user already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserProfile user = userService.createUser(userMapper.toCreateUserCommand(userCreateDTO));

        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @Operation(
            summary = "Get all users",
            description = "Returns a list with basic information about all registered users"
    )
    @ApiResponse(
            responseCode = "200", description = "User list obtained successfully"
    )
    @GetMapping("")
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        List<UserProfile> users = userService.getUsers();

        return ResponseEntity.ok(users.stream().map(userMapper::toUserInfoDTO).toList());
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns complete user information based on their identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.getUserById(userId)));
    }

    @Operation(
            summary = "Update user",
            description = "Update the data of an existing user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody UserSaveDTO dto) {
        UpdateUserCommand cmd = userMapper.toUpdateUserCommand(userId, dto);
        UserProfile user = userService.updateUser(cmd);

        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @Operation(
            summary = "Delete user",
            description = "Remove a user from the system by their identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
