package com.incident_manager.DTO;

import com.incident_manager.DTO.user.UserSummaryDTO;

/**
 * Data Transfer Object for authentication response.
 *
 * <p>Returned when a user successfully logs in. Contains the JWT token and user information.
 *
 * @param token the JWT authentication token
 * @param userSummaryDTO summary of user identification information
 * @param userAuthDTO authentication-specific user details
 */
public record AuthResponseDTO(String token, UserSummaryDTO userSummaryDTO, UserAuthDTO userAuthDTO) {}
