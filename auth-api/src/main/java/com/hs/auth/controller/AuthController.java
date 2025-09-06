package com.hs.auth.controller;

import com.hs.auth.api.AuthApi;
import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.authentication.jwt.application.ValidateTokenUseCase;
import com.hs.auth.user.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {

    private final ValidateTokenUseCase validateTokenUseCase;

    public AuthController(ValidateTokenUseCase validateTokenUseCase) {
        this.validateTokenUseCase = validateTokenUseCase;
    }

    @Override
    public ApiResponse<Void> validateToken(String accessToken) {
        try {
            boolean isValid = validateTokenUseCase.execute(accessToken);
            if (isValid) {
                return ApiResponse.success("토큰 검증이 완료되었습니다.", null);
            } else {
                return ApiResponse.error("유효하지 않은 토큰입니다.", null);
            }
        } catch (Exception e) {
            return ApiResponse.error("토큰 검증 중 오류가 발생했습니다: " + e.getMessage(), null);
        }
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
    public ApiResponse<Object> getUserInfo(String accessToken) {
        try {
            User user = validateTokenUseCase.extractUserFromToken(accessToken);
            return ApiResponse.success("사용자 정보 조회가 완료되었습니다.", user);
        } catch (Exception e) {
            return ApiResponse.error("사용자 정보 조회 중 오류가 발생했습니다: " + e.getMessage(), null);
        }
    }

    @Override
    public ApiResponse<Void> revokeToken(String accessToken, String refreshToken) {
        // TODO: 토큰 폐기 로직 구현
        return ApiResponse.success("토큰이 폐기되었습니다.", null);
    }
}