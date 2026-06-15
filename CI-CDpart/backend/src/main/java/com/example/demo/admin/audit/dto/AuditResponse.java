package com.example.demo.admin.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditResponse {
    private Long id;
    private Long userId;
    private String action;
    private String createdAt;
}