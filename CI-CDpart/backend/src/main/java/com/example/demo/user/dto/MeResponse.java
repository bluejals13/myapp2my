package com.example.demo.user.dto;

import com.example.demo.admin.role.domain.Role;
import com.example.demo.admin.permission.domain.Permission;

import java.util.List;

public record MeResponse(
    Long id,
    String username,
    List<String> roles,
    List<String> permissions
) {}
