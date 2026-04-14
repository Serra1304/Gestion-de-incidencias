package com.incident_manager.Exception;

/**
 * Exception thrown when the request contains invalid or malformed data.
 *
 * <p>This is a runtime exception that is caught by the global exception handler
 * and translated to an HTTP 400 Bad Request response.
 *
 * <p>Example scenarios:
 * <ul>
 *   <li>Assigning a ticket to a user not in the work group</li>
 *   <li>Removing a user not assigned to a group</li>
 *   <li>Invalid status transition for a ticket</li>
 * </ul>
 */
public class BadRequestException extends RuntimeException {
    /**
     * Constructs a new BadRequestException with the specified message.
     *
     * @param message the detail message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
