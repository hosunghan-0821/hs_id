package com.hs.auth.project.application.dto;

import com.hs.auth.project.domain.ProjectId;

public class UpdateProjectCommand {
    private final ProjectId projectId;
    private final String description;

    public UpdateProjectCommand(ProjectId projectId, String description) {
        this.projectId = projectId;
        this.description = description;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public String getDescription() {
        return description;
    }
}