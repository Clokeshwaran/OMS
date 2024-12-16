package com.example.OrderManagementSystem.dto;

import com.example.OrderManagementSystem.entity.ProductEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderHistoryDTO {
    private UUID orderId;
    //    private UserEntity userId;
    private ProductEntity productId;
    private String quantity;
    private String orderDate;


}
