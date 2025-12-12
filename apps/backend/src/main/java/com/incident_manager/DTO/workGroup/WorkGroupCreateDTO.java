package com.incident_manager.DTO.workGroup;

public record WorkGroupCreateDTO(
        String name,
        String description,
        Boolean active
) { }
