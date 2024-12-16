package com.example.OrderManagementSystem.controllerInterface;

import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.dto.OrderHistoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface OrderManagementControllerInterface {
    @PostMapping("/new-order")
    ResponseEntity<?> PlaceOrder(@RequestBody OrderDTO orderDTO, @RequestHeader(value = "Authorization") String token);

    @PostMapping("/order-history/{id}")
    ResponseEntity<?> orderHistory(@RequestBody OrdehistoryREQDTO ordehistoryREQDTO);//@RequestHeader(value = "Authorization") String token);
//
//    @PostMapping("/New-User")
//    ResponseEntity<?> newUserRegister(@RequestBody NewUserDTO newUserDTO);

}
