package com.hs.auth.project.domain.event;

import com.hs.auth.common.event.DomainEvent;
import com.hs.auth.project.domain.ProjectId;

public class ProjectRegisteredEvent extends DomainEvent {
    private final ProjectId projectId;
    private final String projectName;
    private final String clientId;
    private final String redirectUri;

    public ProjectRegisteredEvent(ProjectId projectId, String projectName, String clientId, String redirectUri) {
        super();
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}