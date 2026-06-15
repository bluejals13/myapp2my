package com.example.demo.user.domain;

import com.example.demo.admin.domain.UserStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private LocalDateTime passwordChangedAt; // 비번 변경 시간

    // 기본 생성자 (핵심 수정)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.passwordChangedAt = null;
    }

    // 팩토리 메서드
    public static User create(String username, String encodedPassword) {
        return new User(username, encodedPassword);
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
        this.passwordChangedAt = LocalDateTime.now();
    }

    public LocalDateTime getPasswordChangedAt() {
        return passwordChangedAt;
    }
    
    public void changeStatus(UserStatus status) {
    this.status = status;
    }
}
