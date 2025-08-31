package com.hs.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth2 인가코드 콜백 응답")
public class OAuth2CallbackResponse {
    
    @Schema(description = "인가 코드", example = "wxNMqOIb9n8dWyeCOs7K...")
    private String authorizationCode;
    
    @Schema(description = "State 파라미터", example = "kakao_shop")
    private String state;
    
    @Schema(description = "다음 단계 안내", example = "이제 /api/auth/oauth2/authenticate API를 호출하여 인증을 완료하세요.")
    private String nextStep;

    public OAuth2CallbackResponse() {}

    public OAuth2CallbackResponse(String authorizationCode, String state, String nextStep) {
        this.authorizationCode = authorizationCode;
        this.state = state;
        this.nextStep = nextStep;
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

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }
}