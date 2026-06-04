package com.example.demo.user.security;

import com.example.demo.user.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.TokenResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public TokenResponse refresh(String refreshToken) {

        // 0. parse once
        Claims claims = jwtProvider.parseClaims(refreshToken);

        // 1. 토큰에서 userId 추출
        Long userId = Long.parseLong(claims.getSubject());
        String type = claims.get("type", String.class);
        
        if (!"refresh".equals(type)) {
            throw new BadCredentialsException("INVALID_TOKEN_TYPE");
        }    

        // 2. Redis에서 저장된 refreshToken 조회
        String saved = redisTemplate.opsForValue()
                .get("refresh:" + userId);

        // 3. 검증
        if (saved == null || !refreshToken.equals(saved)) {
            throw new BadCredentialsException("INVALID_REFRESH_TOKEN");
        }

        // 4. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow();

        // 5. 새로운 Access Token 발급
        String newAccessToken =
            jwtProvider.createAccessToken(user.getId(), user.getUsername());

        return new TokenResponse(newAccessToken);
    }
}
