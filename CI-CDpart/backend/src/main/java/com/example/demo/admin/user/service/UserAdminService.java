package com.example.demo.admin.user.service;

import com.example.demo.admin.user.dto.AdminUserResponse;
import com.example.demo.user.domain.UserStatus;

import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.domain.User;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getStatus(),
                        user.getPasswordChangedAt()
                ))
                .toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) { throw new IllegalArgumentException("User not found"); }
        userRepository.deleteById(id);
    }

    public void changeStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));    // null safety 부분

        user.changeStatus(status);
    }
}
