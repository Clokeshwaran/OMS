package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductsListDTO {
    private UUID id;
    private String name;
    private int price;
}
