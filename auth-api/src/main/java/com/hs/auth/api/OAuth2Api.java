package com.hs.auth.api;

import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.api.dto.OAuth2AuthenticateRequest;
import com.hs.auth.api.dto.OAuth2AuthenticateResponse;
import com.hs.auth.api.dto.OAuth2CallbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "OAuth2 Authentication", description = "OAuth2 인증 관련 API")
public interface OAuth2Api {

    @GetMapping("/callback")
    @Operation(summary = "OAuth2 인가 코드 받기", description = "OAuth2 제공자로부터 받은 인가 코드를 클라이언트에게 전달")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인가 코드 전달 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 인가 코드 또는 State")
    })
    ApiResponse<OAuth2CallbackResponse> handleAuthorizationCallback(
            @RequestParam("code") String authorizationCode,
            @RequestParam(value = "state", required = false) String state
    );

    @PostMapping("/authenticate")
    @Operation(summary = "OAuth2 인증 처리", description = "인가 코드로 토큰 교환, 사용자 정보 조회, 가입 처리 및 JWT 토큰 발급")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증 성공 및 JWT 토큰 발급"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "인증 처리 실패")
    })
    ApiResponse<OAuth2AuthenticateResponse> authenticateWithOAuth2(
            @RequestBody OAuth2AuthenticateRequest request
    );
}