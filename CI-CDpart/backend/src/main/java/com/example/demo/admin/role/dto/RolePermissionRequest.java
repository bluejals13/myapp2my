package com.example.demo.admin.role.dto;

import java.util.List;

public record RolePermissionRequest(
        List<Long> permissionIds
) {}