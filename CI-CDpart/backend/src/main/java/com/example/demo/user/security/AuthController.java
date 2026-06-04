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

        Long userId = jwtProvider.getUserId(refreshToken);

        String saved = redisTemplate.opsForValue()
                .get("refresh:" + userId);

        if (saved == null || !refreshToken.equals(saved)) {
            throw new BadCredentialsException("INVALID_REFRESH_TOKEN");
        }

        User user = userRepository.findById(userId)
                .orElseThrow();

        String newAccessToken =
                jwtProvider.createToken(user.getId(), user.getUsername());

        return new TokenResponse(newAccessToken);
    }
}
