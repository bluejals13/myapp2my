package com.example.demo.user.repository;

import com.example.demo.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {
        "roles",
        "roles.permissions"
    })
    Optional<User> findWithRolesById(Long id);
    
    boolean existsByUsername(String username);
}
