package com.example.demo.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    // 🔥 blacklist 등록
    public void blacklist(String jti) {
        redisTemplate.opsForValue().set(
                "blacklist:" + jti,
                "true",
                Duration.ofMinutes(30)
        );
    }

    // 🔥 체크
    public boolean isBlacklisted(String token, JwtProvider jwtProvider) {

        String jti = jwtProvider.getJti(token);

        return Boolean.TRUE.equals(
                redisTemplate.hasKey("blacklist:" + jti)
        );
    }
}
