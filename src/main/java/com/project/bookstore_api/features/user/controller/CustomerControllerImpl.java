package com.project.bookstore_api.features.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore_api.features.user.service.CustomerService;

@RestController
@RequestMapping("customer")
@Tag(name = "Customer")
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController{

        final private CustomerService service;

        @PatchMapping("/address")
        @Override
        @Operation(summary = "Update Address")
        public ResponseEntity<Void> updateAddress(@RequestBody String address) {
            service.updateAddress(address);
            return ResponseEntity.noContent().build();
        }
}
