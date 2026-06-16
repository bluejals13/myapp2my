package com.example.demo.admin.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.admin.role.domain.Role;
import com.example.demo.admin.role.repository.RoleRepository;
import com.example.demo.admin.audit.domain.AuditAction;
import com.example.demo.admin.audit.service.AuditService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuditService auditService;

    public void assignRoles(
            Long adminId,
            Long userId,
            List<Long> roleIds
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Set<Role> roles = new HashSet<>(
                roleRepository.findAllById(roleIds)
        );

        if (roles.size() != roleIds.size()) {
            throw new IllegalArgumentException("Role not found");
        }

        user.setRoles(roles); // ✅ 실제 적용

        auditService.log(
                adminId,
                AuditAction.ROLE_ASSIGN,
                userId
        );
    }
}
