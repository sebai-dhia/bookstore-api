package com.project.bookstore_api.features.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore_api.features.user.service.CustomerService;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController{

        final private CustomerService service;

        @PatchMapping("/address")
        @Override
        public ResponseEntity<Void> updateAddress(@RequestBody String address) {
            service.updateAddress(address);
            return ResponseEntity.noContent().build();
        }
}
