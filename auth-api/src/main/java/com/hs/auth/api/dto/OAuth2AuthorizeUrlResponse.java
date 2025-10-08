package com.hs.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "OAuth2 인증 URL 응답")
public class OAuth2AuthorizeUrlResponse {

    @Schema(description = "OAuth2 Provider 인증 URL", example = "https://kauth.kakao.com/oauth/authorize?client_id=xxx&redirect_uri=xxx&state=xxx")
    private String authorizeUrl;
}