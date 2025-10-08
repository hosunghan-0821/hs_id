package com.hs.auth.api.dto;

import com.hs.auth.project.domain.Project;

public class ProjectRegistrationResult {
    private final Project project;
    private final String plainClientSecret;

    public ProjectRegistrationResult(Project project, String plainClientSecret) {
        this.project = project;
        this.plainClientSecret = plainClientSecret;
    }

    public Project getProject() {
        return project;
    }

    public String getPlainClientSecret() {
        return plainClientSecret;
    }
}