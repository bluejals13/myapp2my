package com.example.demo.user.dto;

public record UserResponse(
        Long id,
        String username
) {
        return new UserResponse(
        user.getId(),
        user.getUsername()
);
}
