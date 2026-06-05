package com.example.demo.user.security;

import com.example.demo.user.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {    // 각 토큰 검증 필터

    private final JwtProvider jwtProvider;
    private final TokenBlacklistService tokenBlacklistService;
    private final RedisTemplate<String, String> redisTemplate; // 🔥 추가

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(7);
            System.out.println("TOKEN CHECK: " + token);
            System.out.println("BLACKLIST CHECK: " + tokenBlacklistService.isBlacklisted(token));

            // 1. 블랙리스트 검사
            if (tokenBlacklistService.isBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 2. JWT 검증
            if (!jwtProvider.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 return;
            }
            
                Long userId = jwtProvider.getUserId(token);
                String tokenJti = jwtProvider.getJti(token); // 🔥 중요

                // 3. Redis의 현재 활성 세션 조회
                String activeJti = redisTemplate.opsForValue()
                        .get("active-jti:" + userId);
            
            // active-jti 없으면 실패
            if (activeJti == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            
            // 현재 활성 토큰이 아니면 실패
            if (!tokenJti.equals(activeJti)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
                // 4. 인증 성공
                CustomUserPrincipal principal = new CustomUserPrincipal(userId);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
