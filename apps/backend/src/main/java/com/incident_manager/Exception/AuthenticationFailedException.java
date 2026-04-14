package com.incident_manager.Exception;

/**
 * Exception thrown when user authentication fails.
 *
 * <p>This is a runtime exception that is caught by the global exception handler
 * and translated to an HTTP 401 Unauthorized response.
 *
 * <p>Example scenarios:
 * <ul>
 *   <li>Invalid email or password during login</li>
 *   <li>Invalid or expired authentication token</li>
 *   <li>User account is disabled</li>
 * </ul>
 */
public class AuthenticationFailedException extends RuntimeException {
    /**
     * Constructs a new AuthenticationFailedException with the specified message.
     *
     * @param message the detail message
     */
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
