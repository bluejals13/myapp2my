package com.example.demo.user.dto;

public record LoginResult(
        String accessToken,
        String grantType,
        String refreshToken
) {}
