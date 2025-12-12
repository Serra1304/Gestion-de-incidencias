package com.incident_manager.DTO;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiErrorDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public static ApiErrorDTO of(HttpStatus status, String message, String path) {
        return new ApiErrorDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }
}
