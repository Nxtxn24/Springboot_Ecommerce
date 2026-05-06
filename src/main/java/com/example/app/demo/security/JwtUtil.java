package com.example.app.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import com.example.app.demo.users.UserEntity;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final String SECRET = "mysecretkeymysecretkeymysecretkey";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getEmail()) 
                .issuedAt(new Date())
                .claim("role", user.getRole().name())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key) 
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public String extractRole(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }
}