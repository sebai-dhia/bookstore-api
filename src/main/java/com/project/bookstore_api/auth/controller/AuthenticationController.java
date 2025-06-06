package com.project.bookstore_api.auth.controller;

import org.springframework.http.ResponseEntity;

import com.project.bookstore_api.auth.dto.AuthenticationRequest;
import com.project.bookstore_api.auth.dto.AuthenticationResponse;
import com.project.bookstore_api.auth.dto.RegistrationRequest;

public interface AuthenticationController {
    ResponseEntity<AuthenticationResponse> register(RegistrationRequest request);
    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
