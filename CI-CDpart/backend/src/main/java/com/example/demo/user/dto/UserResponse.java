package com.example.demo.user.dto;

public UserResponse getMe(Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow();

    return new UserResponse(
            user.getId(),
            user.getUsername()
    );
}
