package com.hs.auth.project.application;

import com.hs.auth.project.application.dto.ActivateProjectCommand;
import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivateProjectUseCase {
    private final ProjectRepository projectRepository;

    public ActivateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project execute(ActivateProjectCommand command) {
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        Project activatedProject = project.activate();
        return projectRepository.save(activatedProject);
    }
}