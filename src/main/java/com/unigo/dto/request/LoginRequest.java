package com.unigo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank @Size(min = 8, max = 128) String password,
    @NotBlank @Size(max = 15) @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$") String phoneNumber) {

}
