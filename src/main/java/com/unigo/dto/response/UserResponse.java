package com.unigo.dto.response;

import com.unigo.entity.Role;

public record UserResponse(
    String id,
    String username,
    String email,
    String firstName,
    String lastName,
    String phoneNumber,
    Role role,
    Boolean isActive,
    String createdAt,
    String updatedAt) {
}
