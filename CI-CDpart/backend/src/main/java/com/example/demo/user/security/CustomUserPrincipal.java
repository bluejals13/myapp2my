package com.example.demo.user.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CustomUserPrincipal {

    private final Long userId;

    public CustomUserPrincipal(Long userId) {
        this.userId = userId;
    }

}
