package com.hs.auth.project.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Project {
    private final ProjectId id;
    private final String name;
    private final String description;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final ProjectStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Project(String name, String description, String redirectUri) {
        this.id = ProjectId.generate();
        this.name = validateName(name);
        this.description = description;
        this.clientId = generateClientId();
        this.clientSecret = generateClientSecret();
        this.redirectUri = validateRedirectUri(redirectUri);
        this.status = ProjectStatus.INACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private Project(ProjectId id, String name, String description, String clientId, 
                   String clientSecret, String redirectUri, ProjectStatus status,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Project restore(ProjectId id, String name, String description, String clientId,
                                String clientSecret, String redirectUri, ProjectStatus status,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Project(id, name, description, clientId, clientSecret, redirectUri, 
                          status, createdAt, updatedAt);
    }

    public Project activate() {
        if (this.status == ProjectStatus.ACTIVE) {
            throw new IllegalStateException("Project is already active");
        }
        return new Project(this.id, this.name, this.description, this.clientId, 
                          this.clientSecret, this.redirectUri, ProjectStatus.ACTIVE,
                          this.createdAt, LocalDateTime.now());
    }

    public Project deactivate() {
        if (this.status == ProjectStatus.INACTIVE) {
            throw new IllegalStateException("Project is already inactive");
        }
        return new Project(this.id, this.name, this.description, this.clientId, 
                          this.clientSecret, this.redirectUri, ProjectStatus.INACTIVE,
                          this.createdAt, LocalDateTime.now());
    }

    public Project updateDescription(String newDescription) {
        return new Project(this.id, this.name, newDescription, this.clientId, 
                          this.clientSecret, this.redirectUri, this.status,
                          this.createdAt, LocalDateTime.now());
    }

    public Project regenerateClientSecret() {
        return new Project(this.id, this.name, this.description, this.clientId, 
                          generateClientSecret(), this.redirectUri, this.status,
                          this.createdAt, LocalDateTime.now());
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Project name cannot exceed 100 characters");
        }
        return name.trim();
    }

    private String validateRedirectUri(String redirectUri) {
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            throw new IllegalArgumentException("Redirect URI cannot be null or empty");
        }
        if (!redirectUri.startsWith("http://") && !redirectUri.startsWith("https://")) {
            throw new IllegalArgumentException("Redirect URI must start with http:// or https://");
        }
        return redirectUri.trim();
    }

    private String generateClientId() {
        return "client_" + System.currentTimeMillis();
    }

    private String generateClientSecret() {
        return "secret_" + UUID.randomUUID().toString().replace("-", "");
    }

    public ProjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}