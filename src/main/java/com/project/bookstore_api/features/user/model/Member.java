package com.project.bookstore_api.features.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@Entity
public class Member extends Customer{
    private int loyaltyPoints;

    @PrePersist
    public void ensureRoleIsMember() {
        this.setRole(Role.MEMBER);
    }

    public Member(){
        super(Role.MEMBER);
    }
}
