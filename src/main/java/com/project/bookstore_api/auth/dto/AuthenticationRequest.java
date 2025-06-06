package com.project.bookstore_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationRequest {

    @NotBlank
    @Email
    private String identifier;

    @NotBlank
    private String password;
}
