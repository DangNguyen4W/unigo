package com.unigo.dto.response;

import java.util.List;

public record ApiErrorResponse(
        int status,
        String code,
        List<FieldValidationError> errors
) {
}
