package com.hs.auth.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdminAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AdminAuthFilter.class);
    private static final String ADMIN_API_KEY_HEADER = "X-Admin-API-Key";

    @Value("${auth.admin.api-key}")
    private String adminApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader(ADMIN_API_KEY_HEADER);

        try {
            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new IllegalArgumentException("Admin API Key 헤더가 없습니다.");
            }

            if (!adminApiKey.equals(apiKey)) {
                throw new IllegalArgumentException("유효하지 않은 Admin API Key입니다.");
            }

            // SecurityContext에 Admin 권한 설정
            setAuthentication();
            log.debug("Admin 인증 성공");

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.warn("Admin 인증 실패: {}", e.getMessage());
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            SecurityContextHolder.clearContext();
        }
    }

    private void setAuthentication() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("admin", null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Admin 인증이 필요한 경로만 필터링
        boolean needsAdminAuth = path.startsWith("/api/projects");

        // 필요한 경로가 아니면 필터를 건너뜀 (shouldNotFilter = true)
        return !needsAdminAuth;
    }

    private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("message", message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), body);
    }
}