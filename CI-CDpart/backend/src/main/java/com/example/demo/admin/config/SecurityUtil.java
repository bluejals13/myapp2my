package com.example.demo.admin.config;


import com.example.demo.user.security.CustomUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class SecurityUtil {

    public Long getUserId() {
        return ((CustomUserPrincipal)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
        ).getUserId();
    }
}
