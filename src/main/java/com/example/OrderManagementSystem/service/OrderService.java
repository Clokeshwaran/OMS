package com.example.OrderManagementSystem.service;

import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.dto.OrderHistoryDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public ResponseEntity<?> placeOrder(OrderDTO orderDTO);
    public ResponseEntity<?> orderHistory(OrdehistoryREQDTO ordehistoryREQDTO);
}
