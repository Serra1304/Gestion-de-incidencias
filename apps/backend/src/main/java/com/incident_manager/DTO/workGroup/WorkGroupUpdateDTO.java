package com.incident_manager.DTO.workGroup;

public record WorkGroupUpdateDTO(
        String name,
        String description,
        Boolean active
) {}
