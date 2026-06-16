package com.example.demo.admin.permission.service;

import com.example.demo.admin.permission.dto.PermissionResponse;
import com.example.demo.admin.permission.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PermissionAdminService {

    private final PermissionRepository permissionRepository;

    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }
}