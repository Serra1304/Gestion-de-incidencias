package com.incident_manager.service.command;

import java.util.List;
import java.util.UUID;

public record UpdateWorkGroupCommand(
        UUID groupId,
        String name,
        String description,
        Boolean active,
        List<UUID> userIds
) {}
