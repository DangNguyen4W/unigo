package com.unigo.dto.response;

import java.util.List;

public record UsersResponse(
        List<UserResponse> users) {
}
