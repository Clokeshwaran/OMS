package com.example.OrderManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class SellerEntity {

    @Id
//    @GeneratedValue(generator = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID sellerId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "BUSINESS_NAME")
    private String businessName;
}
