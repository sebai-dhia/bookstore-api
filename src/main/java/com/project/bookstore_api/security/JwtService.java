package com.project.bookstore_api.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateGuestToken(UUID sessionId);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String extractSubject(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Claims extractAllClaims(String token);
    String buildToken(Map<String, Object> extraClaims, String subject, long expiration);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    Key getSignInKey();
}
