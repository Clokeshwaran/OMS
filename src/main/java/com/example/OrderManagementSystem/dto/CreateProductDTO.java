package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateProductDTO {
    private String name;
    private double price;
    private UUID sellerId;
}
