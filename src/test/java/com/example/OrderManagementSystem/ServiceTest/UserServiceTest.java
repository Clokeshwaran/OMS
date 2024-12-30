package com.example.OrderManagementSystem.ServiceTest;

import com.example.OrderManagementSystem.dto.NewUserDTO;
import com.example.OrderManagementSystem.dto.UserDataDTO;
import com.example.OrderManagementSystem.entity.Role;
import com.example.OrderManagementSystem.entity.UserEntity;
import com.example.OrderManagementSystem.repository.RoleRepository;
import com.example.OrderManagementSystem.repository.UserEntityRepository;
import com.example.OrderManagementSystem.service.impl.UserServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceIMPL userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserRegister_Success() {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setEmail("valid.email@example.com");
        newUserDTO.setName("Test User");
        newUserDTO.setPassword("Password123");

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID());
        userEntity.setEmail(newUserDTO.getEmail());
        userEntity.setName(newUserDTO.getName());
        userEntity.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        Role role = new Role();
        role.setId(1);
        userEntity.setRole(role);

        when(userEntityRepository.findByEmail(newUserDTO.getEmail())).thenReturn(null);
        when(roleRepository.findById(1)).thenReturn(Optional.of(new Role()));
//        when(modelMapper.map(newUserDTO, UserEntity.class)).thenReturn(userEntity);

        ResponseEntity<?> response = userService.userRegister(newUserDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Registered success.", response.getBody());
//        verify(userEntityRepository, times(1)).save(userEntity);
    }

    @Test
    void testUserRegister_InvalidEmail() {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setEmail("invalid-email");
        newUserDTO.setName("Test User");
        newUserDTO.setPassword("Password123");

        ResponseEntity<?> response = userService.userRegister(newUserDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please enter valid user name", response.getBody());
        verify(userEntityRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testUserRegister_UserAlreadyExists() {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setEmail("existing.email@example.com");
        newUserDTO.setName("Test User");
        newUserDTO.setPassword("Password123");

        UserEntity existingUser = new UserEntity();
        existingUser.setEmail(newUserDTO.getEmail());

        when(userEntityRepository.findByEmail(newUserDTO.getEmail())).thenReturn(existingUser);

        ResponseEntity<?> response = userService.userRegister(newUserDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is already exists", response.getBody());
        verify(userEntityRepository, never()).save(any(UserEntity.class));
    }

    // Test case for valid user
//    @Test
//    void testUserData_ValidUser() {
//        UUID userId = UUID.randomUUID();
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(userId);
//        userEntity.setName("Test User");
//
//        UserDataDTO expectedUserDataDTO = new UserDataDTO();
//        expectedUserDataDTO.setName("Test User");
//
//        // Mock repository to return a user entity
//        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//
//        // Mock ModelMapper to return the DTO
//        when(modelMapper.map(userEntity, UserDataDTO.class)).thenReturn(expectedUserDataDTO);
//
//        // Call the method
//        UserDataDTO result = userService.userData(userId);
//
//        // Verify the result
//        assertNotNull(result);
//        assertEquals("Test User", result.getName());
//    }

    // Test case for invalid user (user does not exist)
    @Test
    void testUserData_InvalidUser() {
        UUID userId = UUID.randomUUID();

        // Mock repository to return an empty Optional
        when(userEntityRepository.findById(userId)).thenReturn(null);

        // Call the method
        ResponseEntity<?> result = userService.userData(userId);

        // Verify the result
        assertNull(result.getBody());
    }

    // Test case for repository error (simulate an exception)
    @Test
    void testUserData_RepositoryError() {
        UUID userId = UUID.randomUUID();

        // Mock repository to throw an exception
        when(userEntityRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));

        // Call the method and verify exception is thrown
        assertThrows(RuntimeException.class, () -> userService.userData(userId));
    }
}
