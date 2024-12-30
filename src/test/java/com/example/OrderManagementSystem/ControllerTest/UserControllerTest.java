package com.example.OrderManagementSystem.ControllerTest;

import com.example.OrderManagementSystem.controller.UserController;
import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.UserDataDTO;
import com.example.OrderManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void newUserRegister() {
        // Arrange: Set up input and expected response
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setEmail("test@gmail.com");
        newUserDTO.setName("Test");
        newUserDTO.setPassword("Test@123");

        ResponseEntity<?> expectedResponse = ResponseEntity.ok("User Registered success.");
        Mockito.doReturn(expectedResponse)
                .when(userService)
                .userRegister(ArgumentMatchers.any(NewUserDTO.class));

        // Call the actual method (if needed)
        ResponseEntity<?> actualResponse = userService.userRegister(newUserDTO);
        // Assert: Verify the response
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void getUserTest() {
        UUID id = UUID.randomUUID();
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setId(id);
        userDataDTO.setName("Test");
        userDataDTO.setEmail("Test@gmai.com");

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(userDataDTO);
        Mockito.doReturn(expectedResponse).when(userService).userData(ArgumentMatchers.any(UUID.class));
        ResponseEntity<?> actualResponse = userService.userData(id);
        // Assert: Verify the response
        assertEquals(expectedResponse, actualResponse);
    }


}
