package com.example.OrderManagementSystem.service.impl;

import com.example.OrderManagementSystem.constant.Constant;
import com.example.OrderManagementSystem.dto.*;
import com.example.OrderManagementSystem.entity.ProductEntity;
import com.example.OrderManagementSystem.entity.SellerEntity;
import com.example.OrderManagementSystem.repository.ProductEntityRepository;
import com.example.OrderManagementSystem.repository.SellerEntityRepository;
import com.example.OrderManagementSystem.service.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SellerServiceIMPL implements SellerService {

    @Autowired
    SellerEntityRepository sellerEntityRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductEntityRepository productEntityRepository;

    @Override
    public ResponseEntity<?> sellerRegister(SellerRegisterDTO sellerRegisterDTO) {

        Pattern pattern = Pattern.compile(Constant.REGEX);
        Matcher matcher = pattern.matcher(sellerRegisterDTO.getEmail());

        if (!matcher.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please enter valid user name");
        }
        SellerEntity seller = sellerEntityRepository.findByEmail(sellerRegisterDTO.getEmail());

        if(seller==null) {
            SellerEntity sellerEntity = modelMapper.map(sellerRegisterDTO, SellerEntity.class);
            sellerEntity.setSellerId(UUID.randomUUID());
            sellerEntityRepository.save(sellerEntity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Seller Registered success.");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Seller is already exists");
    }

    @Override
    public SellerDataDTO sellerData(UUID id) {
        return modelMapper.map(sellerEntityRepository.findById(id), SellerDataDTO.class);
    }

    @Override
    public List<ProductsListDTO> listProducts(OrdehistoryREQDTO ordehistoryREQDTO) {
//        List<ProductsListDTO> productsListDTOList = productEntityRepository.findAllBySellerId_id(id).stream()
//                .map(productEntity -> modelMapper.map(productEntity, ProductsListDTO.class))
//                .collect(Collectors.toList())


        return productEntityRepository.findAllBySellerId_SellerId(ordehistoryREQDTO.getUserId()).stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductsListDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String sellerCreateProduct(CreateProductDTO createProductDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(UUID.randomUUID());
        productEntity.setName(createProductDTO.getName());
        productEntity.setPrice(createProductDTO.getPrice());
        productEntity.setSellerId(sellerEntityRepository.findById(createProductDTO.getSellerId()).get());

        productEntityRepository.save(productEntity);
        return "Product created success.";
    }
}
