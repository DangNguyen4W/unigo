package com.unigo.dto.response;

import java.util.List;

public record PermissionsResponse(
    List<PermissionResponse> permissions) {
}
