package com.hs.auth.authenticate.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class KakaoUserInfoResponse implements OAuth2UserInfoResponse {
    private Long id;
    
    @JsonProperty("connected_at")
    private String connectedAt;
    
    private Map<String, Object> properties;
    
    @JsonProperty("kakao_account")
    private Map<String, Object> kakaoAccount;

    public Long getKakaoId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(String connectedAt) {
        this.connectedAt = connectedAt;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getKakaoAccount() {
        return kakaoAccount;
    }

    public void setKakaoAccount(Map<String, Object> kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }
    
    @Override
    public String getNickname() {
        if (properties != null) {
            return (String) properties.get("nickname");
        }
        return null;
    }
    
    @Override
    public String getEmail() {
        if (kakaoAccount != null) {
            return (String) kakaoAccount.get("email");
        }
        return null;
    }
    
    @Override
    public String getId() {
        return id != null ? id.toString() : null;
    }
    
    @Override
    public String getProfileImage() {
        if (properties != null) {
            return (String) properties.get("profile_image");
        }
        return null;
    }
}