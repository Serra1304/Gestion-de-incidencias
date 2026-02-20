package com.incident_manager.controller;

import com.incident_manager.DTO.user.UserCreateDTO;
import com.incident_manager.DTO.user.UserResponseDTO;
import com.incident_manager.DTO.user.UserSummaryDTO;
import com.incident_manager.DTO.user.UserSaveDTO;
import com.incident_manager.entity.UserProfile;
import com.incident_manager.mapper.UserMapper;
import com.incident_manager.service.UserService;
import com.incident_manager.service.command.UpdateUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
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
            description = "Creates a new user in the system using the provided data"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/problem+json"
            }
    )
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserProfile user = userService.createUser(
                userMapper.toCreateUserCommand(userCreateDTO));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toUserDTO(user));
    }

    @Operation(
            summary = "Get all users",
            description = "Returns a list of users with basic identification data"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User list retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserSummaryDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        List<UserProfile> users = userService.getUsers();

        return ResponseEntity.ok(users
                .stream()
                .map(userMapper::toUserInfoDTO)
                .toList());
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
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID userId) {
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
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userId, @RequestBody UserSaveDTO dto) {
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
