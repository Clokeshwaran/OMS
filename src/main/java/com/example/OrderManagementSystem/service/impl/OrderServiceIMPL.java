package com.example.OrderManagementSystem.service.impl;

import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.dto.ProductsListDTO;
import com.example.OrderManagementSystem.entity.OrderEntity;
import com.example.OrderManagementSystem.entity.ProductEntity;
import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.OrderEntityRepository;
import com.example.OrderManagementSystem.repository.ProductEntityRepository;
import com.example.OrderManagementSystem.repository.UserEntityRepository;
import com.example.OrderManagementSystem.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceIMPL implements OrderService {

    @Autowired
    OrderEntityRepository orderEntityRepository;
    @Autowired
    ProductEntityRepository productEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> placeOrder(OrderDTO orderDTO, String userId) {

        Optional<ProductEntity> productEntity = productEntityRepository.findById(orderDTO.getProductId());
        if(productEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product not available");
        }
        Optional<UserEntity> user = userEntityRepository.findById(UUID.fromString(userId));
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User not fount");
        }
        OrderEntity orderEntity = new OrderEntity();
        LocalDateTime currentDate = LocalDateTime.now();
        orderEntity.setOrderDate(currentDate);
        orderEntity.setProductId(productEntity.get());
        orderEntity.setUserId(user.get());
        orderEntity.setQuantity(orderDTO.getQuantity());
        orderEntity.setOrderId(UUID.randomUUID());

        orderEntityRepository.save(orderEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Order created successful");
    }

    @Override
    public ResponseEntity<?> orderHistory(String userId) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderEntityRepository.findAllByUserId_UserId(UUID.fromString(userId)).stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductsListDTO.class))
                .collect(Collectors.toList()));
    }
}
