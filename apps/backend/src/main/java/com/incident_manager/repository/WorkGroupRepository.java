package com.incident_manager.repository;

import com.incident_manager.entity.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkGroupRepository extends JpaRepository<WorkGroup, UUID> {
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
