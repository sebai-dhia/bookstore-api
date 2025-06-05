package com.project.bookstore_api.auth.service;

import java.util.UUID;

import com.project.bookstore_api.auth.dto.AuthenticationResponse;

public interface GuestSessionService {
    boolean sessionExist(UUID sessionId);
    AuthenticationResponse generateGuestToken(UUID sessionId);
    AuthenticationResponse createNewGuestWithToken(UUID sessionId);
}
