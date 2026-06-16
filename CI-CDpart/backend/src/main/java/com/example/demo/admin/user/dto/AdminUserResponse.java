package com.example.demo.admin.user.dto;

import com.example.demo.user.domain.UserStatus;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminUserResponse (
    Long id;
    String username;
    UserStatus status;
    LocalDateTime passwordChangedAt;
) {}
