package com.example.demo.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.user.security.CustomUserPrincipal;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public Long getUserId() {
        return ((CustomUserPrincipal)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
        ).getUserId();
    }
}
