package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrdehistoryREQDTO {
    private UUID userId;
    private int page;
    private int pageSize;
    private String direction;
}
