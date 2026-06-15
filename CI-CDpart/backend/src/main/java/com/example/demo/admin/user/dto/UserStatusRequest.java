package com.example.demo.admin.user.dto;

import com.example.demo.admin.user.domain.UserStatus;

public record UserStatusRequest(
        UserStatus status
) {
}
