package com.example.demo.admin.role.repository;

import com.example.demo.admin.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r join fetch r.permissions")
    List<Role> findAllWithPermissions();
}