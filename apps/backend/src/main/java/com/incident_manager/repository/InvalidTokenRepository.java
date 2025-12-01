package com.incident_manager.repository;

import com.incident_manager.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, UUID> {
    boolean existsByToken(String token);
}

