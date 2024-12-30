package com.example.OrderManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID productId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private double price;

    @ManyToOne(targetEntity = SellerEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "SELLER_ID", referencedColumnName = "ID")
    private SellerEntity sellerId;

}
