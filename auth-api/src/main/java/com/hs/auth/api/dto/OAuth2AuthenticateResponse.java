package com.hs.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth2 인증 완료 응답")
public class OAuth2AuthenticateResponse {
    
    @Schema(description = "OAuth2 제공자", example = "kakao")
    private String provider;
    
    @Schema(description = "서비스 이름", example = "shop")
    private String serviceName;
    
    @Schema(description = "사용자 정보")
    private UserInfo user;
    
    @Schema(description = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    
    @Schema(description = "JWT 리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    public OAuth2AuthenticateResponse() {}

    public OAuth2AuthenticateResponse(String provider, String serviceName, UserInfo user, String accessToken, String refreshToken) {
        this.provider = provider;
        this.serviceName = serviceName;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Schema(description = "사용자 정보")
    public static class UserInfo {
        @Schema(description = "사용자 ID", example = "12345")
        private String id;
        
        @Schema(description = "닉네임", example = "홍길동")
        private String nickname;
        
        @Schema(description = "이메일", example = "test@example.com")
        private String email;

        public UserInfo() {}

        public UserInfo(String id, String nickname, String email) {
            this.id = id;
            this.nickname = nickname;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}