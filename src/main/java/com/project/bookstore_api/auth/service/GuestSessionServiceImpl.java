package com.project.bookstore_api.auth.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.project.bookstore_api.auth.dto.AuthenticationResponse;
import com.project.bookstore_api.features.user.repository.GuestRepository;
import com.project.bookstore_api.features.user.model.Guest;
import com.project.bookstore_api.features.user.model.Role;
import com.project.bookstore_api.security.JwtService;

@Service
@RequiredArgsConstructor
public class GuestSessionServiceImpl implements GuestSessionService{

    private final GuestRepository guestRepository;
    private final JwtService jwtService;

    @Override
    public boolean sessionExist(UUID sessionId) {
        return guestRepository.findBySessionId(sessionId).isPresent();
    }

    @Override
    public AuthenticationResponse generateGuestToken(UUID sessionId) {
        Guest guest = guestRepository.findBySessionId(sessionId)
                .orElseGet(()->{
                            Guest newGuest = new Guest(Role.GUEST);
                            newGuest.setSessionId(sessionId);
                            return guestRepository.save(newGuest);
                        }
                );

        var token = jwtService.generateGuestToken(sessionId);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse createNewGuestWithToken(UUID sessionId) {
        Guest guest = new Guest(Role.GUEST);
        guest.setSessionId(sessionId);
        guestRepository.save(guest);
        var token  = jwtService.generateGuestToken(sessionId);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
