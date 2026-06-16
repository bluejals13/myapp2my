package com.example.demo.admin.user.service;

import com.example.demo.admin.audit.domain.AuditAction;
import com.example.demo.admin.audit.service.AuditService;

import com.example.demo.admin.user.dto.AdminUserResponse;

import com.example.demo.user.repository.UserRepository;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.User;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserAdminService {

    private final UserRepository userRepository;
    private final AuditService auditService;
    @Transactional
    public void deleteUser(Long adminId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //userRepository.delete(user);
        user.changeStatus(UserStatus.SUSPENDED);

        auditService.log(
                adminId,
                AuditAction.USER_STATUS_CHANGE,
                userId
        );
    }
    
    @Transactional
    public void changeStatus(Long adminId, Long userId, UserStatus status) {

        User user = userRepository.findById(userId)
            .orElseThrow();

        user.changeStatus(status);

        auditService.log(
            adminId,
            AuditAction.USER_STATUS_CHANGE,
            userId
        );
    }
}
