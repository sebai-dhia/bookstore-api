package com.project.bookstore_api.features.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookstore_api.features.user.repository.CustomerRepository;
import com.project.bookstore_api.features.user.model.Customer;
import com.project.bookstore_api.security.CustomUserDetails;
import com.project.bookstore_api.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public void updateAddress(String address) {
        final CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        Customer customer = customerRepository.findById(currentUser.user().getId())
                .orElseThrow(()-> new RuntimeException("Customer Not Found"));

        customer.setAddress(address);

    }
}
