package com.incident_manager.DTO;

import com.incident_manager.DTO.user.UserInfoDTO;

public record AuthResponseDTO(String token, UserInfoDTO userInfoDTO, UserAuthDTO userAuthDTO) {}
