package com.unigo.dto.response;

public record FieldValidationError(
        String field,
        String code
) {
}
