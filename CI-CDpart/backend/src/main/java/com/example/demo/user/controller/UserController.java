package com.example.demo.user.controller;

import com.example.demo.user.dto.*;
import com.example.demo.user.security.AuthService;
import com.example.demo.user.service.UserService;

import java.util.Arrays;
import java.util.Optional;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServlet.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.example.demo.user.security.CustomUserPrincipal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // 회원가입
    @PostMapping("/auth/signup")
    public UserResponse signup(@RequestBody SignupRequest req) {
        return userService.signup(req);
    }

    // 로그인
    @PostMapping("/auth/login")
    public LoginResponse login(
        @RequestBody LoginRequest req,
        HttpServletResponse response
        ) {
           LoginResult result = userService.login(req);

            Cookie cookie = new Cookie(
            "refreshToken",
            result.refreshToken()
                );
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true); // HTTPS에서만 전송
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
        return new LoginResponse(
            result.accessToken(),
            "Bearer"
            );
    }

    // 내 정보 조회 (JWT 필요)
    @GetMapping("/users/me")
    public UserResponse getMe(@AuthenticationPrincipal CustomUserPrincipal principal) {
        System.out.println("PRINCIPAL = " + principal.getUserId());
        return userService.getMe(principal.getUserId());
    }
    
    // 리프레시 와 redis 연결 가
    @PostMapping("/auth/refresh")
    public TokenResponse refresh(HttpServletRequest request) {

        String refreshToken = (cookies == null) ? null :
            Arrays.stream(cookies)
            .filter(c -> "refreshToken".equals(c.getName()))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);

        System.out.println("refreshToken = " + refreshToken);

        if (refreshToken == null) {
            throw new RuntimeException("NO_REFRESH_TOKEN");
        }
        return authService.refresh(refreshToken);
    }
    
    // 비밀번호 변경 (JWT 필요)
    @PatchMapping("/users/me/password")
    public void updatePassword(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody UpdatePasswordRequest req
    ) {
        userService.updatePassword(
                principal.getUserId(),
                req
        );
    }
}
