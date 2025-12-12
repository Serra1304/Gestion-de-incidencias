package com.incident_manager.service.data;

import com.incident_manager.entity.AuthUser;
import com.incident_manager.entity.WorkGroup;

import java.util.List;
import java.util.Set;

public record WorkGroupFullData(
        WorkGroup group,
        Set<AuthUser> groupUsers,
        List<AuthUser> availableUsers
) { }
