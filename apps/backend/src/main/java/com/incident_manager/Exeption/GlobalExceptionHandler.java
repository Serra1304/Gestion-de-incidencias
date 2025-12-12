package com.incident_manager.Exeption;

import com.incident_manager.DTO.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    // Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorDTO.of(HttpStatus.NOT_FOUND, ex.getMessage(), extractPath(request)));
    }

    // Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorDTO> handleBadRequest(BadRequestException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorDTO.of(HttpStatus.BAD_REQUEST, ex.getMessage(), extractPath(request)));
    }

    // Operation Not Allowed – reglas de negocio
    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ApiErrorDTO> handleForbidden(OperationNotAllowedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiErrorDTO.of(HttpStatus.FORBIDDEN, ex.getMessage(), extractPath(request)));
    }

    // Conflict (duplicados)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorDTO> handleConflict(ConflictException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiErrorDTO.of(HttpStatus.CONFLICT, ex.getMessage(), extractPath(request)));
    }

    // Authentication Failed
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiErrorDTO> handleAuthFail(AuthenticationFailedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiErrorDTO.of(HttpStatus.UNAUTHORIZED, ex.getMessage(), extractPath(request)));
    }

    // Fallback para ERRORES NO CONTROLADOS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGeneralError(Exception ex, WebRequest request) {
        ex.printStackTrace(); // Debug

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorDTO.of(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", extractPath(request)));
    }
}

