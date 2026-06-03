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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        
        String path = request.getServletPath();

        // 🔥 로그인/회원가입은 JWT 검사 자체를 안 함
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
      
        // 선 실행 토큰 생성 / 등록
        try {
            //System.out.println("JWT FILTER START"); //
            
            String header = request.getHeader("Authorization");
            
            //System.out.println("HEADER = " + header); //

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        //System.out.println("VALID = " + jwtProvider.validateToken(token)); //

        if (token != null && jwtProvider.validateToken(token)) {

            Long userId = jwtProvider.getUserId(token);

            CustomUserPrincipal principal = new CustomUserPrincipal(userId);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );

            //System.out.println("USER ID = " + userId); //

            SecurityContextHolder.getContext().setAuthentication(auth);
           }

           } catch (Exception e) {

            SecurityContextHolder.clearContext();
                // 🔥 선택: 완전 차단하려면 아래 활성화
                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //return;
        }
        //System.out.println("AUTH = "+ SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);
    }
}
