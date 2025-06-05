package com.project.bookstore_api.features.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore_api.features.user.model.Guest;

public interface GuestRepository extends JpaRepository<Guest,Long> {
    Optional<Guest> findBySessionId(UUID sessionId);
}
