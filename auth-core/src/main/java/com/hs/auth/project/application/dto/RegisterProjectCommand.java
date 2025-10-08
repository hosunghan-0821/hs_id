package com.hs.auth.project.application.dto;

public class RegisterProjectCommand {
    private final String name;
    private final String description;
    private final String redirectUri;
    private final String clientId;
    private final String encodedClientSecret;

    public RegisterProjectCommand(String name, String description, String redirectUri, String clientId, String encodedClientSecret) {
        this.name = name;
        this.description = description;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
        this.encodedClientSecret = encodedClientSecret;
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

    public String getClientId() {
        return clientId;
    }

    public String getEncodedClientSecret() {
        return encodedClientSecret;
    }
}