package com.example.demo.admin;

import com.example.demo.role.dto.RolePermissionRequest;
import com.example.demo.role.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PreAuthorize("hasAuthority('ROLE_PERMISSION_MANAGE')")
    @PostMapping("/{roleId}/permissions")
    public void assignPermissions(
            @PathVariable Long roleId,
            @RequestBody RolePermissionRequest request
    ) {
        rolePermissionService.assignPermissions(roleId, request.permissionIds());
    }
}
