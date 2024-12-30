package com.example.OrderManagementSystem.service;

import com.example.OrderManagementSystem.dto.LoginDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public ResponseEntity<?> placeOrder(OrderDTO orderDTO);

    public ResponseEntity<?> orderHistory(OrdehistoryREQDTO ordehistoryREQDTO);

    public ResponseEntity<?> orderData(OrdehistoryREQDTO ordehistoryREQDTO);

    public ResponseEntity<?> login(LoginDTO loginDTO);


}
