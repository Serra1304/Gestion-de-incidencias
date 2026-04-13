package com.incident_manager.controller;

import com.incident_manager.DTO.AuthResponseDTO;
import com.incident_manager.DTO.LoginRequest;
import com.incident_manager.security.authentication.AuthenticationService;
import com.incident_manager.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequest req) {

        return authService.login(req.email(), req.password());
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = jwtService.getClaims(token);
        UUID userId = UUID.fromString((String) claims.get("uid"));

        authService.logout(token, userId);
    }
}

