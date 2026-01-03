package com.incident_manager.security.authentication;

import com.incident_manager.DTO.AuthResponseDTO;
import com.incident_manager.DTO.UserAuthDTO;
import com.incident_manager.DTO.user.UserInfoDTO;
import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.InvalidToken;
import com.incident_manager.repository.AuthUserRepository;
import com.incident_manager.repository.InvalidTokenRepository;
import com.incident_manager.repository.UserProfileRepository;
import com.incident_manager.security.jwt.JwtService;
import com.incident_manager.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final AuthUserRepository authUserRepository;
    private final InvalidTokenRepository invalidTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationService(
            AuthUserRepository authUserRepository, UserProfileRepository userProfileRepository,
            InvalidTokenRepository invalidTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService, UserService userService
    ) {
        this.authUserRepository = authUserRepository;
        this.userService = userService;
        this.invalidTokenRepository = invalidTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(String email, String password) {
        AuthUser user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getSecurityRole());
        UserAuthDTO userAuthDTO = new UserAuthDTO(user.getEmail(), user.getSecurityRole());
        UserInfoDTO userInfoDTO = userService.getUserByAuthUserId(user.getId());

        return new AuthResponseDTO(token, userInfoDTO, userAuthDTO);
    }

    public void logout(String token, UUID userId) {
        invalidTokenRepository.save(new InvalidToken(null, token, userId, null));
    }
}

