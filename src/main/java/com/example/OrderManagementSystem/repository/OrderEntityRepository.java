package com.example.OrderManagementSystem.repository;

import com.example.OrderManagementSystem.entity.OrderEntity;
import com.example.OrderManagementSystem.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderEntityRepository  extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findAllByUserId_UserId(UUID id);

}