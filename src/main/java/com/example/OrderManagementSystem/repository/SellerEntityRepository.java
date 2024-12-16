package com.example.OrderManagementSystem.repository;

import com.example.OrderManagementSystem.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellerEntityRepository extends JpaRepository<SellerEntity, UUID> {
    SellerEntity findByEmail(String email);
}
