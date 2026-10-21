package com.unigo.dto.response;

import java.time.Instant;

public record UserResponse(
    String id,
    String username,
    String email,
    String firstName,
    String lastName,
    String phoneNumber,
    RoleResponse role,
    boolean isActive,
    Instant createdAt,
    Instant updatedAt) {
}
