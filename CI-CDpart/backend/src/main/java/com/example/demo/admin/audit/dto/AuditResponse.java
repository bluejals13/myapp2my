package com.example.demo.admin.audit.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuditResponse {

    private Long id;
    private Long userId;
    private String action;
    private String detail;
    private LocalDateTime createdAt;
}