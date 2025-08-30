package com.hs.auth.project.application.dto;

public class RegisterProjectCommand {
    private final String name;
    private final String description;
    private final String redirectUri;

    public RegisterProjectCommand(String name, String description, String redirectUri) {
        this.name = name;
        this.description = description;
        this.redirectUri = redirectUri;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}