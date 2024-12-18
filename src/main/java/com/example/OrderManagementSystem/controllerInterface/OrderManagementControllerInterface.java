package com.example.OrderManagementSystem.controllerInterface;

import com.example.OrderManagementSystem.dto.LoginDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface OrderManagementControllerInterface {
    @PostMapping("/new-order")
    ResponseEntity<?> PlaceOrder(@RequestBody OrderDTO orderDTO, @RequestHeader(value = "Authorization") String token);

    @PostMapping("/order-history/{id}")
    ResponseEntity<?> orderHistory(@RequestBody OrdehistoryREQDTO ordehistoryREQDTO);//@RequestHeader(value = "Authorization") String token);

    @PostMapping("/user-order-data")
    public ResponseEntity<?> userOrderData(@RequestBody OrdehistoryREQDTO ordehistoryREQDTO);

    @PostMapping("/login")
    ResponseEntity<?> newUserRegister(@RequestBody LoginDTO loginDTO);

}
