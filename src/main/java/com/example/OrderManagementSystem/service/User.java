package com.example.OrderManagementSystem.service;


import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class User implements UserDetailsService {

//    private final UserRepoServiceIMPL userRepoServiceIMPL

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findById(UUID.fromString(userId)).get();

        String username = userEntity.getName();
        String password = userEntity.getPassword();
        String role = "Admin";
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }
}