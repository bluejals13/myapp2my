package com.example.demo.user.dto;

import java.util.List;

public record MeResponse(
    Long id,
    String username,
    List<String> roles,
    List<String> permissions
) {}
