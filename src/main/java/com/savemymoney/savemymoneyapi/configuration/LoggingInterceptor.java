package com.savemymoney.savemymoneyapi.configuration;

import com.savemymoney.savemymoneyapi.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final CustomUserDetailsService customUserDetailsService;

    public LoggingInterceptor(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("{} requested {}", customUserDetailsService.getUsuarioLogado() == null ? "Unknown user" : customUserDetailsService.getUsuarioLogado().getEmail(), request.getRequestURI());
        return true;
    }
}
