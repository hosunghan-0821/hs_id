package com.hs.auth.authenticate.oauth2.common;

import com.hs.auth.authenticate.oauth2.dto.OAuth2TokenResponse;
import com.hs.auth.authenticate.oauth2.dto.OAuth2UserInfoResponse;
import com.hs.auth.common.OAuth2Provider;

public interface OAuth2Client {
    OAuth2TokenResponse exchangeCodeForToken(String authorizationCode);
    OAuth2UserInfoResponse getUserInfo(String accessToken);
    OAuth2Provider getProvider();
}