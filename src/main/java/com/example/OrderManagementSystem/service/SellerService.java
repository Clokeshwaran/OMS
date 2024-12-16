package com.example.OrderManagementSystem.service;

import com.example.OrderManagementSystem.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SellerService {

    public ResponseEntity<?> sellerRegister(SellerRegisterDTO sellerRegisterDTO);

    public SellerDataDTO sellerData(UUID id);

    public List<ProductsListDTO> listProducts(OrdehistoryREQDTO ordehistoryREQDTO);

    public String sellerCreateProduct(CreateProductDTO createProductDTO);

}
