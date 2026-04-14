package com.incident_manager.Exception;

import com.incident_manager.DTO.ApiErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST controllers.
 *
 * <p>Centralizes exception handling across the application and provides consistent
 * error responses in the RFC 7807 Problem Details format.
 *
 * <p>Handled exceptions:
 * <ul>
 *   <li>MethodArgumentNotValidException - validation errors (400)</li>
 *   <li>ResourceNotFoundException - resource not found (404)</li>
 *   <li>BadRequestException - invalid request (400)</li>
 *   <li>OperationNotAllowedException - forbidden operation (403)</li>
 *   <li>ConflictException - resource conflict (409)</li>
 *   <li>AuthenticationFailedException - authentication failure (401)</li>
 *   <li>General Exception - internal server error (500)</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Extracts the request path from the WebRequest.
     *
     * @param request the web request
     * @return the request URI path
     */
    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    /**
     * Handles validation exceptions from request body validation.
     *
     * @param ex the validation exception
     * @param request the HTTP request
     * @return HTTP 400 Bad Request with detailed validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation error");
        problem.setType(URI.create("https://api.incident-manager.com/problems/validation-error"));
        problem.setDetail("One or more fields are invalid");
        problem.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    /**
     * Handles conflicts when a user already exists with the same email.
     *
     * @param ex the UserAlreadyExistsException
     * @param request the HTTP request
     * @return HTTP 409 Conflict
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleConflict(
            UserAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problem.setTitle("Resource conflict");
        problem.setType(URI.create("https://api.incident-manager.com/problems/conflict"));
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    /**
     * Handles entity not found exceptions from JPA.
     *
     * @param ex the EntityNotFoundException
     * @param request the HTTP request
     * @return HTTP 404 Not Found
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Resource not found");
        problem.setType(URI.create("https://api.incident-manager.com/problems/not-found"));
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    /**
     * Handles unexpected exceptions not caught by specific handlers.
     *
     * @param ex the generic Exception
     * @param request the HTTP request
     * @return HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Internal server error");
        problem.setType(URI.create("https://api.incident-manager.com/problems/internal-error"));
        problem.setDetail("An unexpected error occurred");
        problem.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.internalServerError().body(problem);
    }

    /**
     * Handles type mismatch exceptions for request parameters.
     *
     * @param ex the MethodArgumentTypeMismatchException
     * @param request the HTTP request
     * @return HTTP 400 Bad Request with parameter details
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Invalid request parameter");
        problem.setType(URI.create("https://api.incident-manager.com/problems/invalid-parameter"));
        problem.setDetail(
                String.format(
                        "Parameter '%s' has invalid value '%s'",
                        ex.getName(),
                        ex.getValue()
                )
        );
        problem.setInstance(URI.create(request.getRequestURI()));

        return ResponseEntity.badRequest().body(problem);
    }

    /**
     * Handles ResourceNotFoundException from custom exception.
     *
     * @param ex the ResourceNotFoundException
     * @param request the web request
     * @return HTTP 404 Not Found with error details
     */
    // Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorDTO.of(HttpStatus.NOT_FOUND, ex.getMessage(), extractPath(request)));
    }

    /**
     * Handles BadRequestException from custom exception.
     *
     * @param ex the BadRequestException
     * @param request the web request
     * @return HTTP 400 Bad Request with error details
     */
    // Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorDTO> handleBadRequest(BadRequestException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorDTO.of(HttpStatus.BAD_REQUEST, ex.getMessage(), extractPath(request)));
    }

    /**
     * Handles OperationNotAllowedException from custom exception.
     *
     * @param ex the OperationNotAllowedException
     * @param request the web request
     * @return HTTP 403 Forbidden with error details
     */
    // Operation Not Allowed – reglas de negocio
    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ApiErrorDTO> handleForbidden(OperationNotAllowedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiErrorDTO.of(HttpStatus.FORBIDDEN, ex.getMessage(), extractPath(request)));
    }

    /**
     * Handles ConflictException from custom exception.
     *
     * @param ex the ConflictException
     * @param request the web request
     * @return HTTP 409 Conflict with error details
     */
    // Conflict (duplicados)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorDTO> handleConflict(ConflictException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiErrorDTO.of(HttpStatus.CONFLICT, ex.getMessage(), extractPath(request)));
    }

    /**
     * Handles AuthenticationFailedException from custom exception.
     *
     * @param ex the AuthenticationFailedException
     * @param request the web request
     * @return HTTP 401 Unauthorized with error details
     */
    // Authentication Failed
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiErrorDTO> handleAuthFail(AuthenticationFailedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiErrorDTO.of(HttpStatus.UNAUTHORIZED, ex.getMessage(), extractPath(request)));
    }
}

