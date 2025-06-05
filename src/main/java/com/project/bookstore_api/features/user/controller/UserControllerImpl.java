package com.project.bookstore_api.features.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore_api.features.user.service.UserService;


@RestController
@RequestMapping("user")
@Service
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    final UserService service;

    @PatchMapping("/email")
    @Override
    public ResponseEntity<Void> updateEmail(@RequestBody String email) {
        service.updateEmail(email);
        return ResponseEntity.noContent().build();

    }
}
