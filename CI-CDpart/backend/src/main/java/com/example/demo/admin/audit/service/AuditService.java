package com.example.demo.admin.audit.service;

import com.example.demo.admin.audit.domain.Audit;
import com.example.demo.admin.audit.domain.AuditAction;
import com.example.demo.admin.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditService {

    private final AuditRepository auditRepository;

    public void log(Long actorId, AuditAction action, Long targetId) {

        Audit audit = Audit.create(
                actorId,
                action,
                targetId
        );

        auditRepository.save(audit);
    }
}