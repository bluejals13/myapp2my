package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/auth/signup")
    public dto.UserResponse signup(
            @RequestBody dto.SignupRequest req
    ) {
        return userService.signup(req);
    }

    // 로그인
    @PostMapping("/auth/login")
    public String login(
            @RequestBody dto.LoginRequest req
    ) {
        return userService.login(req);
    }

    // 내 정보 조회
    @GetMapping("/users/me")
    public dto.UserResponse me(
            @RequestParam Long userId
    ) {
        return userService.getMe(userId);
    }

    // 비밀번호 수정
    @PatchMapping("/users/me")
    public void update(
            @RequestParam Long userId,
            @RequestBody dto.UpdateRequest req
    ) {
        userService.updatePassword(userId, req);
    }
}
