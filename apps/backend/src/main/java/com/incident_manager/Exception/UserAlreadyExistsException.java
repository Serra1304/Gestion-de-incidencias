package com.incident_manager.Exception;

/**
 * Exception thrown when attempting to create a user with an email that already exists.
 *
 * <p>This is a runtime exception that is caught by the global exception handler
 * and translated to an HTTP 409 Conflict response.
 *
 * <p>This exception is typically thrown during user creation when the email address
 * is not unique in the system.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UserAlreadyExistsException for the specified email.
     *
     * @param email the email address that already exists
     */
    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists");
    }
}
