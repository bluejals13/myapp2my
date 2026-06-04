package com.example.demo.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import com.example.demo.user.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProvider jwtProvider;

    // 🔥 blacklist 등록
    public void blacklist(String token) {
        String jti = jwtProvider.getJti(token);
        redisTemplate.opsForValue().set(
                "blacklist:" + jti,
                "true",
                Duration.ofMinutes(30)
        );
    }

    // 🔥 체크
    public boolean isBlacklisted(String token) {

        String jti = jwtProvider.getJti(token);

        return Boolean.TRUE.equals(
                redisTemplate.set("blacklist:" + jti, "1")
        );
    }
}
