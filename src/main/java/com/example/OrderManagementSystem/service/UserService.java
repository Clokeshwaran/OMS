package com.example.OrderManagementSystem.service;

import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.UserDataDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {

    public ResponseEntity<?> userRegister(NewUserDTO newUserDTO);
    public UserDataDTO userData(UUID id);

}
