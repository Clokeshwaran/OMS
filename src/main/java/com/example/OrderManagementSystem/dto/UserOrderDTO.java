package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserOrderDTO {

    private UUID orderId;
    private UUID productId;
    private String productName;
    private UUID userId;
    private String userName;
    private String quantity;
    private LocalDateTime orderDate;

}
