package com.example.demo.admin.user.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private UserStatus status;
    private LocalDateTime passwordChangedAt;
}
