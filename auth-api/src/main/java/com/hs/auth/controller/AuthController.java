package com.hs.auth.controller;

import com.hs.auth.api.AuthApi;
import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.authentication.jwt.application.ValidateTokenUseCase;
import com.hs.auth.service.AuthFacadeService;
import com.hs.auth.user.domain.ServiceUser;
import com.hs.auth.user.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {

    private final ValidateTokenUseCase validateTokenUseCase;
    private final AuthFacadeService authFacadeService;

    public AuthController(ValidateTokenUseCase validateTokenUseCase,
                          AuthFacadeService authFacadeService) {
        this.validateTokenUseCase = validateTokenUseCase;
        this.authFacadeService = authFacadeService;
    }

    @Override
    public ApiResponse<User> verifyToken(String accessToken, @AuthenticationPrincipal User user) {
        authFacadeService.verifyToken(accessToken, user);
        return ApiResponse.success("토큰 검증이 완료되었습니다.", user);
    }

    @Override
    public ApiResponse<String> refreshToken(String refreshToken) {
        // TODO: 토큰 갱신 로직 구현
        return ApiResponse.success("토큰이 갱신되었습니다.", "new-access-token");
    }

    @Override
    public ApiResponse<Void> logout(String accessToken) {
        // TODO: 로그아웃 로직 구현 (토큰 무효화)
        return ApiResponse.success("로그아웃이 완료되었습니다.", null);
    }

    @Override
    public ApiResponse<Object> getUserInfo(String accessToken, @AuthenticationPrincipal User user) {
        return ApiResponse.success("사용자 정보 조회가 완료되었습니다.", authFacadeService.getUserInfo(accessToken, user));
    }

    @Override
    public ApiResponse<Void> revokeToken(String accessToken, String refreshToken) {
        // TODO: 토큰 폐기 로직 구현
        return ApiResponse.success("토큰이 폐기되었습니다.", null);
    }
}