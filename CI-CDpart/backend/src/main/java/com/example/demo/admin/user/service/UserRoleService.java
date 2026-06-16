package com.example.demo.admin.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.admin.role.domain.Role;
import com.example.demo.admin.role.repository.RoleRepository;

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

    public void assignRoles(
            Long userId,
            List<Long> roleIds
    ) {

        User user = userRepository.findById(userId)
            .orElseThrow();

        Set<Role> roles =
            new HashSet<>(
                roleRepository.findAllById(roleIds)
            );

        if (roles.size() != roleIds.size()) {
            throw new IllegalArgumentException("Role not found");
            }

        user.setRoles(roles);
    }
}
