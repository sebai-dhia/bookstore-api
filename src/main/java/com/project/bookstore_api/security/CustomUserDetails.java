package com.project.bookstore_api.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.bookstore_api.features.user.model.User;
import com.project.bookstore_api.features.user.model.Guest;


public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword() != null ? user.getPassword() : "";
    }

    @Override
    public String getUsername() {
        if (user instanceof Guest guest) {
            return "guest:" + guest.getSessionId();
        }
        return user.getUsername();
    }

    public boolean isGuest() {
        return user instanceof Guest;
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}
