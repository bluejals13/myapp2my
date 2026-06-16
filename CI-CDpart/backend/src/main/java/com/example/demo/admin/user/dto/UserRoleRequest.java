package com.example.demo.admin.user.dto;

import java.util.List;

public record UserRoleRequest(
        List<Long> roleIds
) {
}