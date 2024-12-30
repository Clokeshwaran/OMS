package com.example.OrderManagementSystem.ControllerTest;

import com.example.OrderManagementSystem.controller.SellerController;
import com.example.OrderManagementSystem.dto.OrdehistoryREQDTO;
import com.example.OrderManagementSystem.dto.ProductsListDTO;
import com.example.OrderManagementSystem.dto.SellerDataDTO;
import com.example.OrderManagementSystem.dto.SellerRegisterDTO;
import com.example.OrderManagementSystem.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SellerControllerTest {

    @Mock
    SellerService sellerService;
//    @Mock
//    private OrdehistoryREQDTO ordehistoryREQDTO;  // Mock the request DTO


    @InjectMocks
    SellerController sellerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sellerRegister() {
        SellerRegisterDTO sellerRegisterDTO = new SellerRegisterDTO();
        sellerRegisterDTO.setName("Test");
        sellerRegisterDTO.setEmail("Test@gmail.com");
        sellerRegisterDTO.setBusinessName("Testing");

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Seller Registered success.");
        Mockito.doReturn(expectedResponse)
                .when(sellerService)
                .sellerRegister(ArgumentMatchers.any(SellerRegisterDTO.class));

        ResponseEntity<?> actualResponse = sellerService.sellerRegister(sellerRegisterDTO);
        // Assert: Verify the response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getSellerTest() {
        UUID id = UUID.randomUUID();
        SellerDataDTO sellerDataDTO = new SellerDataDTO();
        sellerDataDTO.setId(id);

        Mockito.doReturn(sellerDataDTO).when(sellerService).sellerData(ArgumentMatchers.any(UUID.class));

        SellerDataDTO actualResponse = sellerService.sellerData(id);
        // Assert: Verify the response
        assertEquals(sellerDataDTO, actualResponse);
    }

    @Test
    void ListProductsTest() {
        UUID id = UUID.randomUUID();
        OrdehistoryREQDTO ordehistoryREQDTO = new OrdehistoryREQDTO();
        ordehistoryREQDTO.setId(id);
        ordehistoryREQDTO.setPage(0);
        ordehistoryREQDTO.setDirection("asc");
        ordehistoryREQDTO.setPageSize(10);

        ProductsListDTO product1 = new ProductsListDTO(UUID.randomUUID(), "test1", 98);
        ProductsListDTO product2 = new ProductsListDTO(UUID.randomUUID(), "test2", 100);
        List<ProductsListDTO> mockProductList = Arrays.asList(product1, product2);
        // Arrange
        // Mock the service method to return a list of products (or any expected response)
        when(sellerService.listProducts(ordehistoryREQDTO)).thenReturn(mockProductList);

        // Act
        List<?> response = sellerService.listProducts(ordehistoryREQDTO);

        // Assert: Verify the response status and body
        assertEquals(mockProductList, response);


    }
}
