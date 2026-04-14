package com.incident_manager.Exception;

/**
 * Exception thrown when a requested resource is not found in the system.
 *
 * <p>This is a runtime exception that is typically caught by the global exception handler
 * and translated to an HTTP 404 Not Found response.
 *
 * <p>Example scenarios:
 * <ul>
 *   <li>Requesting a user that doesn't exist</li>
 *   <li>Requesting a work group that doesn't exist</li>
 *   <li>Requesting a ticket that doesn't exist</li>
 * </ul>
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
