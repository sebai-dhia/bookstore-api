package com.project.bookstore_api.config;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.bookstore_api.auth.dto.RegistrationRequest;
import com.project.bookstore_api.auth.service.AuthenticationServiceImpl;
import com.project.bookstore_api.features.user.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BeansConfig {
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("!prod")
    CommandLineRunner initAdmin (
            AuthenticationServiceImpl authService,
            @Value("${app.admin.email}") String adminEmail,
            @Value("${app.admin.password}") String adminPassword) {
        return args -> {
            try {
                if (userRepository.findByEmailOrUsername(adminEmail).isEmpty()) {
                    RegistrationRequest adminRequest = new RegistrationRequest(
                            "dhia",
                            "sebai",
                            adminEmail,
                            LocalDate.parse("1998-10-02"),
                            adminPassword
                    );
                    authService.registerAdmin(adminRequest);
                    log.info("Admin registered successfully.");

                }
            }  catch (Exception e) {
            log.error("Failed to register admin: ",e);
        }
        };
    }

}
