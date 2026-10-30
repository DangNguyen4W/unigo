package com.unigo.dto.response;

import java.math.BigInteger;
import java.time.Instant;

public record PermissionResponse(
    BigInteger id,
    String code,
    boolean isActive,
    Instant createdAt,
    Instant updatedAt) {

}
