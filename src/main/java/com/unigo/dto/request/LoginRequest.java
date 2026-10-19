package com.unigo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
                @NotBlank @Size(min = 3, max = 50) @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9._]{1,48}[a-zA-Z0-9])?$") String username,
                @NotBlank @Size(max = 100) @Email String email,
                @NotBlank @Size(min = 8, max = 128) String password,
                @Size(max = 50) String phoneNumber) {

}
