package com.incident_manager.entity;

/**
 * Enumeration of ticket status values.
 *
 * <p>Represents the current state of a ticket in its lifecycle.
 * Tickets follow a workflow: OPEN → IN_PROGRESS/WAITING_CUSTOMER → RESOLVED → CLOSED
 */
public enum TicketStatus {
    /**
     * Ticket has been created and is waiting to be picked up.
     */
    OPEN("Open"),

    /**
     * Ticket is actively being worked on.
     */
    IN_PROGRESS("In progress"),

    /**
     * Ticket is waiting for a response or action from the customer.
     */
    WAITING_CUSTOMER("Waiting customer"),

    /**
     * Ticket has been resolved but not yet closed.
     */
    RESOLVED("Resolved"),

    /**
     * Ticket has been closed and is no longer active.
     */
    CLOSED("Closed"),

    /**
     * Ticket has been cancelled without resolution.
     */
    CANCELLED("Canceled");

    private final String displayName;

    /**
     * Constructs a TicketStatus with a display name.
     *
     * @param displayName the human-readable name
     */
    TicketStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name for this status.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
