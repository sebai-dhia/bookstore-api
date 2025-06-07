package com.project.bookstore_api.security;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.bookstore_api.features.user.repository.UserRepository;
import com.project.bookstore_api.features.user.repository.GuestRepository;
import com.project.bookstore_api.features.user.model.Guest;
import com.project.bookstore_api.features.user.model.Role;
import com.project.bookstore_api.features.user.model.User;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final GuestRepository guestRepository;


    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        if (input.startsWith("guest:")) {
            UUID sessionId = UUID.fromString(input.substring(6));
            Guest guest = guestRepository.findBySessionId(sessionId)
                    .orElseGet(() -> {
                         var newGuest = new Guest(Role.GUEST);
                         newGuest.setSessionId(sessionId);
                         newGuest.setUsername(input);
                        return newGuest;
                    });
            return new CustomUserDetails(guest);
        } else {
            User user = repository.findByEmailOrUsername(input)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetails(user);
        }
    }
}
