package com.example.OrderManagementSystem.exception;

import lombok.Data;

@Data
public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(String message){
        super(message);
    }
}
