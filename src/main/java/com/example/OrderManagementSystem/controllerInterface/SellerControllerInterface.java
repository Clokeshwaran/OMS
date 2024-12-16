package com.example.OrderManagementSystem.controllerInterface;

import com.example.OrderManagementSystem.dto.CreateProductDTO;
import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.SellerRegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface SellerControllerInterface {

    @PostMapping("/new-seller")
    ResponseEntity<?> sellerRegister(@RequestBody SellerRegisterDTO sellerRegisterDTO);

    @GetMapping("/Get-seller/{id}")
    ResponseEntity<?> getSeller(@PathVariable UUID id);

    @PostMapping("/Get-list-products/{id}")
    ResponseEntity<?> ListProducts(@RequestBody OrdehistoryREQDTO ordehistoryREQDTO);

    @PostMapping("/create-product")
    ResponseEntity<?> sellerCreateProduct(@RequestBody CreateProductDTO createProductDTO);


}
