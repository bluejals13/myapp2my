package com.example.demo.user.repository;

import com.example.demo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    
    @EntityGraph(attributePaths = {
        "roles",
        "roles.permissions"
    })
    Optional<User> findWithRolesById(Long id);
    
    @Query("""
            select distinct u
            from User u
            join u.roles r
            join r.permissions p
            where p.id = :permissionId
        """)
        List<User> findByPermissionId(Long permissionId);
}
