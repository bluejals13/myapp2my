package com.example.demo.admin;

import com.example.demo.role.dto.RoleRequest;
import com.example.demo.role.dto.RoleResponse;
import com.example.demo.role.service.RoleAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RoleAdminController {

    private final RoleAdminService roleAdminService;

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping
    public List<RoleResponse> getRoles() {
        return roleAdminService.getRoles();
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping
    public void createRole(@RequestBody RoleRequest request) {
        roleAdminService.createRole(request);
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PatchMapping("/{id}")
    public void updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequest request
    ) {
        roleAdminService.updateRole(id, request);
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleAdminService.deleteRole(id);
    }
}