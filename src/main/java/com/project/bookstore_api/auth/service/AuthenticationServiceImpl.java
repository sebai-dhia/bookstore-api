package com.project.bookstore_api.auth.service;

import java.util.HashMap;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

import com.project.bookstore_api.security.JwtService;
import com.project.bookstore_api.security.CustomUserDetails;
import com.project.bookstore_api.features.user.repository.UserRepository;
import com.project.bookstore_api.features.user.model.*;
import com.project.bookstore_api.auth.dto.RegistrationRequest;
import com.project.bookstore_api.auth.dto.AuthenticationResponse;
import com.project.bookstore_api.auth.dto.AuthenticationRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getIdentifier(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var userDetails = ((CustomUserDetails) auth.getPrincipal());
        claims.put("fullName", userDetails.getFullName());

        var jwToken = jwtService.generateToken(claims, userDetails);

        return AuthenticationResponse.builder()
                .token(jwToken)
                .build();
    }

    @Override
    public AuthenticationResponse register(RegistrationRequest request) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.findByEmailOrUsername(request.email());

        if(existingUser.isPresent()){
            throw new UsernameNotFoundException("Email already Taken!");
        }

        var user = Member.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .birthday(request.birthday())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.MEMBER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(), new CustomUserDetails(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void registerAdmin(RegistrationRequest request) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.findByEmailOrUsername(request.email());

        if(existingUser.isPresent()){
            throw new UsernameNotFoundException("Email already Taken!");
        }

        var admin = Admin.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .birthday(request.birthday())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        var jwtToken = jwtService.generateToken(new HashMap<>(), new CustomUserDetails(admin));

        AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
