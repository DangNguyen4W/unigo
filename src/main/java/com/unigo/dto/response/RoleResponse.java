package com.unigo.dto.response;

import java.time.Instant;

public record RoleResponse(
    Integer id,
    String name,
    boolean isActive,
    Instant createdAt,
    Instant updatedAt) {
}
