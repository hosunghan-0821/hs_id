package com.hs.auth.oauth2.dto;

public interface OAuth2TokenResponse {
    String getTokenType();
    String getAccessToken();
    Long getExpiresIn();
    String getRefreshToken();
    Long getRefreshTokenExpiresIn();
    String getScope();
}