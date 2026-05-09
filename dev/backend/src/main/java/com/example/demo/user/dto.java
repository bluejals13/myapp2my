package com.example.demo.user;

public class dto {

    public record SignupRequest(
            String username,
            String password
    ) {}

    public record LoginRequest(
            String username,
            String password
    ) {}

    public record UserResponse(
            Long id,
            String username
    ) {}

    public record UpdateRequest(
            String password
    ) {}
}