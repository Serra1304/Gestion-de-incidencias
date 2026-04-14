package com.incident_manager.Exception;

/**
 * Exception thrown when an operation is not allowed due to business rules or permissions.
 *
 * <p>This is a runtime exception that is caught by the global exception handler
 * and translated to an HTTP 403 Forbidden response.
 *
 * <p>Example scenarios:
 * <ul>
 *   <li>User lacks required permissions for the operation</li>
 *   <li>Operation violates business logic constraints</li>
 * </ul>
 */
public class OperationNotAllowedException extends RuntimeException {
    /**
     * Constructs a new OperationNotAllowedException with the specified message.
     *
     * @param message the detail message
     */
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
