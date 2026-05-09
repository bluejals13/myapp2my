package com.example.demo.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}