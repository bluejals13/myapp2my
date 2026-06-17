package com.example.demo.admin.role.dto;

import com.example.demo.admin.role.domain.Role;

public record RoleDto(
    Long id,
    String name
) {
    public static RoleDto from(Role role) {
        return new RoleDto(
            role.getId(),
            role.getName()
        );
    }
}