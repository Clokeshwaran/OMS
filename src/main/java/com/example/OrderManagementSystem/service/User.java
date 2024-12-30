package com.example.OrderManagementSystem.service;


import com.example.OrderManagementSystem.entity.SellerEntity;
import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.SellerEntityRepository;
import com.example.OrderManagementSystem.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class User implements UserDetailsService {

    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    SellerEntityRepository sellerEntityRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        String username;
        String password;
        String role;

        Optional<UserEntity> userEntity = userEntityRepository.findById(UUID.fromString(userId));
        if (userEntity.isEmpty()) {
            SellerEntity sellerEntity = sellerEntityRepository.findById(UUID.fromString(userId)).get();
            username = sellerEntity.getEmail();
            password = sellerEntity.getPassword();
            role = sellerEntity.getRole().getRole();

        } else {
            username = userEntity.get().getEmail();
            password = userEntity.get().getPassword();
            role = userEntity.get().getRole().getRole();
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }
}