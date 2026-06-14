package com.example.demo.user.dto;

public record LoginResponse(
    String accessToken,
    String refreshToken,
    String grantType
) {}
