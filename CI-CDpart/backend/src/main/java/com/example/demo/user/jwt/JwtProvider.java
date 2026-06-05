package com.example.demo.user.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {    // 각 토큰 제공 파일

    // 환경 변수 는 나중에 고려 1.
    @Value("${jwt.secret}")
    private String secretKey;

    // 환경 변수 는 나중에 고려 2.
    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;
    // secretKey 길이 32바이트 미만이면 런타임 에러
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(
            secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    // 1. 접근 토큰 생성
    public String createAccessToken(Long userId, String username) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + expiration);

    String jti = UUID.randomUUID().toString();

    return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setId(jti) // 🔥 핵심
            .claim("username", username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}
    // 1. 리프레시 토큰 생성    
    public String createRefreshToken(Long userId) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000L);

    String jti = UUID.randomUUID().toString();

    return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setId(jti) // 🔥 핵심
            .claim("type", "refresh")
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}
    

    // 2. claims 공통 파서
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // 2-5. Jti 추출
    public String getJti(String token) {
        return parseClaims(token).getId();
    }

    // 3. userId 추출
    public Long getUserId(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    // 4. 중복 등 예외처리 시 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
