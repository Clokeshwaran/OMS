package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDTO {
    private UUID userId;
    private UUID productId;
    private UUID sellerId;
    private int quantity;
}
