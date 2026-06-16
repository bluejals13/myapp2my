package com.example.demo.admin.audit;

import com.example.demo.admin.audit.dto.AuditResponse;
import com.example.demo.admin.audit.service.AuditAdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/audits")
@RequiredArgsConstructor
public class AuditAdminController {

    private final AuditAdminService auditAdminService;

    @PreAuthorize("hasAuthority('AUDIT_READ')")
    @GetMapping
    public List<AuditResponse> getAudits(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action
    ) {
        return auditAdminService.getAudits(userId, action);
    }

    @PreAuthorize("hasAuthority('AUDIT_READ')")
    @GetMapping("/{id}")
    public AuditResponse getAudit(@PathVariable Long id) {
        return auditAdminService.getAudit(id);
    }
    
    @GetMapping
    public Page<AuditResponse> getAudits(
        Pageable pageable
    );
    
}
