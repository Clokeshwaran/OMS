package com.example.OrderManagementSystem.repository;

import com.example.OrderManagementSystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRpository extends JpaRepository<Role, Integer> {
}

