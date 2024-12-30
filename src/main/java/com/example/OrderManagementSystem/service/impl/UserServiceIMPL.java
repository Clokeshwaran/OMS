package com.example.OrderManagementSystem.service.impl;

import com.example.OrderManagementSystem.constant.Constant;
import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.UserDataDTO;
import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.RoleRepository;
import com.example.OrderManagementSystem.repository.UserEntityRepository;
import com.example.OrderManagementSystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> userRegister(NewUserDTO newUserDTO) {

        Pattern pattern = Pattern.compile(Constant.REGEX);
        Matcher matcher = pattern.matcher(newUserDTO.getEmail());

        if (!matcher.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please enter valid user name");
        }
        UserEntity user = userEntityRepository.findByEmail(newUserDTO.getEmail());

        if (user == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(newUserDTO.getName());
            userEntity.setEmail(newUserDTO.getEmail());
            userEntity.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
//            String hashedPassword = passwordEncoder.encode(plainPassword);
            userEntity.setUserId(UUID.randomUUID());
            userEntity.setRole(roleRepository.findById(1).get());
            userEntityRepository.save(userEntity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User Registered success.");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User is already exists");
    }

    @Override
    public ResponseEntity<?> userData(UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(userEntityRepository.findById(id),
                UserDataDTO.class));
    }
}
