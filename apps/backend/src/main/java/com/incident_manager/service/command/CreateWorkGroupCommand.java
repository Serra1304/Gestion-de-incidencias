package com.incident_manager.service.command;

public record CreateWorkGroupCommand(
        String name,
        String description,
        Boolean active
) {
}
