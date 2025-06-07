package com.project.bookstore_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static CustomUserDetails getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth!=null && auth.getPrincipal() instanceof CustomUserDetails userDetails){
            return userDetails;
        }
        throw new RuntimeException("No authenticated User");
    }
}
