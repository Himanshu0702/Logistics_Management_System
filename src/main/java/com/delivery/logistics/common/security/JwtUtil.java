package com.delivery.logistics.common.security;

import com.delivery.logistics.common.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "d3l1v3ryL0g1st1cs_JWT_Secr3t_K3y_2026!!";
    private final Long JWT_EXPIRATION_TIME = 3600000L;

    public String generateJwtToken(UUID id, String email, Role role) {
        Date currentTime = Date.from(Instant.now());
        Date expiryDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME);
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        JwtBuilder jwtBuilder = Jwts.builder()
                .subject(id.toString())
                .claim("role", role.toString())
                .claim("email", email)
                .issuedAt(currentTime)
                .expiration(expiryDate)
                .signWith(key);

        return jwtBuilder.compact();
    }

    public boolean validateJwtToken(String token) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token);

                return true;
            } catch (Exception e) {
                return false;
            }

    }

    public UUID extractIdFromJwtToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return UUID.fromString(claims.getSubject());
    }

    public Role extractRoleFromJwtToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String role =  claims.get("role", String.class);
        return Role.valueOf(role);
    }
}
