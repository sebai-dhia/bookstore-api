package com.project.bookstore_api.auth.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegistrationRequest(
        @NotBlank @Size(max = 50) String firstName,
        @NotBlank @Size(max = 50) String lastName,
        @Email @NotNull @Size(max = 255) String email,
        LocalDate birthday,
        @NotBlank @Size(min = 8, max = 100)
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,}$",
                message = "Password must contain 1 uppercase, 1 lowercase, 1 digit, and 1 special character"
        ) String password
) {}