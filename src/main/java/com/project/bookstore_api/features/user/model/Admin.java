package com.project.bookstore_api.features.user.model;

import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Admin extends User{
    public Admin(){
        super(Role.ADMIN);
    }
}
