package com.hs.auth.user.application.dto;

import com.hs.auth.common.OAuth2Provider;

public class AuthenticateUserCommand {
    private final String email;
    private final OAuth2Provider provider;

    public AuthenticateUserCommand(String email, OAuth2Provider provider) {
        this.email = email;
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public OAuth2Provider getProvider() {
        return provider;
    }
}