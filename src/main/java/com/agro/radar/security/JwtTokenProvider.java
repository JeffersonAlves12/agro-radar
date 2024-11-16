package com.agro.radar.security;

import java.security.Key;
import java.sql.Date;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final Key key;

    @Value("${jwt.expiration-ms}")
    private int jwtExpirationMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret.trim()); // Remove espaços extras
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }
    
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(jwtExpirationMs))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String getEmailDoToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Invalid token
            return false;
        }
    }
}
