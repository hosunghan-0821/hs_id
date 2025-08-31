package com.hs.auth.authentication.jwt.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

public class JwtToken {
    private final String token;
    private final TokenType type;
    private final ZonedDateTime issuedAt;
    private final ZonedDateTime expiresAt;
    
    public JwtToken(String token, TokenType type, ZonedDateTime issuedAt, ZonedDateTime expiresAt) {
        this.token = Objects.requireNonNull(token, "토큰은 필수입니다.");
        this.type = Objects.requireNonNull(type, "토큰 타입은 필수입니다.");
        this.issuedAt = Objects.requireNonNull(issuedAt, "발급일시는 필수입니다.");
        this.expiresAt = Objects.requireNonNull(expiresAt, "만료일시는 필수입니다.");
    }
    
    public String getToken() {
        return token;
    }
    
    public TokenType getType() {
        return type;
    }
    
    public ZonedDateTime getIssuedAt() {
        return issuedAt;
    }
    
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public boolean isExpired() {
        return ZonedDateTime.now().isAfter(expiresAt);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtToken jwtToken = (JwtToken) o;
        return Objects.equals(token, jwtToken.token);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}