package com.project.bookstore_api.features.user.controller;

import org.springframework.http.ResponseEntity;

public interface CustomerController {
    ResponseEntity<Void> updateAddress(String address);
}
