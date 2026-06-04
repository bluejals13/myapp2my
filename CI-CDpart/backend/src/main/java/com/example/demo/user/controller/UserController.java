package com.example.demo.user.controller;

import com.example.demo.user.dto.*;
import com.example.demo.user.security.AuthService;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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
    public LoginResponse login(@RequestBody LoginRequest req) {
        return userService.login(req);
    }

    // 내 정보 조회 (JWT 필요)
    @GetMapping("/users/me")
    public UserResponse getMe(Authentication authentication) {
        System.out.println("PRINCIPAL = " + principal);
        Long userId = (Long) authentication.getPrincipal();
        return userService.getMe(userId);
    }
    
    // 리프레시 와 redis 연결 가
    @PostMapping("/auth/refresh")
    public TokenResponse refresh(@RequestBody RefreshRequest req) {
        System.out.println("REQ = " + req);        
        return authService.refresh(req.getRefreshToken());
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
