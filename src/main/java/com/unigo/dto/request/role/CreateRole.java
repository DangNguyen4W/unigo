package com.unigo.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateRole(
    @NotBlank(message = "Role name must not be blank") @Size(max = 50, message = "Role name must not exceed 50 characters") @Pattern(regexp = "^[A-Z][A-Z0-9]*(?:_[A-Z0-9]+)*$", message = "Role name must contain only uppercase letters, digits, and underscores") String name) {
}