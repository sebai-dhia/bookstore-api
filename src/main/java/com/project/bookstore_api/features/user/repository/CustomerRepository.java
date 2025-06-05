package com.project.bookstore_api.features.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore_api.features.user.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
