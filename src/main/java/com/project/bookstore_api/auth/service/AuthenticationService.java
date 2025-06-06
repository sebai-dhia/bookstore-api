package com.project.bookstore_api.auth.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.bookstore_api.auth.dto.AuthenticationRequest;
import com.project.bookstore_api.auth.dto.AuthenticationResponse;
import com.project.bookstore_api.auth.dto.RegistrationRequest;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(RegistrationRequest request) throws UsernameNotFoundException;
}
