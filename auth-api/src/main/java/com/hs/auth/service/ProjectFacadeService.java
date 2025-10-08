package com.hs.auth.service;

import com.hs.auth.api.dto.ProjectRegistrationResult;
import com.hs.auth.project.application.*;
import com.hs.auth.project.application.dto.ActivateProjectCommand;
import com.hs.auth.project.application.dto.RegisterProjectCommand;
import com.hs.auth.project.application.dto.UpdateProjectCommand;
import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectFacadeService {

    private final RegisterProjectUseCase registerProjectUseCase;
    private final ProjectQueryService projectQueryService;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final ActivateProjectUseCase activateProjectUseCase;
    private final DeactivateProjectUseCase deactivateProjectUseCase;
    private final PasswordEncoder passwordEncoder;

    public ProjectFacadeService(
            RegisterProjectUseCase registerProjectUseCase,
            ProjectQueryService projectQueryService,
            UpdateProjectUseCase updateProjectUseCase,
            ActivateProjectUseCase activateProjectUseCase,
            DeactivateProjectUseCase deactivateProjectUseCase,
            PasswordEncoder passwordEncoder
    ) {
        this.registerProjectUseCase = registerProjectUseCase;
        this.projectQueryService = projectQueryService;
        this.updateProjectUseCase = updateProjectUseCase;
        this.activateProjectUseCase = activateProjectUseCase;
        this.deactivateProjectUseCase = deactivateProjectUseCase;
        this.passwordEncoder = passwordEncoder;
    }

    public ProjectRegistrationResult registerProject(String name, String description, String redirectUri) {
        // 1. clientId와 평문 secret 생성
        String clientId = Project.generateClientId();
        String plainSecret = Project.generatePlainClientSecret();

        // 2. secret 암호화
        String encodedSecret = passwordEncoder.encode(plainSecret);

        // 3. UseCase 실행
        RegisterProjectCommand command = new RegisterProjectCommand(
            name, description, redirectUri, clientId, encodedSecret
        );
        Project project = registerProjectUseCase.execute(command);

        // 4. 평문 secret과 함께 반환
        return new ProjectRegistrationResult(project, plainSecret);
    }

    public List<Project> getAllProjects() {
        return projectQueryService.findAll();
    }

    public Project getProject(String projectId) {
        return projectQueryService.findById(ProjectId.from(projectId))
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: " + projectId));
    }

    public Project updateProject(String projectId, String description) {
        UpdateProjectCommand command = new UpdateProjectCommand(ProjectId.from(projectId), description);
        return updateProjectUseCase.execute(command);
    }

    public Project activateProject(String projectId) {
        ActivateProjectCommand command = new ActivateProjectCommand(ProjectId.from(projectId));
        return activateProjectUseCase.execute(command);
    }

    public Project deactivateProject(String projectId) {
        ActivateProjectCommand command = new ActivateProjectCommand(ProjectId.from(projectId));
        return deactivateProjectUseCase.execute(command);
    }
}