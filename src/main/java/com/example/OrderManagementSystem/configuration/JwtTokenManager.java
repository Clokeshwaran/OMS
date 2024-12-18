package com.example.OrderManagementSystem.configuration;

import com.example.OrderManagementSystem.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenManager implements Serializable {

    /**
     *
     */

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int TOKEN_VALIDITY = 24 * 60 * 60;

    private String secret = "assdfghjklzxcvbnm,ppppppoiuytq1234567890-/lok00o0-9kl";

    public String generateJwtToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 1000 ))
                .signWith(SignatureAlgorithm.HS512, secret) // Use the encoded key
                .compact();
    }

    //    private Key getSignKey() {
//        byte[] keyBytes = JWT_SECRET.getBytes();
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        final Claims claims = getClaims(token);
        //final Claims claims = parser().parseClaimsJws(token).getBody()
        Boolean isTokenExpired = claims.getExpiration().before(new Date());

        if (Boolean.TRUE.equals(isTokenExpired)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Token Expired");
        }
        return username.equals(userDetails.getUsername()) && !isTokenExpired;
    }


    public String getUsernameFromToken(String token) {
//        byte[] publicKeyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//        PublicKey publicKey;
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            publicKey = keyFactory.generatePublic(keySpec);
//        } catch (Exception e) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Failed to create public key from string" + "\n" + e);
//
//        }

            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
    }

    public Claims getClaims(String token) {
//        byte[] publicKeyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//        PublicKey publicKey;
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            publicKey = keyFactory.generatePublic(keySpec);
//        } catch (Exception e) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Failed to create public key from string" + "\n" + e);
//        }

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Refresh Token Generation (Long-lived)
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Only include user identifier
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


}