package com.hs.auth.oauth2.common;

import com.hs.auth.common.OAuth2Provider;
import com.hs.auth.oauth2.dto.OAuth2TokenResponse;
import com.hs.auth.oauth2.dto.OAuth2UserInfoResponse;

public interface OAuth2Client {
    OAuth2TokenResponse exchangeCodeForToken(String authorizationCode);
    OAuth2UserInfoResponse getUserInfo(String accessToken);
    OAuth2Provider getProvider();
}