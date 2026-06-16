package com.example.demo.admin.audit.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;      // 누가 했는지
    private String action;    // 무엇을 했는지 (CREATE, DELETE 등)
    
    private String targetType; // 대상 타입

    private Long targetId;    // 대상 ID

    private String result;    // 작업 성공/실패

    @CreatedDate
    private LocalDateTime createdAt;
}
