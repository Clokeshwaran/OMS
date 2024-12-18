package com.example.OrderManagementSystem.service.impl;

import com.example.OrderManagementSystem.configuration.JwtTokenManager;
import com.example.OrderManagementSystem.dto.*;
import com.example.OrderManagementSystem.entity.OrderEntity;
import com.example.OrderManagementSystem.entity.ProductEntity;
import com.example.OrderManagementSystem.entity.SellerEntity;
import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.OrderEntityRepository;
import com.example.OrderManagementSystem.repository.ProductEntityRepository;
import com.example.OrderManagementSystem.repository.SellerEntityRepository;
import com.example.OrderManagementSystem.repository.UserEntityRepository;
import com.example.OrderManagementSystem.service.OrderService;
import com.example.OrderManagementSystem.service.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceIMPL implements OrderService {

    @Autowired
    OrderEntityRepository orderEntityRepository;
    @Autowired
    ProductEntityRepository productEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    SellerEntityRepository sellerEntityRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    User user;
    @Autowired
    JwtTokenManager jwtTokenManager;

    @Override
    public ResponseEntity<?> placeOrder(OrderDTO orderDTO) {

        Optional<ProductEntity> productEntity = productEntityRepository.findById(orderDTO.getProductId());
        if(productEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product not available");
        }
        Optional<UserEntity> user = userEntityRepository.findById(orderDTO.getUserId());
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User not fount");
        }
        Optional<SellerEntity> seller = sellerEntityRepository.findById(orderDTO.getSellerId());
        if(seller.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("seller not fount");
        }

        OrderEntity orderEntity = new OrderEntity();
        LocalDateTime currentDate = LocalDateTime.now();
        orderEntity.setOrderDate(currentDate);
        orderEntity.setProductId(productEntity.get());
        orderEntity.setUserId(user.get());
        orderEntity.setQuantity(orderDTO.getQuantity());
        orderEntity.setSellerId(seller.get());
        orderEntity.setOrderId(UUID.randomUUID());

        orderEntityRepository.save(orderEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Order created successful");
    }

    @Override
    public ResponseEntity<?> orderHistory(OrdehistoryREQDTO ordehistoryREQDTO) {

        Sort.Direction direction = Sort.Direction.DESC;
        if(Objects.equals(ordehistoryREQDTO.getDirection(), "asc"))
            direction = Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "orderDate");

        Pageable pageable = PageRequest.of(ordehistoryREQDTO.getPage(), ordehistoryREQDTO.getPageSize(), sort);


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderEntityRepository.findAllByUserId_UserId(ordehistoryREQDTO.getId(), pageable).stream()
                .map(productEntity -> {
                    ProductsListDTO productsListDTO = new ProductsListDTO();//modelMapper.map(productEntity, ProductsListDTO.class);
                    productsListDTO.setName(productEntity.getProductId().getName()); // Set product name
                    productsListDTO.setId(productEntity.getProductId().getProductId());       // Set user name
                    productsListDTO.setPrice(productEntity.getProductId().getPrice());
                    return productsListDTO;
                })
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> orderData(OrdehistoryREQDTO ordehistoryREQDTO) {
        Sort.Direction direction = Sort.Direction.DESC;
        if(Objects.equals(ordehistoryREQDTO.getDirection(), "asc"))
            direction = Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "orderDate");
        Pageable pageable = PageRequest.of(ordehistoryREQDTO.getPage(), ordehistoryREQDTO.getPageSize(), sort);


        return ResponseEntity.status(HttpStatus.OK).body(orderEntityRepository
                .findAllBySellerId_SellerId(ordehistoryREQDTO.getId(), pageable)
                .stream()
                .map(productEntity -> {
                    UserOrderDTO dto = modelMapper.map(productEntity, UserOrderDTO.class);
                    dto.setProductName(productEntity.getProductId().getName()); // Set product name
                    dto.setUserName(productEntity.getUserId().getName());       // Set user name
                    dto.setProductId(productEntity.getProductId().getProductId()); // Set product name
                    dto.setUserId(productEntity.getUserId().getUserId());
                    return dto;
                })
                .collect(Collectors.toList()));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderEntityRepository.findAllBySellerId_SellerId(ordehistoryREQDTO.getId(), pageable).stream()
//                .map(productEntity -> modelMapper.map(productEntity, UserOrderDTO.class))
//                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {

        SellerEntity sellerEntity;
        UUID id;

        UserEntity userEntity = userEntityRepository.findByEmail(loginDTO.getEmail());
        if(userEntity==null){
            sellerEntity = sellerEntityRepository.findByEmail(loginDTO.getEmail());
            if(!Objects.equals(sellerEntity.getEmail(), loginDTO.getEmail()) && !Objects.equals(sellerEntity.getPassword(), loginDTO.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid user credentials");
            }
            id = sellerEntity.getSellerId();

        }else {
            if(!Objects.equals(userEntity.getEmail(), loginDTO.getEmail()) && !Objects.equals(userEntity.getPassword(), loginDTO.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid user credentials");
            }
            id = userEntity.getUserId();
        }

        TockenDTO tockenDTO = new TockenDTO();
        tockenDTO.setAccessToken(jwtTokenManager.generateJwtToken(user.loadUserByUsername(id.toString())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid user credentials");
    }
}
