package com.project.bookstore_api.auth.controller;

import javax.naming.ServiceUnavailableException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore_api.auth.dto.AuthenticationResponse;
import com.project.bookstore_api.auth.service.GuestSessionService;

@RestController
@RequestMapping("/guest-token")
@RequiredArgsConstructor
@Slf4j
public class GuestSessionControllerImpl implements GuestSessionController {

    private final GuestSessionService service;

    @PostMapping(path = {"", "/"})
    @Override
    public ResponseEntity<AuthenticationResponse> generateGuestToken(
            @RequestHeader(value = "X-Session-Id", defaultValue = "") String sessionIdHeader) throws ServiceUnavailableException {

        UUID sessionId;
        AuthenticationResponse response;

        if (sessionIdHeader != null && !sessionIdHeader.isBlank()) {
            try {
                sessionId = UUID.fromString(sessionIdHeader);
            } catch (IllegalArgumentException  e) {
                log.error(String.valueOf(e));

                return ResponseEntity.badRequest()
                        .body(AuthenticationResponse.builder()
                                .token(null)
                                .message("Invalid X-Session-Id format")
                                .build());
            }
            if (!service.sessionExist(sessionId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(AuthenticationResponse.builder()
                                .token(null)
                                .message("Session not found or expired")
                                .build());
            }
            response = service.generateGuestToken(sessionId);
        }
        else {
            var attempts = 0;
            do {
                sessionId = UUID.randomUUID();
                if (++attempts > 3) {
                    throw new ServiceUnavailableException("Failed to generate unique session ID");
                }
            } while (service.sessionExist(sessionId));
            response = service.createNewGuestWithToken(sessionId);

        }

        return ResponseEntity.ok(response);
    }
}
