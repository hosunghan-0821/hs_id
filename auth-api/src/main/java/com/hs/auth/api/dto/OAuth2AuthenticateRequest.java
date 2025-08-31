package com.hs.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth2 인증 요청")
public class OAuth2AuthenticateRequest {
    
    @Schema(description = "OAuth2 인가 코드", example = "wxNMqOIb9n8dWyeCOs7K...", required = true)
    private String authorizationCode;
    
    @Schema(description = "State 파라미터", example = "kakao_shop", required = true)
    private String state;

    public OAuth2AuthenticateRequest() {}

    public OAuth2AuthenticateRequest(String authorizationCode, String state) {
        this.authorizationCode = authorizationCode;
        this.state = state;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}