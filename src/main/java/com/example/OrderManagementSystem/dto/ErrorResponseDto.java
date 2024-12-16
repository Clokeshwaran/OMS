package com.example.OrderManagementSystem.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private String status="error";
    private String message;
}
