package com.example.OrderManagementSystem.controller;

import com.example.OrderManagementSystem.configuration.JwtTokenManager;
import com.example.OrderManagementSystem.controllerInterface.OrderManagementControllerInterface;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class OrderManagementController implements OrderManagementControllerInterface {
    @Autowired
    JwtTokenManager tokenManager;
    @Autowired
    OrderService orderService;

    @Override
    public ResponseEntity<?> PlaceOrder(OrderDTO orderDTO, String token) {
//        String userId = tokenManager.getUsernameFromToken(token);
        return orderService.placeOrder(orderDTO);
    }

    @Override
    public ResponseEntity<?> orderHistory(OrdehistoryREQDTO ordehistoryREQDTO) {
//        String userId = tokenManager.getUsernameFromToken(token);
        return orderService.orderHistory(ordehistoryREQDTO);
    }
}
