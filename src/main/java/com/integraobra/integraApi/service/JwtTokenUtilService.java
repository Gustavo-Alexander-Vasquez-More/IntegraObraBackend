package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.users.UserLoginDetailDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenUtilService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Helper para obtener siempre la misma llave en formato SecretKey
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Cambiado: usa el objeto SecretKey y verifyWith
                .build()
                .parseSignedClaims(token)     // Cambiado: para versión 0.12+
                .getPayload()                // Cambiado: para versión 0.12+
                .getSubject();
    }

    //extractId
    public Long extractId(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    //Armamos el token con el username y tambien el id del usuario
    public String generateToken(UserLoginDetailDTO userLoginDetailDTO) {
        return Jwts.builder()
                .subject(userLoginDetailDTO.getUsername())
                .claim("userId", userLoginDetailDTO.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey()) // Usamos el helper
                .compact();
    }
}