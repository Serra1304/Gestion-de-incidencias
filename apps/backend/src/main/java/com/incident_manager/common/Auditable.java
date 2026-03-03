package com.incident_manager.common;

import java.time.LocalDateTime;

public interface Auditable {

    void setCreatedAt(LocalDateTime time);
    void setUpdatedAt(LocalDateTime time);
}
