package com.project.bookstore_api.auth.controller;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.ResponseEntity;

import com.project.bookstore_api.auth.dto.AuthenticationResponse;


public interface GuestSessionController {
    ResponseEntity<AuthenticationResponse> generateGuestToken(String sessionIdHeader) throws ServiceUnavailableException;
}
