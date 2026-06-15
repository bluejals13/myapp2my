package com.example.demo.admin.audit.domain;

@Entity
@Table(name = "audit_logs")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;      // 누가 했는지
    private String action;    // 무엇을 했는지 (CREATE, DELETE 등)

    private String detail;    // 추가 설명 (선택)

    private LocalDateTime createdAt;
}