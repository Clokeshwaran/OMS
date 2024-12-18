package com.example.OrderManagementSystem.controller;

import com.example.OrderManagementSystem.controllerInterface.SellerControllerInterface;
import com.example.OrderManagementSystem.dto.CreateProductDTO;
import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.SellerRegisterDTO;
import com.example.OrderManagementSystem.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SellerController implements SellerControllerInterface {

    @Autowired
    SellerService sellerService;

    @GetMapping("/get")
    public String Test(){
        return "Ok";
    }

    @Override
    public ResponseEntity<?> sellerRegister(SellerRegisterDTO sellerRegisterDTO) {
        return sellerService.sellerRegister(sellerRegisterDTO);
    }

    @Override
    public ResponseEntity<?> getSeller(UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sellerService.sellerData(id));
    }

    @Override
    public ResponseEntity<?> ListProducts(OrdehistoryREQDTO ordehistoryREQDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sellerService.listProducts(ordehistoryREQDTO));
    }

    @Override
    public ResponseEntity<?> sellerCreateProduct(CreateProductDTO createProductDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sellerService.sellerCreateProduct(createProductDTO));
    }
}
