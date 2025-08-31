package com.hs.auth.user.application.dto;

import com.hs.auth.user.domain.UserId;

public class RegisterServiceUserCommand {
    private final UserId userId;
    private final String serviceName;

    public RegisterServiceUserCommand(UserId userId, String serviceName) {
        this.userId = userId;
        this.serviceName = serviceName;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getServiceName() {
        return serviceName;
    }
}
