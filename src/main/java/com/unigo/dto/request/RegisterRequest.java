package com.unigo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "VALIDATION.USERNAME.REQUIRED")
        @Size(min = 3, max = 50, message = "VALIDATION.USERNAME.FROM_3_TO_50")
        @Pattern(
                regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9._]{1,48}[a-zA-Z0-9])?$",
                message = "VALIDATION.USERNAME.INVALID"
        )
        String username,

        @NotBlank(message = "VALIDATION.EMAIL.REQUIRED")
        @Size(max = 100, message = "VALIDATION.EMAIL.MAX_100")
        @Email(message = "VALIDATION.EMAIL.INVALID")
        String email,

        @NotBlank(message = "VALIDATION.PASSWORD.REQUIRED")
        @Size(min = 8, max = 128, message = "VALIDATION.PASSWORD.FROM_8_TO_128")
        String password,

        @Size(max = 50, message = "VALIDATION.FIRST_NAME.MAX_50")
        String firstName,

        @Size(max = 50, message = "VALIDATION.LAST_NAME.MAX_50")
        String lastName,

        @Pattern(
                regexp = "^\\+?[1-9][0-9]{7,14}$",
                message = "VALIDATION.PHONE_NUMBER.INVALID"
        )
        String phoneNumber
) {
}
