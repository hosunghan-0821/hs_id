package com.hs.auth.oauth2.dto;

public interface OAuth2UserInfoResponse {
    String getId();
    String getNickname();
    String getEmail();
    String getProfileImage();
}