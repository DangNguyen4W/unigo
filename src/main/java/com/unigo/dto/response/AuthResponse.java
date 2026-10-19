package com.unigo.dto.response;

public record AuthResponse(
        UserResponse user,
        String accessToken) {
}
