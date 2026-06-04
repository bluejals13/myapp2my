package com.example.demo.user.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public TokenResponse refresh(String refreshToken) {

        // 1. 토큰에서 userId 추출
        Long userId = jwtProvider.getUserId(refreshToken);

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
                jwtProvider.createToken(user.getId(), user.getUsername());

        return new TokenResponse(newAccessToken);
    }
}
