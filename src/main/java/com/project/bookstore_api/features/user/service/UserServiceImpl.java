package com.project.bookstore_api.features.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookstore_api.features.user.model.User;
import com.project.bookstore_api.features.user.repository.UserRepository;
import com.project.bookstore_api.security.CustomUserDetails;
import com.project.bookstore_api.security.SecurityUtils;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final private UserRepository repository;

    @Transactional
    @Override
    public void updateEmail(String email) {
        final CustomUserDetails currentUser = SecurityUtils.getCurrentUser();
        User user = repository.findById(currentUser.user().getId()).orElseThrow(()-> new RuntimeException("User Not Fond"));
        user.setEmail(email);
    }
}
