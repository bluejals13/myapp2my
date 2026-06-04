package com.example.demo.user.security;

import com.example.demo.user.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.TokenResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import java.time.Duration; // 시간

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    public void blacklist(String token) {
        redisTemplate.opsForValue().set(
            "blacklist:" + token,
            "true",
            Duration.ofMinutes(30)
        );
    }

    public boolean isBlacklisted(String token) {
        return "true".equals(
            redisTemplate.opsForValue().get("blacklist:" + token)
        );
    }
}
