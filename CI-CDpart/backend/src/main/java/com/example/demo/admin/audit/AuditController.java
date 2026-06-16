package com.example.demo.admin.audit;

import com.example.demo.admin.audit.dto.AuditResponse;
import com.example.demo.admin.audit.service.AuditAdminService;
import com.example.demo.admin.audit.service.AuditService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@RestController
@RequestMapping("/admin/audits")
@RequiredArgsConstructor
public class AuditAdminController {

    private final AuditAdminService auditAdminService;

    @GetMapping
    public List<AuditResponse> getAudits(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action
    ) {
        return auditAdminService.getAudits(userId, action);
    }

    @GetMapping("/page")
    public Page<AuditResponse> getAudits(Pageable pageable) {
        return auditAdminService.getAudits(pageable);
    }
}
