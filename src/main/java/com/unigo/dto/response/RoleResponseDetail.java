package com.unigo.dto.response;

import java.time.Instant;
import java.util.Set;

public record RoleResponseDetail(
    Integer id,
    String name,
    boolean isActive,
    Set<PermissionResponse> permissions,
    Instant createdAt,
    Instant updatedAt) {
}
