package com.example.demo.user.dto;

public record MeResponse(
    Long id,
    String username,
    List<String> roles,
    List<String> permissions
) {}