package com.project.bookstore_api.features.payment;

import com.project.bookstore_api.features.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
