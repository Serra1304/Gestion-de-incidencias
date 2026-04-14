package com.incident_manager.DTO;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for API error responses.
 *
 * <p>Used to communicate error information to API clients in a consistent format.
 * Contains timestamp, HTTP status, error type, message, and the request path.
 *
 * @param timestamp when the error occurred
 * @param status HTTP status code
 * @param error HTTP status reason phrase
 * @param message error message description
 * @param path the request URI path
 */
public record ApiErrorDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
    /**
     * Creates an ApiErrorDTO from HTTP status and error details.
     *
     * @param status the HTTP status
     * @param message the error message
     * @param path the request path
     * @return ApiErrorDTO with current timestamp
     */
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
