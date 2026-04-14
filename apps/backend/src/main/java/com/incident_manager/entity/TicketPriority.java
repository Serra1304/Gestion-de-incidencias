package com.incident_manager.entity;

/**
 * Enumeration of ticket priority levels.
 *
 * <p>Represents the urgency and importance of a ticket in the incident management system.
 */
public enum TicketPriority {
    /**
     * Low priority - minor issues that can be addressed when time permits.
     */
    LOW("Low"),

    /**
     * Medium priority - issues that should be addressed within normal timeframe.
     */
    MEDIUM("Medium"),

    /**
     * High priority - important issues that require prompt attention.
     */
    HIGH("High"),

    /**
     * Critical priority - urgent issues requiring immediate action.
     */
    CRITICAL("Critical");

    private final String displayName;

    /**
     * Constructs a TicketPriority with a display name.
     *
     * @param displayName the human-readable name
     */
    TicketPriority(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name for this priority level.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
