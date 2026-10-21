package com.unigo.exception;

import com.unigo.dto.response.ApiErrorResponse;
import com.unigo.dto.response.FieldValidationError;
import com.unigo.validation.ValidationCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
            BadCredentialsException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_CREDENTIALS",
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException exception
    ) {
        List<FieldValidationError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldValidationError)
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "VALIDATION_ERROR",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(response);
    }

    private FieldValidationError toFieldValidationError(FieldError error) {
        return new FieldValidationError(
                error.getField(),
                resolveCode(error)
        );
    }

    private String resolveCode(FieldError error) {
        String field = error.getField();

        return switch (error.getCode()) {
            case "NotBlank", "NotNull", "NotEmpty" ->
                    ValidationCode.required(field);
            case "Email", "Pattern" ->
                    ValidationCode.invalid(field);
            case "Size" -> resolveSizeCode(error);
            default -> ValidationCode.invalid(field);
        };
    }

    private String resolveSizeCode(FieldError error) {
        List<Integer> limits = Arrays.stream(error.getArguments() == null
                        ? new Object[0]
                        : error.getArguments())
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .toList();

        int min = limits.size() > 0 ? limits.get(0) : 0;
        int max = limits.size() > 1 ? limits.get(1) : Integer.MAX_VALUE;

        if (min > 0 && max < Integer.MAX_VALUE) {
            return ValidationCode.fromTo(error.getField(), min, max);
        }

        if (min > 0) {
            return ValidationCode.min(error.getField(), min);
        }

        return ValidationCode.max(error.getField(), max);
    }
}
