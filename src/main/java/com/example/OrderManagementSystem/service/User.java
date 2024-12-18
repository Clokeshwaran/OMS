package com.example.OrderManagementSystem.service;


import com.example.OrderManagementSystem.entity.SellerEntity;
import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.SellerEntityRepository;
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
    @Autowired
    SellerEntityRepository sellerEntityRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        SellerEntity sellerEntity;
        String username;
        String password;
        String role;

        UserEntity userEntity = userEntityRepository.findById(UUID.fromString(userId)).get();
        if(userEntity==null){
            sellerEntity = sellerEntityRepository.findById(UUID.fromString(userId)).get();
            username = sellerEntity.getEmail();
            password = sellerEntity.getPassword();
            role = sellerEntity.getRole().getRole();

        }else {
             username = userEntity.getEmail();
             password = userEntity.getPassword();
             role = userEntity.getRole().getRole();
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }
}