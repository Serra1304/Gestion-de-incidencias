package com.incident_manager.DTO;

import com.incident_manager.DTO.user.UserSummaryDTO;

public record AuthResponseDTO(String token, UserSummaryDTO userSummaryDTO, UserAuthDTO userAuthDTO) {}
