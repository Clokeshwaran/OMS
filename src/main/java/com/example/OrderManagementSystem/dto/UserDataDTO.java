package com.example.OrderManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDataDTO {
    private UUID id;
    private String name;
    private String email;
}
