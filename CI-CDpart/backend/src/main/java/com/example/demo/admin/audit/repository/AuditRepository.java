package com.example.demo.admin.audit.repository;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findByUserId(Long userId);

    List<Audit> findByAction(String action);

    List<Audit> findByUserIdAndAction(Long userId, String action);
}