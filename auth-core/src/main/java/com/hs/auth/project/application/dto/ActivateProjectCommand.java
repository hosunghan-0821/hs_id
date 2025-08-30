package com.hs.auth.project.application.dto;

import com.hs.auth.project.domain.ProjectId;

public class ActivateProjectCommand {
    private final ProjectId projectId;

    public ActivateProjectCommand(ProjectId projectId) {
        this.projectId = projectId;
    }

    public ProjectId getProjectId() {
        return projectId;
    }
}