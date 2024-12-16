package com.example.OrderManagementSystem.controllerInterface;

import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.dto.OrderHistoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface OrderManagementControllerInterface {
    @PostMapping("/new-order")
    ResponseEntity<?> PlaceOrder(@RequestBody OrderDTO orderDTO, @RequestHeader(value = "Authorization") String token);

    @GetMapping("/order-history")
    ResponseEntity<?> orderHistory(@RequestHeader(value = "Authorization") String token);
//
//    @PostMapping("/New-User")
//    ResponseEntity<?> newUserRegister(@RequestBody NewUserDTO newUserDTO);

}
