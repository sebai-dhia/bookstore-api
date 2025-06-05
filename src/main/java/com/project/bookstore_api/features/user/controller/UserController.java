package com.project.bookstore_api.features.user.controller;

import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Void> updateEmail(String email);
}
