package com.example.demo.admin.menu.repository;

import com.example.demo.admin.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}