package com.example.demo.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

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

    private LocalDateTime passwordChangedAt;	// 비번 변경 부분
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
        LocalDateTime passwordChangedAt		// 비번 변경 부분
    ) {
        this.username = username;
        this.password = password;
        this.passwordChangedAt = passwordChangedAt;	// 비번 변경 부분
    }
	}

	public static User create(String username, String encodedPassword) {
		return new User(username, encodedPassword, LocalDateTime.now() // 날짜시간 기록);
	}

	public void updatePassword(String encodedPassword) {
		this.password = encodedPassword;
        this.passwordChangedAt = LocalDateTime.now();	// 날짜시간 기록
	}
	
    public LocalDateTime getPasswordChangedAt() {
        return passwordChangedAt;				// 비번 변경 부분
    }
}
