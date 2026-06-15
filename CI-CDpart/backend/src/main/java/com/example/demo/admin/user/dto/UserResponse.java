package com.example.demo.admin.user.dto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String status;
    private LocalDateTime passwordChangedAt;
}
