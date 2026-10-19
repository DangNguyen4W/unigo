package com.unigo.service;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.unigo.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenExpirationMs;

    public JwtService(
            @Value("${jwt.secret-base64}") String secretBase64,
            @Value("${jwt.access-token-expiration-ms:900000}") long accessTokenExpirationMs) {
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        if (accessTokenExpirationMs <= 0) {
            throw new IllegalArgumentException("JWT expiration must be positive");
        }
    }

    public String generateAccessToken(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User have not existed");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User must have a role before generating a token");
        }
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(accessTokenExpirationMs);
        return Jwts.builder().subject(user.getId().toString()).issuedAt(Date.from(now))
                .expiration(Date.from(expiration)).signWith(signingKey, Jwts.SIG.HS256).compact();
    }

    public Claims parseAccessToken(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}
