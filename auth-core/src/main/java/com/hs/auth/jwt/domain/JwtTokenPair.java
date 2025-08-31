package com.hs.auth.jwt.domain;

public class JwtTokenPair {
    private final JwtToken accessToken;
    private final JwtToken refreshToken;
    
    public JwtTokenPair(JwtToken accessToken, JwtToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    
    public JwtToken getAccessToken() {
        return accessToken;
    }
    
    public JwtToken getRefreshToken() {
        return refreshToken;
    }
}