package com.example.demo.admin.user.dto;

import com.example.demo.user.domain.UserStatus;

import java.time.LocalDateTime;

public record AdminUserResponse (
    Long id,
    String username,
    UserStatus status,
    LocalDateTime passwordChangedAt
) {}
