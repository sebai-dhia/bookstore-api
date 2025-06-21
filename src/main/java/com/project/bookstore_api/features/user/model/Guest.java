package com.project.bookstore_api.features.user.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Guest extends Customer{
    @NotNull
    @Column(unique = true)
    private UUID sessionId;

    @PrePersist
    public void ensureRoleIsGuest() {
        this.setRole(Role.GUEST);
    }

    public Guest(Role role) {
        super(Role.GUEST);
        this.setEmail(null);
        this.setPassword(null);
    }
}
