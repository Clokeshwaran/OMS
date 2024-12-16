package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SellerDataDTO {
    private UUID id;
    private String name;
    private String email;
    private String businessName;
}
