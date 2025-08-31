package com.hs.auth.authentication.jwt.application.dto;

import com.hs.auth.user.domain.User;

public class GenerateTokenCommand {
    private final User user;
    private final String serviceName;

    public GenerateTokenCommand(User user, String serviceName) {
        this.user = user;
        this.serviceName = serviceName;
    }

    public User getUser() {
        return user;
    }

    public String getServiceName() {
        return serviceName;
    }
}
