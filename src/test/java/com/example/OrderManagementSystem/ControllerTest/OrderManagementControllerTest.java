package com.example.OrderManagementSystem.ControllerTest;

import com.example.OrderManagementSystem.configuration.JwtTokenManager;
import com.example.OrderManagementSystem.controller.OrderManagementController;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.OrderDTO;
import com.example.OrderManagementSystem.dto.SellerRegisterDTO;
import com.example.OrderManagementSystem.service.OrderService;
import com.example.OrderManagementSystem.service.impl.OrderServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderManagementControllerTest {

    @Mock
    JwtTokenManager tokenManager;
    @Mock
    OrderServiceIMPL orderService;

    @InjectMocks
    OrderManagementController orderManagementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void PlaceOrderTest() {
        // Arrange: Set up test data
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(UUID.randomUUID());
        orderDTO.setQuantity(10);
        orderDTO.setProductId(UUID.randomUUID());
        orderDTO.setSellerId(UUID.randomUUID());

        // Expected response
        ResponseEntity<?> expectedResponse = ResponseEntity.ok("Order created successful");

        Mockito.doReturn(expectedResponse)
                .when(orderService)
                .placeOrder(ArgumentMatchers.any(OrderDTO.class));

        // Act: Call the method under test
        ResponseEntity<?> actualResponse = orderService.placeOrder(orderDTO);

        // Assert: Verify the behavior and response
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode()); // Check status code

    }

    @Test
    void userOrderDataTest(){

        OrdehistoryREQDTO ordehistoryREQDTO = new OrdehistoryREQDTO();
        ordehistoryREQDTO.setPageSize(10);
        ordehistoryREQDTO.setDirection("asc");
        ordehistoryREQDTO.setId(UUID.randomUUID());
        ordehistoryREQDTO.setPage(0);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok("Order History");

        Mockito.doReturn(expectedResponse)
                .when(orderService)
                .orderData(ArgumentMatchers.any(OrdehistoryREQDTO.class));

        // Act: Call the method under test
        ResponseEntity<?> actualResponse = orderService.orderData(ordehistoryREQDTO);

        // Assert: Verify the behavior and response
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode()); // Check status code


    }


}
