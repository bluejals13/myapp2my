package com.example.demo.admin;

import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.dto.UserStatusRequest;
import com.example.demo.user.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;

    // 전체 사용자 조회
    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public List<UserResponse> getUsers() {
        return userAdminService.getUsers();
    }

    // 사용자 삭제
    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userAdminService.deleteUser(id);
    }

    // 사용자 상태 변경 (활성/비활성)
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping("/{id}/status")
    public void changeStatus(
            @PathVariable Long id,
            @RequestBody UserStatusRequest request
    ) {
        userAdminService.changeStatus(id, request.status());
    }
}