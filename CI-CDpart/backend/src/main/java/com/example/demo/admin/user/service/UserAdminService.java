package com.example.demo.admin.user.service;

import com.example.demo.admin.user.dto.UserResponse;
import com.example.demo.user.domain.UserStatus;

import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.domain.User;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getPasswordChangedAt()
                ))
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void changeStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow();

        user.changeStatus(status);
    }
}
