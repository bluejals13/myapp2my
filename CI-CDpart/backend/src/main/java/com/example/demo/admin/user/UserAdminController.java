package com.example.demo.admin.user;

import com.example.demo.admin.user.service.UserAdminService;
import com.example.demo.admin.user.service.UserRoleService;
import com.example.demo.admin.user.dto.UserRoleRequest;
import com.example.demo.admin.user.dto.UserStatusRequest;
import com.example.demo.admin.user.dto.AdminUserResponse;

import com.example.demo.admin.config.SecurityUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;
    private final UserRoleService userRoleService;
    private final SecurityUtil securityUtil;

    @PostConstruct
    public void init() {
        System.out.println("UserAdminController LOADED");
    }
    
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        Long adminId = securityUtil.getUserId();

        userAdminService.deleteUser(adminId, id);
    }
    
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping("/{id}/status")
    public void changeStatus(
            @PathVariable Long id,
            @RequestBody UserStatusRequest request
    ) {
        Long adminId = securityUtil.getUserId();

        userAdminService.changeStatus(adminId, id, request.status());
    }
    
    @PreAuthorize("hasAuthority('USER_ROLE_MANAGE')")
    @PostMapping("/{id}/roles")
    public void assignRoles(
            @PathVariable Long id,
            @RequestBody UserRoleRequest request
    ) {
        Long adminId = securityUtil.getUserId();

        userRoleService.assignRoles(adminId, id, request.roleIds());
    }
}
