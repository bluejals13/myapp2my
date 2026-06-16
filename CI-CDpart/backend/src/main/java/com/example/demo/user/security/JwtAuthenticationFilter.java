package com.example.demo.user.security;

import com.example.demo.user.jwt.JwtProvider;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;

import jakarta.servlet.FilterChain;                // 서브렛 http 요청 가로체기 및 jwt 검사
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;             // 생성자 주입 자동 생성

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;    // jwt 사용자 정보 추출 후 생성
import org.springframework.security.core.authority.SimpleGrantedAuthority;        // 권한 처리
import org.springframework.security.core.context.SecurityContextHolder;                    // 보안 문자열 보관
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;    // 요청 당 1회 실행 필터
import org.springframework.data.redis.core.RedisTemplate;    // redis 템플릿 으로 캐시 운용
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {    // 각 토큰 검증 필터

    private final JwtProvider jwtProvider;
    private final TokenBlacklistService tokenBlacklistService;
    private final RedisTemplate<String, String> redisTemplate; // 🔥 추가
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/auth/")) {
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
            
                User user = userRepository.findWithRolesById(userId).orElseThrow();
            
                String tokenJti = jwtProvider.getJti(token); // 🔥 중요

                // 3. Redis의 현재 활성 세션 조회
                String activeJti = redisTemplate.opsForValue()
                        .get("active-jti:" + userId);
            
            // active-jti 없고            
            // 현재 활성 토큰이 아니면 실패
            if (activeJti != null && !tokenJti.equals(activeJti)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // 유저 상태 분별
            if (user.getStatus() == null || user.getStatus() != UserStatus.ACTIVE) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            System.out.println("tokenJti = " + tokenJti);
            System.out.println("activeJti = " + activeJti);
                // 4. 인증 성공
                CustomUserPrincipal principal = new CustomUserPrincipal(userId);

                List<GrantedAuthority> authorities = new ArrayList<>();
                    
                // ROLE
                user.getRoles().forEach(r ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getName()))
                );
                
                // PERMISSION
                user.getRoles().forEach(r ->
                    r.getPermissions().forEach(p ->
                        authorities.add(new SimpleGrantedAuthority(p.getName()))
                    )
                );
            
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                //List.of(new SimpleGrantedAuthority("ROLE_USER"))
                                authorities
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
