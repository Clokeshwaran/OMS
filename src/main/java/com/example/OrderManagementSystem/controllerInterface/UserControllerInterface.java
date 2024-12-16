package com.example.OrderManagementSystem.controllerInterface;

import com.example.OrderManagementSystem.dto.NewUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerInterface {

    @PostMapping("/New-User")
    ResponseEntity<?> newUserRegister(@RequestBody NewUserDTO newUserDTO);

    @GetMapping("/Get-user/{id}")
    ResponseEntity<?> getUser(@PathVariable String id);


}
