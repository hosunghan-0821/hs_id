package com.hs.auth.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.auth.project.application.ProjectQueryService;
import com.hs.auth.project.domain.Project;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProjectClientAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ProjectClientAuthFilter.class);
    private static final String CLIENT_ID_HEADER = "X-Client-Id";
    private static final String CLIENT_SECRET_HEADER = "X-Client-Secret";

    private final ProjectQueryService projectQueryService;
    private final PasswordEncoder passwordEncoder;

    public ProjectClientAuthFilter(ProjectQueryService projectQueryService, PasswordEncoder passwordEncoder) {
        this.projectQueryService = projectQueryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientId = request.getHeader(CLIENT_ID_HEADER);
        String clientSecret = request.getHeader(CLIENT_SECRET_HEADER);

        try {
            if (clientId == null || clientSecret == null) {
                throw new IllegalArgumentException("Client-Id 또는 Client-Secret 헤더가 없습니다.");
            }

            // Client ID로 프로젝트 조회
            Project project = projectQueryService.findByClientId(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Client-Id입니다."));

            // Client Secret 검증
            if (!passwordEncoder.matches(clientSecret, project.getClientSecret())) {
                throw new IllegalArgumentException("Client-Secret이 일치하지 않습니다.");
            }

            // 프로젝트가 활성 상태인지 확인
            if (project.getStatus() != com.hs.auth.project.domain.ProjectStatus.ACTIVE) {
                throw new IllegalArgumentException("비활성화된 프로젝트입니다.");
            }

            // SecurityContext에 프로젝트 정보 설정
            setAuthentication(project);
            log.debug("Project Client 인증 성공: {}", project.getName());

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.warn("Project Client 인증 실패: {}", e.getMessage());
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            SecurityContextHolder.clearContext();
        }
    }

    private void setAuthentication(Project project) {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_PROJECT"));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(project, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Project Client 인증이 필요한 경로만 필터링
        boolean needsClientAuth =
                path.equals("/api/auth/oauth2/authorize-url") ||
                path.equals("/api/auth/oauth2/authenticate") ||
                path.equals("/api/auth/refresh") ||
                path.equals("/api/auth/token/verify");

        // 필요한 경로가 아니면 필터를 건너뜀 (shouldNotFilter = true)
        return !needsClientAuth;
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