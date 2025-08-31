package com.hs.auth.oauth2.client;

import com.hs.auth.common.OAuth2Provider;
import com.hs.auth.oauth2.common.OAuth2Client;
import com.hs.auth.oauth2.dto.KakaoTokenResponse;
import com.hs.auth.oauth2.dto.KakaoUserInfoResponse;
import com.hs.auth.oauth2.dto.OAuth2TokenResponse;
import com.hs.auth.oauth2.dto.OAuth2UserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOAuth2Client implements OAuth2Client {
    
    private final RestTemplate restTemplate;
    
    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    
    @Value("${oauth2.kakao.client-secret}")
    private String clientSecret;
    
    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUri;
    
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    
    public KakaoOAuth2Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public OAuth2TokenResponse exchangeCodeForToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("code", authorizationCode);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
            TOKEN_URL,
            HttpMethod.POST,
            request,
            KakaoTokenResponse.class
        );
        
        return response.getBody();
    }
    
    @Override
    public OAuth2UserInfoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
            USER_INFO_URL,
            HttpMethod.GET,
            request,
            KakaoUserInfoResponse.class
        );
        
        return response.getBody();
    }
    
    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }
}