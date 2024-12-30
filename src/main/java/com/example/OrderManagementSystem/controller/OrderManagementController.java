package com.example.OrderManagementSystem.controller;

import com.example.OrderManagementSystem.configuration.JwtTokenManager;
import com.example.OrderManagementSystem.controllerInterface.OrderManagementControllerInterface;
import com.example.OrderManagementSystem.dto.LoginDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderManagementController implements OrderManagementControllerInterface {
    @Autowired
    JwtTokenManager tokenManager;
    @Autowired
    OrderService orderService;

    @Override
    public ResponseEntity<?> PlaceOrder(OrderDTO orderDTO, String token) {
        return orderService.placeOrder(orderDTO);
    }

    @Override
    public ResponseEntity<?> orderHistory(OrdehistoryREQDTO ordehistoryREQDTO) {
        return orderService.orderHistory(ordehistoryREQDTO);
    }

    @Override
    public ResponseEntity<?> userOrderData(OrdehistoryREQDTO ordehistoryREQDTO) {
        return orderService.orderData(ordehistoryREQDTO);
    }

    @Override
    public ResponseEntity<?> newUserRegister(LoginDTO loginDTO) {
        return orderService.login(loginDTO);
    }

}
