package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// 회원가입
	public dto.UserResponse signup(dto.SignupRequest req) {

		if (userRepository.findByUsername(req.username()).isPresent()) {
			throw new RuntimeException("이미 존재하는 유저");
		}

		User user = new User(req.username(), req.password());
		User saved = userRepository.save(user);

		return new dto.UserResponse(saved.getId(), saved.getUsername());
	}

	// 로그인 (최소 버전)
	public String login(dto.LoginRequest req) {

		User user = userRepository.findByUsername(req.username())
				.orElseThrow(() -> new RuntimeException("유저 없음"));

		if (!user.getPassword().equals(req.password())) {
			return "LOGIN FAIL";
		}

		return "LOGIN SUCCESS";
	}

	// 내 정보 조회
	public dto.UserResponse getMe(Long userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("유저 없음"));

		return new dto.UserResponse(user.getId(), user.getUsername());
	}

	// 비밀번호 변경
	public void updatePassword(Long userId, dto.UpdateRequest req) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("유저 없음"));

		user.updatePassword(req.password());
		userRepository.save(user);
	}
}