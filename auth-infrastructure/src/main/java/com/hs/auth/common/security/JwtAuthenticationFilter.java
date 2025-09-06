package com.hs.auth.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.auth.authentication.jwt.application.ValidateTokenUseCase;
import com.hs.auth.authentication.jwt.domain.port.TokenValidator;
import com.hs.auth.user.domain.port.UserRepository;
import com.hs.auth.user.domain.UserId;
import com.hs.auth.user.domain.User;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final ValidateTokenUseCase validateTokenUseCase;
    private final TokenValidator tokenValidator;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(ValidateTokenUseCase validateTokenUseCase,
                                   TokenValidator tokenValidator,
                                   UserRepository userRepository) {
        this.validateTokenUseCase = validateTokenUseCase;
        this.tokenValidator = tokenValidator;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenFromRequest(request);
        try {
            if (token != null && validateTokenUseCase.execute(token)) {

                // 토큰에서 userId와 serviceName 추출
                String userId = tokenValidator.extractSubject(token);
                String serviceName = tokenValidator.extractServiceName(token);

                // ServiceUser로 해당 서비스에 가입되어 있는지 확인
                //TODO Hosung Cachcing처리 Evict 타이밍 확인 or TTL
                userRepository.findServiceUserByUserIdAndServiceName(UserId.of(userId), serviceName)
                        .orElseThrow(() -> new RuntimeException("해당 서비스에 가입되지 않은 사용자입니다."));

                // 기본 User 정보도 조회 (SecurityContext에 설정용)
                //TODO Hosung Cachcing처리 Evict 타이밍 확인 or TTL
                User user = userRepository.findById(UserId.of(userId))
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

                setAuthentication(user);
                log.debug("JWT 인증 성공: {} (서비스: {})", user.getEmail(), serviceName);
            }
        } catch (Exception e) {
            log.warn("JWT 사용자 정보 추출 실패: {}", e.getMessage());
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    private void setAuthentication(User user) {
        List<SimpleGrantedAuthority> authorities = List.of();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 인증이 필요없는 경로들
        return path.startsWith("/api/auth/oauth2") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/health") ||
                path.equals("/actuator/health") ||
                path.equals("/api/auth/health");
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