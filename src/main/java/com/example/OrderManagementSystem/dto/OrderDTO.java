package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDTO {
    private UUID productId;
    private int quantity;
}
