package com.example.OrderManagementSystem.configuration;


import com.example.OrderManagementSystem.constant.Constant;
import com.example.OrderManagementSystem.dto.ErrorResponseDto;
import com.example.OrderManagementSystem.service.User;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenManager tokenManager;

    private final User user;

    private static final AntPathRequestMatcher[] EXCLUDED_PATHS = { // Add the endpoints for public APIs which don't
//            new AntPathRequestMatcher("gs-guide-websocket"),
//            new AntPathRequestMatcher("/topic/greetings"),
//            new AntPathRequestMatcher("/app/hello"),
            new AntPathRequestMatcher("/lok"),
            new AntPathRequestMatcher("/java-fx-file-upload")

    };

    public JwtFilter(JwtTokenManager tokenManager, User user) {
        this.tokenManager = tokenManager;
        this.user = user;
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, HttpClientErrorException {

        JwtTokenManager jwtTokenManager = new JwtTokenManager();
        UserDetails userDetails = user.loadUserByUsername("username");
        System.out.println("TOKEN: "+ jwtTokenManager.generateJwtToken(userDetails));


//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");

        if (request.getMethod().equals("HEAD") || request.getMethod().equals("TRACE")) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }

        if (isExcludedPath(request)) {
            filterChain.doFilter(request, response);
        } else {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                handlePreflightRequest(response);
            } else {
                validateTokenAndAuthenticate(request, response, filterChain);
            }
        }
    }

    private void handlePreflightRequest(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600"); // Cache preflight response for 1 hour
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void validateTokenAndAuthenticate(HttpServletRequest request, HttpServletResponse response,
                                              FilterChain filterChain)
            throws IOException, ServletException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || tokenHeader.isEmpty()) {
            logger.info("Bearer String not found in token");
            request.setAttribute("blank-token", "Token not found");
            exceptionThrow(response, "Token not provided");
            return;
        }

        if (Boolean.FALSE.equals(checkTokenHeader(tokenHeader))) {
            logger.info("Bearer String not found in token");
            request.setAttribute("blank-token", "Token not found");
            exceptionThrow(response, "Token not provided");
            return;
        }
        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        try {
            String username = tokenManager.getUsernameFromToken(token);
            if (Boolean.TRUE.equals(checkUserName(username))) {
                UserDetails userDetails = user.loadUserByUsername(username);
                if (Boolean.TRUE.equals(tokenManager.validateJwtToken(token, userDetails))) {
                    authenticateUser(userDetails, request);
                    addRoleToSecurityContext(userDetails);
                }
            }
        } catch (MalformedJwtException je) {
            request.setAttribute("ERROR", "Provided valid token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            exceptionThrow(response, "Invalid token");
        } catch (ExpiredJwtException e) {
            logger.info(Constant.JWT_TOKEN_EXPIRED);
            request.setAttribute("expired", Constant.JWT_TOKEN_EXPIRED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            exceptionThrow(response, Constant.JWT_TOKEN_EXPIRED);
            return;
        } catch (IllegalArgumentException e) {
            logger.info("Unable to get JWT Token: {}");
            exceptionThrow(response, "Invalid token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Boolean checkTokenHeader(String tokenHeader) {
        return null != tokenHeader && !tokenHeader.isEmpty();
    }

    private Boolean checkUserName(String username) {
        return null != username && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void addRoleToSecurityContext(UserDetails userDetails) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(userDetails.getAuthorities());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        List<String> skipFilterUrls = Arrays.asList("/usermanagement/swagger-ui/**",
//                "/usermanagement/swagger-resources",
//                "/usermanagement/swagger-resources/**", "/usermanagement/v2/api-docs/**",
//                "/usermanagement/v2/api-docs");
//
//        return skipFilterUrls.stream().anyMatch(url -> {
//            AntPathMatcher antPathMatcher = new AntPathMatcher(url);
//            antPathMatcher.setPathSeparator(",");
//            return antPathMatcher.matchStart(url, request.getRequestURI());
//        });
//    }

    private void exceptionThrow(HttpServletResponse response, String message) throws IOException {
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

    private boolean isExcludedPath(HttpServletRequest request) {
        for (AntPathRequestMatcher excludedPath : EXCLUDED_PATHS) {
            if (excludedPath.matches(request)) {
                return true;
            }
        }
        return false;
    }

}