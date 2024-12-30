package com.example.OrderManagementSystem.configuration;

import com.example.OrderManagementSystem.dto.ErrorResponseDto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String message = null;
        if (request.getAttribute("expired") != null) {
            message = (String) request.getAttribute("expired");
        } else if (request.getAttribute("blank-token") != null) {
            message = (String) request.getAttribute("blank-token");
        } else {
            message = authException.getMessage();
        }

        Gson gson = new Gson();
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(message);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        out.print(gson.toJson(errorResponse));
        out.flush();
    }


}