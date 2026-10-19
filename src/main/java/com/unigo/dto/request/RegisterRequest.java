package com.unigo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
                @NotBlank @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$") String phoneNumber,
                @NotBlank @Size(min = 8, max = 128) String password,
                @Size(min = 3, max = 50) @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9._]{1,48}[a-zA-Z0-9])?$") String username,
                @Size(max = 100) @Email String email,
                @Size(max = 50) String firstName,
                @Size(max = 50) String lastName) {

}
