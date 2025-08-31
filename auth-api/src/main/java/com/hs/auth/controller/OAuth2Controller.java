package com.hs.auth.controller;

import com.hs.auth.api.OAuth2Api;
import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.api.dto.OAuth2AuthenticateRequest;
import com.hs.auth.api.dto.OAuth2AuthenticateResponse;
import com.hs.auth.api.dto.OAuth2CallbackResponse;
import com.hs.auth.common.exception.OAuth2Exception;
import com.hs.auth.jwt.application.GenerateTokenUseCase;
import com.hs.auth.jwt.domain.JwtTokenPair;
import com.hs.auth.oauth2.common.OAuth2Client;
import com.hs.auth.oauth2.dto.OAuth2TokenResponse;
import com.hs.auth.oauth2.dto.OAuth2UserInfoResponse;
import com.hs.auth.oauth2.factory.OAuth2ClientFactory;
import com.hs.auth.oauth2.service.OAuth2StateService;
import com.hs.auth.service.OAuth2AuthenticationService;
import com.hs.auth.user.application.AuthenticateUserUseCase;
import com.hs.auth.user.application.dto.AuthenticateUserCommand;
import com.hs.auth.user.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/oauth2")
public class OAuth2Controller implements OAuth2Api {
    
    private final OAuth2StateService oauth2StateService;
    private final OAuth2AuthenticationService oauth2AuthenticationService;
    
    public OAuth2Controller(OAuth2StateService oauth2StateService, OAuth2AuthenticationService oauth2AuthenticationService) {
        this.oauth2StateService = oauth2StateService;
        this.oauth2AuthenticationService = oauth2AuthenticationService;
    }
    
    @Override
    public ApiResponse<OAuth2CallbackResponse> handleAuthorizationCallback(
            @RequestParam("code") String authorizationCode,
            @RequestParam(value = "state", required = false) String state) {
        
        if (authorizationCode == null || authorizationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("인가 코드가 없습니다.");
        }

        oauth2StateService.parseState(state); // IllegalArgumentException 발생 시 GlobalHandler가 처리
        
        OAuth2CallbackResponse callbackResponse = new OAuth2CallbackResponse(
                authorizationCode,
                state != null ? state : "",
                "이제 /api/auth/oauth2/authenticate API를 호출하여 인증을 완료하세요."
        );
        
        return ApiResponse.success("인가 코드를 성공적으로 받았습니다.", callbackResponse);
    }

    @Override
    public ApiResponse<OAuth2AuthenticateResponse> authenticateWithOAuth2(OAuth2AuthenticateRequest request) {
        OAuth2AuthenticateResponse response = oauth2AuthenticationService.authenticate(request);
        return ApiResponse.success("OAuth2 인증이 성공적으로 완료되었습니다.", response);
    }
}