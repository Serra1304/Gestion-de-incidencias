package com.incident_manager.entity;

public enum TicketStatus {
    OPEN("Open"),
    IN_PROGRESS("In progress"),
    WAITING_CUSTOMER("Waiting customer"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    CANCELLED("Canceled");

    private final String displayName;

    TicketStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
