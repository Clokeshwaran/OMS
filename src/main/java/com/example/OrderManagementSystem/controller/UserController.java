package com.example.OrderManagementSystem.controller;

import com.example.OrderManagementSystem.controllerInterface.UserControllerInterface;
import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements UserControllerInterface {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<?> newUserRegister(NewUserDTO newUserDTO) {
        return userService.userRegister(newUserDTO);
    }

    @Override
    public ResponseEntity<?> getUser(String id) {
        return userService.userData(UUID.fromString(id));
    }
}
