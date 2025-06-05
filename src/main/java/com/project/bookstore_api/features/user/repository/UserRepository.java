package com.project.bookstore_api.features.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore_api.features.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :input OR u.username = :input")
    Optional<User> findByEmailOrUsername(@Param("input") String input);


}
