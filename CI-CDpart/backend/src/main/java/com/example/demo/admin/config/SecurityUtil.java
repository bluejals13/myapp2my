package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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