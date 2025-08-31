package com.hs.auth.user.domain;

import com.hs.auth.common.OAuth2Provider;

import java.time.ZonedDateTime;
import java.util.Objects;

public class User {
    private final UserId id;
    private final String email;
    private final OAuth2Provider provider;
    private final ZonedDateTime createdAt;
    
    public User(UserId id, String email, OAuth2Provider provider, ZonedDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "사용자 ID는 필수입니다.");
        this.email = Objects.requireNonNull(email, "이메일은 필수입니다.");
        this.provider = Objects.requireNonNull(provider, "OAuth2 Provider는 필수입니다.");
        this.createdAt = Objects.requireNonNull(createdAt, "생성일시는 필수입니다.");
    }
    
    public UserId getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public OAuth2Provider getProvider() {
        return provider;
    }
    
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}