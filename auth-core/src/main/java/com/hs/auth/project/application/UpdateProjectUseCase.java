package com.hs.auth.project.application;

import com.hs.auth.project.application.dto.UpdateProjectCommand;
import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.port.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProjectUseCase {
    private final ProjectRepository projectRepository;

    public UpdateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project execute(UpdateProjectCommand command) {
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        Project updatedProject = project.updateDescription(command.getDescription());
        return projectRepository.save(updatedProject);
    }
}