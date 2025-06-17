package com.project.bookstore_api.auth.controller;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.bookstore_api.auth.dto.AuthenticationRequest;
import com.project.bookstore_api.auth.dto.RegistrationRequest;
import com.project.bookstore_api.auth.service.AuthenticationService;
import com.project.bookstore_api.auth.dto.AuthenticationResponse;
import com.project.bookstore_api.auth.service.GuestSessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(name = "\u200CAuth", description = "Authentication")
public class AuthenticationControllerImpl implements AuthenticationController {
    private final AuthenticationService autService;
    private final GuestSessionService guestSessionService;

    @PostMapping("/register")
    @Override
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegistrationRequest request) {
        AuthenticationResponse response = autService.register(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = autService.authenticate(request);

        return ResponseEntity.ok(response);
    }
}
