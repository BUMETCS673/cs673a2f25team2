package com.shanzhu.health.config;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtConfig {
    // Validity period
    private static final long JWT_EXPIRE = 60 * 180 * 1000L; // 1 hour
    
    // Read key from environment variable or configuration file (prefer environment variable JWT_SECRET)
    @Value("${jwt.secret:${JWT_SECRET:y3aX2K8/4TdF6zBmQv7hPwScN9jLgRtA+UoWxEiYlDn5fMpGqHtJkKuV}}")
    private String jwtKey;

    public String createToken(Object data) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(JSON.toJSONString(data))
                .issuer("system")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRE))
                .signWith(getSigningKey(), Jwts.SIG.HS256) // Use new version signature method
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtKey); // Decode Base64 key
        return Keys.hmacShaKeyFor(keyBytes); // Generate HMAC-SHA key
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Verify key
                .build()
                .parseSignedClaims(token)   // Replace old version parseClaimsJws
                .getPayload();
    }

    public <T> T parseToken(String token, Class<T> clazz) {
        Claims claims = parseToken(token);
        return JSON.parseObject(claims.getSubject(), clazz);
    }
}
