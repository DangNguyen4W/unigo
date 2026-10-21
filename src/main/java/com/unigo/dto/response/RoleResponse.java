package com.unigo.dto.response;

import java.time.Instant;

public record RoleResponse(
    Integer id,
    String name,
    Instant createdAt,
    Instant updatedAt) {
}
