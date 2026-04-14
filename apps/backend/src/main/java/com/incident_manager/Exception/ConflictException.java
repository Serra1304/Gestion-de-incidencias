package com.incident_manager.Exception;

/**
 * Exception thrown when a conflict occurs during an operation.
 *
 * <p>This is a runtime exception typically thrown when attempting to create or update
 * a resource that violates unique constraints or business rules.
 * It is caught by the global exception handler and translated to an HTTP 409 Conflict response.
 *
 * <p>Example scenarios:
 * <ul>
 *   <li>Creating a user with an email that already exists</li>
 *   <li>Creating a work group with a name that already exists</li>
 *   <li>Assigning a user to a group where they're already assigned</li>
 * </ul>
 */
public class ConflictException extends RuntimeException {
    /**
     * Constructs a new ConflictException with the specified message.
     *
     * @param message the detail message
     */
    public ConflictException(String message) {
        super(message);
    }
}
