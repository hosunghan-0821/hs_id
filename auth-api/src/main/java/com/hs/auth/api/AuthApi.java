package com.hs.auth.api;

import com.hs.auth.api.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Authentication", description = "인증 관련 API")
public interface AuthApi {

    @PostMapping("/validate")
    @Operation(summary = "Access Token 검증", description = "제공된 Access Token의 유효성을 검증합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 검증 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    ApiResponse<Void> validateToken(@RequestHeader("Authorization") String accessToken);

    @PostMapping("/refresh")
    @Operation(summary = "Token 갱신", description = "Refresh Token을 사용하여 새로운 Access Token을 발급합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 Refresh Token")
    })
    ApiResponse<String> refreshToken(@RequestHeader("Refresh-Token") String refreshToken);

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자를 로그아웃하고 토큰을 무효화합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    ApiResponse<Void> logout(@RequestHeader("Authorization") String accessToken);

    @PostMapping("/me")
    @Operation(summary = "사용자 정보 조회", description = "Access Token으로 현재 사용자 정보를 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    ApiResponse<Object> getUserInfo(@RequestHeader("Authorization") String accessToken);

    @PostMapping("/revoke")
    @Operation(summary = "토큰 폐기", description = "특정 토큰을 강제로 무효화합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 폐기 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    ApiResponse<Void> revokeToken(@RequestHeader("Authorization") String accessToken, @RequestHeader(value = "Refresh-Token", required = false) String refreshToken);
}